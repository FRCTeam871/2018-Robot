package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.robot.Robot;

public interface IAction {
	
	public boolean isComplete();
	
	public void abort();
	
	public void execute();
	
	public void init(Robot robot);
}
