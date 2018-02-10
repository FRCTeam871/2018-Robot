package org.usfirst.frc.team871.subsystems.navigation;

import org.usfirst.frc.team871.subsystems.navigation.actions.IAction;
import org.usfirst.frc.team871.subsystems.navigation.actions.NullAction;

/**
 * 
 * @author Team871
 * Contains parameters for navigation to a single point.
 * Can include an Action to perform at said single point. 
 */
public class Waypoint extends Coordinate{

	private double speed;
	private double angle;
	private IAction action;
	private boolean isSpeedConstant;
	
	public Waypoint(double xVal, double yVal, double angle, double speed, IAction action) {
		super(xVal, yVal);
		this.speed = speed;
		this.angle = angle;
		this.action = action;
	
	}
	
	public Waypoint(double xVal, double yVal, double angle, double speed) {
		this(xVal, yVal, angle, speed, new NullAction());
	}
	
	public Waypoint(double xVal, double yVal, double angle) {
		this(xVal, yVal, angle, 1.0, new NullAction());
	}
	
	public double getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getAngle() {
		return this.angle;
	}

	public void setAngle(double angle) {
		
	}
	
	public void setAction(IAction action) {
		this.action = action;
	}
	
	public IAction getAction() {
		return this.action;
	}
	
	public boolean getIsSpeedConstant() {
		return this.isSpeedConstant;
	}
	
}