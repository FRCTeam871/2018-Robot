package org.usfirst.frc.team871.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
/**
 * Grabber class that is used for controlling the grabber. 
 * @author Team871
 *
 */
public class Grabber {
	private Solenoid grabPiston; 
	private Solenoid ejectPiston;
	private DigitalInput cubeSensor;
	/**
	 * Initializes the pistons and sensors that are used in the grabber 
	 * @param grabPiston The piston that opens and closes the grabber
	 * @param ejectPiston The piston ejects out the cubes
	 * @param cubeSensor A sensor that senses whether or not a cube is in the grabber
	 */
	public Grabber(Solenoid grabPiston, Solenoid ejectPiston, DigitalInput cubeSensor) {
		this.grabPiston = grabPiston;
		this.ejectPiston = ejectPiston;
		this.cubeSensor = cubeSensor;
	}
	/**
	 * Toggles the state of the grabber
	 */
	public void toggleGrabber() {
		grabPiston.set(!grabPiston.get());
	}
	/**
	 * Sets the state of the grabber
	 * @param grab If true the grabber state is set to grab, If false the grabber is open
	 */
	public void setGrab(boolean grab) {
		grabPiston.set(!grab);
	}
	/**
	 * The cubeSensor senses whether or not a cube is in the grabber
	 * @return returns the value of the cubeSensor
	 */
	public boolean hasCube() {
		return cubeSensor.get();
	}
	/**
	 * The grabber ejects the cube and then retracts the piston
	 */
	//open it up, push it out, retract the piston
	public void ejectCube() {
		toggleGrabber();
		ejectPiston.set(true);
		Timer tim = new Timer();
		tim.schedule(new TimerTask() {
			public void run() {
				ejectPiston.set(false);
			}
		}, 500);
	}
}