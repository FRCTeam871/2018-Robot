package org.usfirst.frc.team871.subsystems.navigation;

import org.usfirst.frc.team871.subsystems.navigation.actions.NullAction;
import org.usfirst.frc.team871.util.control.PIDReadWrite;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Navigation is field oriented, so that the only point shifting between matches is the starting point of the robot.
 * We feel this is more clear to understand than a robot oriented system, which would require shifting the entire path for each starting position.
 * @author Team871
 */
public class Navigation {

	private IDisplacementSensor displaceSense;
	private IWaypointProvider waypointProvider;
	
	private double distThreshold;
	
	private Waypoint currentLocation;
	private Waypoint nextLocation;
	
	private PIDReadWrite distancePIDInterface;//TODO: Fix to standardize naming scheme for Final variables
	private final double Dist_Kp;
	private final double Dist_Ki;
	private final double Dist_Kd;
	private PIDController distancePID;
	
	private PIDReadWrite anglePIDInterface;
	private final double Angle_Kp;
	private final double Angle_Ki;
	private final double Angle_Kd;
	private PIDController anglePID;
		
	
	public Navigation(IDisplacementSensor displaceSense, IWaypointProvider waypointProvider) {
		this.displaceSense    = displaceSense;
		this.waypointProvider = waypointProvider;
		
		distThreshold = 6.0;
		
		currentLocation = new Waypoint(0.0, 0.0, 0.0, 0.0, new NullAction());
		nextLocation    = new Waypoint(0.0, 0.0, 0.0, 0.0, new NullAction());
		
		PIDReadWrite distancePIDInterface = new PIDReadWrite(PIDSourceType.kDisplacement);
		Dist_Kp = 0.0;
		Dist_Ki = 0.0;
		Dist_Kd = 0.0;
		distancePID = new PIDController(Dist_Kp, Dist_Ki, Dist_Kd, anglePIDInterface, anglePIDInterface);
		
		anglePIDInterface = new PIDReadWrite(PIDSourceType.valueOf("Heading"));
		Angle_Kp = 0.0;
		Angle_Ki = 0.0;
		Angle_Kd = 0.0;
		anglePID = new PIDController(Angle_Kp, Angle_Ki, Angle_Kd, anglePIDInterface, anglePIDInterface);
		
	}
	
	public void navigate() {
		
		if(waypointProvider.hasNext()){
			double distance = getDistance(currentLocation, nextLocation);
			
			if(distance < distThreshold) {
				nextLocation = waypointProvider.getNextWaypoint();
			}
		}
		
	}
	
	
	
	private double getDistance(Waypoint waypoint1, Waypoint waypoint2) {
		double distance = 0;
		
		distance = Math.sqrt(Math.pow(waypoint1.getX(), 2) + Math.pow(waypoint1.getY(), 2)) ;
		//pathagaras theorem- a^2 +b^2 = c^2
		return distance;
	}
	
	private double getAngle(Waypoint waypoint1, Waypoint waypoint2) {
		double angle = 0;
		
		angle = Math.atan2(waypoint2.getY()-waypoint1.getY(), waypoint2.getX()-waypoint1.getX());
		
		return angle;
	}
}
