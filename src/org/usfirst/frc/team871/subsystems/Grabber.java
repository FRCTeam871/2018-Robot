package org.usfirst.frc.team871.subsystems;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * Grabber class that is used for controlling the grabber. 
 * @author Team871
 *
 */
public class Grabber {
	private DoubleSolenoid grabPiston; 
	private DoubleSolenoid ejectPiston;
	private DigitalInput cubeSensor;
	
	/**
	 * Initializes the pistons and sensors that are used in the grabber 
	 * @param grabPiston The piston that opens and closes the grabber
	 * @param ejectPiston The piston ejects out the cubes
	 * @param cubeSensor A sensor that senses whether or not a cube is in the grabber
	 */
	public Grabber(DoubleSolenoid grabPiston, DoubleSolenoid ejectPiston, DigitalInput cubeSensor) {
		this.grabPiston = grabPiston;
		this.ejectPiston = ejectPiston;
		this.cubeSensor = cubeSensor;
		
		grabPiston.setName("Grabber", "Grab Piston");
		ejectPiston.setName("Grabber", "Eject Piston");
		cubeSensor.setName("Grabber", "Cube Sensor");
		
		LiveWindow.add(grabPiston);
		LiveWindow.add(ejectPiston);
		LiveWindow.add(cubeSensor);
	}
	
	/**
	 * Toggles the state of the grabber
	 */
	public void toggleGrabber() {
		grabPiston.set(grabPiston.get() == Value.kReverse ? Value.kForward : Value.kReverse);
	}
	
	/**
	 * Sets the state of the grabber
	 * @param grab If true the grabber state is set to grab, If false the grabber is open
	 */
	public void setGrab(boolean grab) {
		grabPiston.set(grab ? Value.kForward : Value.kReverse);
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
		ejectPiston.set(Value.kForward);
		Timer tim = new Timer();
		tim.schedule(new TimerTask() {
			public void run() {
				ejectPiston.set(Value.kReverse);
			}
		}, 500);
	}
}