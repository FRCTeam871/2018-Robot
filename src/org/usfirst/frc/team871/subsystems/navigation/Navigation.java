package org.usfirst.frc.team871.subsystems.navigation;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team871.subsystems.DriveTrain;
import org.usfirst.frc.team871.subsystems.navigation.Sensors.IDisplacementSensor;
import org.usfirst.frc.team871.util.CoordinateCalculation;
import org.usfirst.frc.team871.util.units.DistanceUnit;

/**
 * Navigation is field oriented, so that the only point shifting between matches is the starting point of the robot.
 * We feel this is more clear to understand than a robot oriented system, which would require shifting the entire path for each starting position.
 *
 * @author Team871-TPfaffe
 */
public class Navigation {
    private DriveTrain drive;
    private IDisplacementSensor displaceSense;
    private IWaypointProvider waypointProvider;
    private Coordinate initialPos;
    private Coordinate currentLocation;
    private Waypoint nextWaypoint;
    private boolean isDone;

    private CoordinateCalculation coordCalc;

    private double DIST_THRESHOLD;
    /**
     * @param displaceSense
     * @param waypointProvider
     * @param startLocation
     */
    public Navigation(DriveTrain drive, IDisplacementSensor displaceSense, IWaypointProvider waypointProvider, Coordinate startLocation) {
        this.displaceSense    = displaceSense;
        this.waypointProvider = waypointProvider;
        this.initialPos = startLocation;
        this.drive = drive;
        this.isDone = false;

        coordCalc = new CoordinateCalculation();

        currentLocation = new Coordinate(startLocation.getX(), startLocation.getY());
        nextWaypoint    = waypointProvider.getNextWaypoint();

        DIST_THRESHOLD  = 3.0;

        //updates location
        updateLocation();

        //enables heading hold
        drive.enableHeadingHold();
    }

    /**
     * Is called on loop during autonomous phase to navigate to differing locations
     */
    public void navigate() {
        updateLocation();

        if (this.isDone) {
            drive.disableHeadingHold();
            drive.driveRobotOriented(0, 0, 0);//stop
        } else {
            double distance = coordCalc.getDistance(currentLocation, nextWaypoint);
            double direction = coordCalc.getAngle(currentLocation, nextWaypoint);
            double magnitude = nextWaypoint.getSpeed();
            
            SmartDashboard.putNumber("navDist", distance);
            SmartDashboard.putNumber("navDir", direction);
            SmartDashboard.putNumber("navMag", magnitude);
            SmartDashboard.putString("navCurrLocation", currentLocation.toString());
            SmartDashboard.putString("navNextWaypoint", nextWaypoint.toString());
            
            if(distance >= DIST_THRESHOLD) {
            	if(drive.isAtSetpoint()) {
            		drive.drivePolar(magnitude, 0, 0);
            	 } else {
                	drive.drivePolar(0, 0, 0);
                }

            	drive.setHeadingHold(direction);
            } else {
                //If we are at the waypoint, do the action

            	drive.drivePolar(0, 0, 0);
            	nextWaypoint.getAction().execute();

                //if the action is complete move on
                if(nextWaypoint.getAction().isComplete()) {
                    //since we got to the waypoint and performed it's action, get the next one
                    if (waypointProvider.hasNext()) {
                        nextWaypoint = waypointProvider.getNextWaypoint();
                        nextWaypoint.getAction().init();
                    } else {
                        //done with navigation
                        this.isDone = true;
                    }
                }
            }
        }
    }

    public void reset() {
        stop();
        waypointProvider.reset();
        isDone = false;
        currentLocation.setX(initialPos.getX());
        currentLocation.setY(initialPos.getY());
    }

    public void stop() {
        if(!nextWaypoint.getAction().isComplete()) {
            nextWaypoint.getAction().abort();
        }

        drive.disableHeadingHold();
        drive.driveRobotOriented(0, 0, 0);//stop
    }

    /**
     * Updates current location, angle, and speed of the robot from displace sensor
     */
    private void updateLocation() {
        Coordinate location = displaceSense.getDisplacement(DistanceUnit.INCH);//displacement in inches

        currentLocation.setX(location.getX() + initialPos.getX());
        currentLocation.setY(location.getY() + initialPos.getY());
    }
}