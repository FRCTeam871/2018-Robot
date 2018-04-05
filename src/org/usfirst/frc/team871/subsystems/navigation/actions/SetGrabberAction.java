package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.subsystems.Grabber;

public class SetGrabberAction implements IAction {

	private final Grabber grabber;
	private final long wait;
	private final boolean grab;

	private long startTime = 0;

	public SetGrabberAction(Grabber grabber, boolean grab, long wait) {
		this.grabber = grabber;
		this.grab = grab;
		this.wait = wait;
	}
	
	public SetGrabberAction(Grabber grabber, boolean grab) {
		this(grabber, grab, 500);
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void execute() {
		if(startTime == 0) {
			System.out.print(toString() + " -- Starting");
			startTime = System.currentTimeMillis();
		}
		
		grabber.setGrab(grab);
	}

	@Override
	public boolean isComplete() {
		return System.currentTimeMillis() > (startTime + wait);
	}

	@Override
	public void abort() {
		
	}

	@Override
	public void halt() {
		
	}

	@Override
	public String toString() {
		return "{ " + (grab ? "Grab " : "Release ") + " and wait " + wait + " }";
	}
}
