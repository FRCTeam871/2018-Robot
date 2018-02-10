package org.usfirst.frc.team871.subsystems.navigation;

import org.usfirst.frc.team871.subsystems.DriveTrain;
import org.usfirst.frc.team871.subsystems.navigation.actions.NullAction;
import org.usfirst.frc.team871.util.StaticCoordinateCalculation;
import org.usfirst.frc.team871.util.control.PIDReadWrite;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Navigation is field oriented, so that the only point shifting between matches is the starting point of the robot.
 * We feel this is more clear to understand than a robot oriented system, which would require shifting the entire path for each starting position.
 * @author Team871
 */
public class Navigation{
//TODO more documentation
	private DriveTrain drive;
	private AHRS navX;
	private IDisplacementSensor displaceSense;
	private IWaypointProvider waypointProvider;
	
	private StaticCoordinateCalculation coordCalc;
	
	private Coordinate initialPos;
	private Waypoint currentLocation;
	private Waypoint nextWaypoint;
	
	private PIDReadWrite distancePIDInterface;
	private final double DIST_KP;
	private final double DIST_KI;
	private final double DIST_KD;
	private PIDController distancePID;
			
	private double DIST_THRESHOLD;
	
	/**
	 * 
	 * @param displaceSense
	 * @param waypointProvider
	 * @param drive
	 * @param startLocation
	 */
	public Navigation(IDisplacementSensor displaceSense, IWaypointProvider waypointProvider, DriveTrain drive, Coordinate startLocation) {
		this.displaceSense    = displaceSense;
		this.waypointProvider = waypointProvider;
		this.drive = drive;
		this.navX = this.drive.getGyro();
		this.initialPos = startLocation;
		
		coordCalc = new StaticCoordinateCalculation();
		
		currentLocation = new Waypoint(0.0, 0.0, 0.0, 0.0, new NullAction());
		nextWaypoint    = new Waypoint(0.0, 0.0, 0.0, 0.0, new NullAction());
		
		DIST_THRESHOLD = 6.0;
		
		DIST_KP = 0.0;
		DIST_KI = 0.0;
		DIST_KD = 0.0;
		distancePIDInterface = new PIDReadWrite(PIDSourceType.kDisplacement);
		distancePID          = new PIDController(DIST_KP, DIST_KI, DIST_KD, distancePIDInterface, distancePIDInterface);
		distancePID.setSetpoint(0);//The distance that we want to be away from the waypoint is zero
		distancePID.setOutputRange(-1, 1);
		
		updateLocation();//updates location
	}

	/**
	 * Is called on loop during autonomous phase to navigate to differing locations
	 */
	public void navigate() {
		
		if(!waypointProvider.hasNext()) {
			updateLocation();
			
			//stop motors
			//We are done... do something?
		}
		else { //TODO: have a doNavigate boolean 
			double distance  = coordCalc.getDistance(currentLocation, nextWaypoint);
			double direction = coordCalc.getAngle(currentLocation, nextWaypoint);
			double angle     = drive.getGyro().getAngle();
			
			
						
			double magnitude = nextWaypoint.getSpeed();
//			if(!nextWaypoint.getIsSpeedConstant()) {_
//				distancePIDInterface.errorWrite(distance);//Distance between waypoint and current location
//			    magnitude = (distancePIDInterface.pidGet() * coordCalc.getResultant(displaceSense.getVelocity()));
//			} 
			
			drive.drivePolar(magnitude, direction, 0);//TODO: angle hold
			updateLocation();
			
			if(distance < DIST_THRESHOLD) {//If we are at the waypoint, do the action
				//TODO: Action handler here
				
				if(waypointProvider.hasNext()) {//since we got to the waypoint, get the next one
					nextWaypoint = waypointProvider.getNextWaypoint();
				}
			}
		}
		
	}
	
	/**
	 * Updates current location, angle, and speed of the robot from displace sensor
	 * @param speed
	 */
	private void updateLocation() {
		Coordinate location = displaceSense.getDisplacement_in();//displacement in inches
		
		currentLocation.setX(location.getX() + initialPos.getX());
		currentLocation.setY(location.getY() + initialPos.getY());	
	}
	
	
}