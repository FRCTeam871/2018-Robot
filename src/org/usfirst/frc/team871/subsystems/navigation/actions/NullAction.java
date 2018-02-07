package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.robot.Robot;

public class NullAction implements IAction{
	

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public void abort() {	
		
	}

	@Override
	public void execute() {	
		dont();
	}
	
	public void dont() {
		return;
	}

	@Override
	public void init(Robot robot) {
		dont();
		
	}
	
}
