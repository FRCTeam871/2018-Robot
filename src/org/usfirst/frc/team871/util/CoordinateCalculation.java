package org.usfirst.frc.team871.util;

import org.usfirst.frc.team871.subsystems.navigation.Coordinate;
import org.usfirst.frc.team871.subsystems.navigation.Waypoint;

public class CoordinateCalculation {

	public CoordinateCalculation(){
		
	}

	/**
	 * Gets distance between two waypoints 
	 * @param waypoint1 First waypoint
	 * @param waypoint2 Second Waypoint
	 * @return
	 */
	public double getDistance(Waypoint waypoint1, Waypoint waypoint2) {
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
	public double getAngle(Waypoint waypoint1, Waypoint waypoint2) {
		double angle = 0;
		//the x and y arguments are reversed because of the coordinate transformation between the gyro angle and the field coordinate system
		angle = Math.atan2(waypoint2.getX()-waypoint1.getX(), waypoint2.getY()-waypoint1.getY());
		
		return angle;
	}
	
	
	public double getResultant(Coordinate coordinate) {
		return getResultant(coordinate.getX(), coordinate.getY());
	}
	
	public double getResultant(double xComponent, double yComponnent) {
		return  Math.sqrt(Math.pow(xComponent, 2) + Math.pow(yComponnent, 2));
	}
	
}
