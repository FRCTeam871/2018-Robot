package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.robot.Robot;

public class NullAction implements IAction{
	

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public void abort() {	
		dont();
	}

	@Override
	public void execute() {	
		dont();
	}

	@Override
	public void init(Robot robot) {
		dont();
		
	}

	@Override
	public void halt() {	
		dont();
	}
	
	/**
	 * It don'ts
	 */
	public void dont() {
		return;
	}
	
}
