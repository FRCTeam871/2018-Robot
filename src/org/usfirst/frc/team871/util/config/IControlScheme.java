package org.usfirst.frc.team871.util.config;

import org.usfirst.frc.team871.util.joystick.POVDirections;

/**
 * Used to fetch control schemes. Implementations of this interface can be swapped out for different schemes
 * @author Team871
 *
 */
public interface IControlScheme {
	
	/**
	 * @return Returns true if the toggle orientation button is pressed
	 */
	boolean getToggleOrientationButton();
	
	/**
	 * @return Returns true if the reset gyro button is pressed
	 */
	boolean getResetGyroButton();
	
	/**
	 * @return Returns true if the toggle grabber button is pressed
	 */
	boolean getToggleGrabberButton();
	
	/**
	 * @return Returns true if the cube eject button is pressed
	 */
	boolean getCubeEjectButton();
	
	/**
	 * @return Returns true if the decrease setpoint button is pressed
	 */
	boolean getDecreaseSetpointButton();
	
	/**
	 * @return Returns true if the increase setpoint button is pressed
	 */
	boolean getIncreaseSetpointButton();
	
	boolean getManualLiftModeButton();
	
	/**
	 * @return Returns the lift axis value
	 */
	double getLiftAxis();
	
	/**
	 * @return Returns the x-axis value
	 */
	double getXAxis();
	
	/**
	 * @return Returns the y-axis value
	 */
	double getYAxis();
	
	/**
	 * @return Returns the rotation axis value
	 */
	double getRotationAxis();
	
	/**
	 * @return Returns the POV direction as a POVDirection
	 */
	POVDirections getPOV();
	
	boolean toottoot();
	
	double getUpperLiftTrim();
	
	double getLowerLiftTrim();
	
	boolean getKickButton();
	
}