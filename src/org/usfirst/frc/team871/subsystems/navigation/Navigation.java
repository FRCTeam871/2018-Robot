package org.usfirst.frc.team871.subsystems.navigation;

import org.usfirst.frc.team871.robot.Robot;
import org.usfirst.frc.team871.subsystems.navigation.Sensors.IDisplacementSensor;
import org.usfirst.frc.team871.subsystems.navigation.actions.ActionHandler;
import org.usfirst.frc.team871.subsystems.navigation.actions.NullAction;
import org.usfirst.frc.team871.util.CoordinateCalculation;
import org.usfirst.frc.team871.util.units.DistanceUnit;

import edu.wpi.first.wpilibj.PIDController;

/**
 * Navigation is field oriented, so that the only point shifting between matches is the starting point of the robot.
 * We feel this is more clear to understand than a robot oriented system, which would require shifting the entire path for each starting position.
 *
 * @author Team871-TPfaffe
 */
public class Navigation {

    private Robot robot;
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

    private final double DIST_KP;
    private final double DIST_KI;
    private final double DIST_KD;
    private PIDController distancePID;

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

        coordCalc = new CoordinateCalculation();

        currentLocation = new Waypoint(0.0, 0.0, 0.0, 0.0, new NullAction());
        nextWaypoint    = new Waypoint(0.0, 0.0, 0.0, 0.0, new NullAction());

        DIST_THRESHOLD  = 6.0;
        MAX_ACTION_TIME = 10;

        DIST_KP = 0.0;
        DIST_KI = 0.0;
        DIST_KD = 0.0;
        //TODO: Implement alternate way to utilize PID for Distance.

        actionHandler = new ActionHandler(this.robot,MAX_ACTION_TIME);

        this.isDone = false;

        updateLocation();//updates location
        this.robot.getDrive().enableHeadingHold();//enables heading hold
    }

    /**
     * Is called on loop during autonomous phase to navigate to differing locations
     */
    public void navigate() {

        if (this.isDone) {
            updateLocation();
            this.robot.getDrive().disableHeadingHold();
            this.robot.getDrive().driveRobotOriented(0, 0, 0);//stop

            //We are done... do something?
        } else {
            double distance = coordCalc.getDistance(currentLocation, nextWaypoint);
            double direction = coordCalc.getAngle(currentLocation, nextWaypoint);
            double angle = this.robot.getNavx().getAngle();

            double magnitude = nextWaypoint.getSpeed();
            this.robot.getDrive().setHeadingHold(angle);

            this.robot.getDrive().drivePolar(magnitude, direction, 0);
            updateLocation();

            if (distance < DIST_THRESHOLD) {//If we are at the waypoint, do the action
                if(nextWaypoint.getAction().isComplete()){//if the action is complete move on
                    if (waypointProvider.hasNext()) {//since we got to the waypoint and performed it's action, get the next one
                        nextWaypoint = waypointProvider.getNextWaypoint();
                        actionHandler.loadNewAction(nextWaypoint.getAction());
                    }
                    else {
                        this.isDone = true; //done with navigation
                    }
                }
                else {//action is not complete
                    actionHandler.runAction();
                }


            }
        }

    }

    public void stop(){
        if(!nextWaypoint.getAction().isComplete()){
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