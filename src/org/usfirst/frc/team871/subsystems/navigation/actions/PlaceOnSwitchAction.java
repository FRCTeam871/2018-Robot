package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.robot.Robot;

public class PlaceOnSwitchAction implements IAction{
	private boolean isDone;
	private Robot robot;
	
	private boolean isAtSetpoint = false;
	
	private final double SWITCH_HEIGHT;
	private final double SETPOINT;
	
	public PlaceOnSwitchAction() {
		SWITCH_HEIGHT = 18.75;
		
		SETPOINT =  SWITCH_HEIGHT + 3;// 3 inch error;
	}
	
	@Override
	public void init(Robot robot) {
		this.isDone = false;
		this.robot = robot;
		
		robot.getGrabber().setGrab(true);//make sure cube is grabbed
		robot.getLift().setHeight(SWITCH_HEIGHT);
	}
	
	@Override
	public boolean isComplete() { //are we done yet
		return isDone;
	}

	@Override
	public void abort() { //hard stop
		isDone = true;
	}
	
	@Override
	public void halt() { //soft stop
		
		//wait a certain amount of time to see if we can complete then execute
		isDone = true;
	}

	@Override
	public void execute() {
		
		//TODO: set isAtSetpoint from Lift in Robot
		
		if(robot.getLift().isAtSetpoint(SETPOINT)) {
			isAtSetpoint = true;
		}
		
		if(isAtSetpoint) {
			
			robot.getGrabber().setGrab(false);//release cube
			
			isDone = true;
		}
		
	}

}
