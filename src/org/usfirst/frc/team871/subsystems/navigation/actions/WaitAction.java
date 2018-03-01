package org.usfirst.frc.team871.subsystems.navigation.actions;

public class WaitAction implements IAction {

	long start = -1;
	long delay;
	
	public WaitAction(long delay) {
		this.delay = delay;
	}
	
	@Override
	public void init() {

	}

	@Override
	public void execute() {
		if(start == -1) {
			start = System.currentTimeMillis();
		}
	}

	@Override
	public boolean isComplete() {
		return (System.currentTimeMillis() - start) >= delay;
	}

	@Override
	public void abort() {

	}

	@Override
	public void halt() {

	}

}
