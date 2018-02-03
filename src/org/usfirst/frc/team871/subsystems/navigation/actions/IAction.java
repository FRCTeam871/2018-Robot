package org.usfirst.frc.team871.subsystems.navigation.actions;

public interface IAction {

	public boolean isComplete();
	
	public void abort();
	
	public void execute();
	
	public void init();
}
