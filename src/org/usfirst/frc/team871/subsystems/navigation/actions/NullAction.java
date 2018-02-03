package org.usfirst.frc.team871.subsystems.navigation.actions;

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

	@Override
	public void init() {
		dont();		
	}

	
	public void dont() {
		return;
	}
	
}
