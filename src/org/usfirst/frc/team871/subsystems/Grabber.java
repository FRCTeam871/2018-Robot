package org.usfirst.frc.team871.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

public class Grabber {
	private Solenoid grabPiston; 
	private Solenoid ejectPiston;
	private DigitalInput cubeSensor;
	
	public Grabber(Solenoid grabPiston, Solenoid ejectPiston, DigitalInput cubeSensor) {
		this.grabPiston = grabPiston;
		this.ejectPiston = ejectPiston;
		this.cubeSensor = cubeSensor;
	}
	
	public void toggleGrabber() {
		grabPiston.set(!grabPiston.get());
	}
	
	public void setGrab(boolean grab) {
		grabPiston.set(!grab);
	}
	
	public boolean hasCube() {
		return cubeSensor.get();
	}
	
	public void ejectCube() {
		ejectPiston.set(true);
		Timer tim = new Timer();
		tim.schedule(new TimerTask() {
			public void run() {
				ejectPiston.set(false);
			}
		}, 500);
	}
}