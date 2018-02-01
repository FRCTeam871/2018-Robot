package org.usfirst.frc.team871.util.config;

import org.usfirst.frc.team871.util.joystick.POVDirections;

/**
 * Used to fetch control schemes. Implementations of this interface can be swapped out for different schemes
 * @author Team871
 *
 */
public interface IControlScheme {
	
	/**
	 * 
	 * @return Returns true if the toggle orientation button is pressed
	 */
	public boolean getToggleOrientationButton();
	
	/**
	 * 
	 * @return Returns true if the reset gyro button is pressed
	 */
	public boolean getResetGyroButton();
	
	/**
	 * 
	 * @return Returns true if the toggle grabber button is pressed
	 */
	public boolean getToggleGrabberButton();
	
	/**
	 * 
	 * @return Returns true if the cube eject button is pressed
	 */
	public boolean getCubeEjectButton();
	
	/**
	 * 
	 * @return Returns true if the decrease setpoint button is pressed
	 */
	public boolean getDecreaseSetpointButton();
	
	/**
	 * 
	 * @return Returns true if the increase setpoint button is pressed
	 */
	public boolean getIncreaseSetpointButton();
	
	/**
	 * 
	 * @return Returns the lift axis value
	 */
	public double getLiftAxis();
	
	/**
	 * 
	 * @return Returns the x-axis value
	 */
	public double getXAxis();
	
	/**
	 * 
	 * @return Returns the y-axis value
	 */
	public double getYAxis();
	
	/**
	 * 
	 * @return Returns the rotation axis value
	 */
	public double getRotationAxis();
	
	/**
	 * 
	 * @return Returns the POV direction as a POVDirection
	 */
	public POVDirections getPOV();
		
}