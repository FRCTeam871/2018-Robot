package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.robot.Robot;

public class PlaceOnSwitchAction implements IAction{
	boolean isDone;
	
	boolean isAtSetpoint = false;
	
	@Override
	public boolean isComplete() {
		
		return isDone;
	}

	@Override
	public void abort() {
		isDone = true;
	}

	@Override
	public void execute() {
		
		//TODO: set isAtSetpoint from Lift in Robot
		
		if(isAtSetpoint) {
			//TODO: clamp.release
			
			isDone = true;
		}
		
	}

	@Override
	public void init(Robot robot) {
		isDone = false;
		
		//TODO: SET LIFT HEIGHT TO 1ft 6-3/4in + error (an inch?)
	}

}
