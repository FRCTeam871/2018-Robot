package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.robot.Robot;
import org.usfirst.frc.team871.subsystems.Grabber;

import edu.wpi.first.wpilibj.Timer;

public class SetGrabberAction implements IAction {

	private Grabber grabber;
	private boolean grab;
	private long startTime = 0;
	private long wait = 0;
	
	public SetGrabberAction(Grabber grabber, boolean grab, long wait) {
		this.grabber = grabber;
		this.grab = grab;
		this.wait = wait;
	}
	
	public SetGrabberAction(Grabber grabber, boolean grab) {
		this(grabber, grab, 500);
	}
	
	@Override
	public void init(Robot robot, Timer timer) {
		
	}

	@Override
	public void execute() {
		if(startTime == 0) {
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

}
