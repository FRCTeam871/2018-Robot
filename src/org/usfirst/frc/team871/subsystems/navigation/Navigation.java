package org.usfirst.frc.team871.subsystems.navigation;

import org.usfirst.frc.team871.subsystems.DriveTrain;
import org.usfirst.frc.team871.subsystems.navigation.actions.NullAction;
import org.usfirst.frc.team871.util.control.PIDReadWrite;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Navigation is field oriented, so that the only point shifting between matches is the starting point of the robot.
 * We feel this is more clear to understand than a robot oriented system, which would require shifting the entire path for each starting position.
 * @author Team871
 */
public class Navigation {
//TODO more documentation
	private DriveTrain drive;
	private AHRS navX;
	private IDisplacementSensor displaceSense;
	private IWaypointProvider waypointProvider;
	
	private double distThreshold;
	
	private Coordinate initialPos;
	private Waypoint currentLocation;
	private Waypoint nextWaypoint;
	
	private PIDReadWrite distancePIDInterface;
	private final double DIST_KP;
	private final double DIST_KI;
	private final double DIST_KD;
	private PIDController distancePID;
	
	private PIDReadWrite anglePIDInterface;
	private final double ANGLE_KP;
	private final double ANGLE_KI;
	private final double ANGLE_KD;
	private PIDController anglePID;
		
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
		
		distThreshold = 6.0;
		
		currentLocation = new Waypoint(0.0, 0.0, 0.0, 0.0, new NullAction());
		nextWaypoint    = new Waypoint(0.0, 0.0, 0.0, 0.0, new NullAction());
		
		DIST_KP = 0.0;
		DIST_KI = 0.0;
		DIST_KD = 0.0;
		distancePIDInterface = new PIDReadWrite(PIDSourceType.kDisplacement);
		distancePID          = new PIDController(DIST_KP, DIST_KI, DIST_KD, distancePIDInterface, distancePIDInterface);
		distancePID.setSetpoint(0);//The distance that we want to be away from the waypoint is zero
		distancePID.setOutputRange(-1, 1);
		
		//Move angle PID things to drive class in a heading hold method
		ANGLE_KP = 0.0;
		ANGLE_KI = 0.0;
		ANGLE_KD = 0.0;
		anglePIDInterface = new PIDReadWrite(PIDSourceType.valueOf("Heading"));
		anglePID          = new PIDController(ANGLE_KP, ANGLE_KI, ANGLE_KD, anglePIDInterface, anglePIDInterface);
		
		updateLocation();//updates location
	}

	/**
	 * Is called on loop during autonomous phase to navigate to differing locations
	 */
	public void navigate() {
		
		if(!waypointProvider.hasNext()) {
			updateLocation();
			//We are done... do something?
		}
		else if(true){ //TODO: have a doNavigate boolean 
			double distance  = getDistance(currentLocation, nextWaypoint);
			double direction = getAngle(currentLocation, nextWaypoint);
			double angle     = nextWaypoint.getAngle();
			
			//Setpoint is 0
			distancePIDInterface.errorWrite(distance);//Distance between waypoint and current location
			double magnitude = (distancePIDInterface.pidGet()*nextWaypoint.getSpeed());
			
			anglePID.setSetpoint(angle);//Angle the robot needs to hold
			anglePIDInterface.errorWrite(navX.getYaw());//The angle the robot is actually at
			anglePID.setOutputRange(0, 360);
			double rotation = anglePIDInterface.pidGet();//how much we need to rotate to be at the desired angle
			
			drive.drivePolar(magnitude, direction, rotation);
			updateLocation();
			
			if(distance < distThreshold) {//If we are at the waypoint, do the action
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
		double velo = Math.sqrt(Math.pow(displaceSense.getVelocity().getX(), 2) + Math.pow(displaceSense.getVelocity().getY(), 2)); //combines both velo components to the velocity
		
		currentLocation.setX(location.getX() + initialPos.getX());
		currentLocation.setY(location.getY() + initialPos.getY());//Uses our fun enhanced gyro class. Fix this. We arent using it. We misunderstood the gyro class.
		currentLocation.setAngle(navX.getYaw());
		currentLocation.setSpeed(velo);
		
	}
	
	/**
	 * Gets distance between two waypoints 
	 * @param waypoint1 First waypoint
	 * @param waypoint2 Second Waypoint
	 * @return
	 */
	private double getDistance(Waypoint waypoint1, Waypoint waypoint2) {

		double distance = 0;
		
		distance = Math.sqrt(Math.pow(waypoint1.getX(), 2) + Math.pow(waypoint1.getY(), 2)) ;
		//pathagaras theorem- a^2 +b^2 = c^2
		//^ Larry spelled it that way so we are going to keep it to shame him
		return distance;
	}
	
	/**
	 * Gets angle based on waypoint coordinate system which is initialized at the beginning of the match with the y axis perpendicular to the back wall
	 * @param waypoint1 First waypoint
	 * @param waypoint2 Second waypoint
	 * @return
	 */
	private double getAngle(Waypoint waypoint1, Waypoint waypoint2) {
		double angle = 0;
		//the x and y arguments are reversed because of the coordinate transformation between the gyro angle and the field coordinate system
		angle = Math.atan2(waypoint2.getX()-waypoint1.getX(), waypoint2.getY()-waypoint1.getY());
		
		return angle;
	}
}