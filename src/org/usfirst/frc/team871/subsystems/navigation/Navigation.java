package org.usfirst.frc.team871.subsystems.navigation;

import org.usfirst.frc.team871.robot.Robot;
import org.usfirst.frc.team871.subsystems.DriveTrain;
import org.usfirst.frc.team871.subsystems.navigation.Sensors.IDisplacementSensor;
import org.usfirst.frc.team871.subsystems.navigation.actions.ActionHandler;
import org.usfirst.frc.team871.subsystems.navigation.actions.IAction;
import org.usfirst.frc.team871.subsystems.navigation.actions.NullAction;
import org.usfirst.frc.team871.util.CoordinateCalculation;
import org.usfirst.frc.team871.util.units.DistanceUnit;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Navigation is field oriented, so that the only point shifting between matches is the starting point of the robot.
 * We feel this is more clear to understand than a robot oriented system, which would require shifting the entire path for each starting position.
 *
 * @author Team871-TPfaffe
 */
public class Navigation {

    private Robot robot;
    private DriveTrain drive;
    private IDisplacementSensor displaceSense;
    private IWaypointProvider waypointProvider;
    private Coordinate initialPos;
    private Waypoint currentLocation;
    private Waypoint nextWaypoint;
    private boolean isDone;

    private CoordinateCalculation coordCalc;
    private ActionHandler actionHandler;

    private double DIST_THRESHOLD;
    private long   MAX_ACTION_TIME;
    /**
     * @param robot robot object
     * @param displaceSense
     * @param waypointProvider
     * @param startLocation
     */
    public Navigation(Robot robot, IDisplacementSensor displaceSense, IWaypointProvider waypointProvider, Coordinate startLocation) {
        this.displaceSense    = displaceSense;
        this.waypointProvider = waypointProvider;
        this.robot      = robot;
        this.initialPos = startLocation;
        this.drive = robot.getDrive();

        coordCalc = new CoordinateCalculation();

        currentLocation = new Waypoint(0.0, 0.0, 0.0, 0.0);
        nextWaypoint    = waypointProvider.getNextWaypoint();

        DIST_THRESHOLD  = 3.0;
        MAX_ACTION_TIME = 10;

        actionHandler = new ActionHandler(this.robot,MAX_ACTION_TIME);

        this.isDone = false;

        updateLocation();//updates location
        drive.enableHeadingHold();//enables heading hold
    }

    /**
     * Is called on loop during autonomous phase to navigate to differing locations
     */
    public void navigate() {
        if (this.isDone) {
            updateLocation();
            drive.disableHeadingHold();
            drive.driveRobotOriented(0, 0, 0);//stop
        } else {
            double distance = coordCalc.getDistance(currentLocation, nextWaypoint);
            double direction = coordCalc.getAngle(currentLocation, nextWaypoint);
            double magnitude = nextWaypoint.getSpeed();
            
            SmartDashboard.putNumber("navDist", distance);
            SmartDashboard.putNumber("navDir", direction);
            SmartDashboard.putNumber("navMag", magnitude);
            SmartDashboard.putString("navCurrLocation", currentLocation.getX() + "," + currentLocation.getY());
            SmartDashboard.putString("navNextWaypoint", nextWaypoint.getX() + "," + nextWaypoint.getY());
            
            if(distance >= DIST_THRESHOLD) {
            	if(drive.isAtSetpoint()) {
            		drive.drivePolar(magnitude, 0, 0);
            	}else {
                	drive.drivePolar(0, 0, 0);
                }
            	drive.setHeadingHold(direction);
            }
            
            updateLocation();

            if (distance < DIST_THRESHOLD) {//If we are at the waypoint, do the action
            	drive.drivePolar(0, 0, 0);
            	nextWaypoint.getAction().execute();
                if(nextWaypoint.getAction().isComplete()) {//if the action is complete move on
                    if (waypointProvider.hasNext()) {//since we got to the waypoint and performed it's action, get the next one
                        nextWaypoint = waypointProvider.getNextWaypoint();
                        nextWaypoint.getAction().init(robot, null);
                    }else {
                        this.isDone = true; //done with navigation
                    }
                }
            }
        }
    }

    public void stop() {
        if(!nextWaypoint.getAction().isComplete()) {
            actionHandler.stopAction(); //dont want to call an abort if we are already done!
        }

        this.robot.getDrive().disableHeadingHold();
        this.robot.getDrive().driveRobotOriented(0, 0, 0);//stop
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