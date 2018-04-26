package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.subsystems.Grabber;

public class ImmediateSetGrabberAction implements IAction {

	private final Grabber grabber;
	private final boolean grab;
	private final long wait;

	public ImmediateSetGrabberAction(Grabber grabber, boolean grab, long wait) {
		this.grabber = grabber;
		this.grab = grab;
		this.wait = wait;
	}
	
	@Override
	public void init() {
		new Thread(() -> {
			try{
				Thread.sleep(wait); 
			} catch(Exception e) {}
			
			grabber.setGrab(grab);
		}).start();
	}

	@Override
	public void execute() {
		
	}

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public void abort() {
		
	}

	@Override
	public void halt() {
		
	}

	@Override
	public String toString() {
		return "{ Immediate " + (grab ? "Grab " : "Release ") + " }";
	}
}
