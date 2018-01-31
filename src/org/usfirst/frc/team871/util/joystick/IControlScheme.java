package org.usfirst.frc.team871.util.joystick;

public interface IControlScheme {
	
	public boolean getToggleOrientationButton();
	public boolean getResetGyroButton();
	public boolean getToggleGrabberButton();
	public boolean getCubeEjectButton();
	public boolean getDecreaseSetpointButton();
	public boolean getIncreaseSetpointButton();
	public double getLiftAxis();
	public double getXAxis();
	public double getYAxis();
	public double getRotationAxis();
	public POVDirections getPOV();
	
	
}
