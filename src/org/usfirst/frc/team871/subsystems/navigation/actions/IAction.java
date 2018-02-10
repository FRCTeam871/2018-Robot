package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.robot.Robot;

public interface IAction {
	
	/**
	 * @param robot object so that the Action can read states
	 */
	public void init(Robot robot);
	
	/**
	 * continue performing the Action
	 */
	public void execute();
	
	/**
	 * @return if the Action is complete with its task
	 */
	public boolean isComplete();
	
	/**
	 * Force stop all action (Nuclear Option)
	 */
	public void abort();
	
	/**
	 * Ask the Action to finish up ASAP
	 */
	public void halt();
}
