package org.usfirst.frc.team871.subsystems.navigation;

public interface IDisplacementSensor {

	/**
	 * @return total displacement since start. In meters
	 */
	public Coordinate getDisplacement_m();
	
	/**
	 * @return total displacement since start. In inches
	 */
	public Coordinate getDisplacement_in();
	
	/**
	 * @return velocity in m/s
	 */
	public Coordinate getVelocity();
}
