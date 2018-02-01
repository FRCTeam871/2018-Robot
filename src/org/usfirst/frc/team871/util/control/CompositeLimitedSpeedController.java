package org.usfirst.frc.team871.util.control;

import java.util.ArrayList;

import org.usfirst.frc.team871.util.sensor.ILimitSwitch;

import edu.wpi.first.wpilibj.SpeedController;
/**
 * Uses speed controllers while using microswitches and encoders as limit switches
 * @author Team871
 *
 */
public class CompositeLimitedSpeedController implements SpeedController {

	private SpeedController motor;
	
	private ArrayList<ILimitSwitch> upperLimitss;
	private ArrayList<ILimitSwitch> lowerLimitss;
	
	/**
	 * 
	 * @param motor The speed controller being limited
	 * @param upperLimitss Arraylist of upper limit switches
	 * @param lowerLimitss Arraylist of lower limit switches
	 * @param inverted Determines if the speed controller output should be negated
	 */
	public CompositeLimitedSpeedController(SpeedController motor, ArrayList<ILimitSwitch> upperLimitss, ArrayList<ILimitSwitch> lowerLimitss, boolean inverted) {
		this.motor = motor;
		this.upperLimitss = upperLimitss;
		this.lowerLimitss = lowerLimitss;
		
		motor.setInverted(inverted);
	}
	
	/**
	 * The motor is not inverted by default
	 * @param motor The speed controller being limited
	 * @param upperLimitss Arraylist of upper limit switches
	 * @param lowerLimitss Arraylist of lower limit switches
	 */
	public CompositeLimitedSpeedController(SpeedController motor, ArrayList<ILimitSwitch> upperLimitss, ArrayList<ILimitSwitch> lowerLimitss) {
		this.motor = motor;
		this.upperLimitss = upperLimitss;
		this.lowerLimitss = lowerLimitss;
		
		motor.setInverted(false);
	}
	
	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public double get() {
		return motor.get();
	}
	
	@Override
	public void set(double speed) {
		if(isAtUpperLimit() && speed > 0 || isAtLowerLimit() && speed < 0) {
			motor.set(0);
		} else {
			motor.set(speed);
		}
	}

	@Override
	public void setInverted(boolean isInverted) {
		motor.setInverted(isInverted);
	}

	@Override
	public boolean getInverted() {
		return motor.getInverted();
	}

	@Override
	public void disable() {
		motor.disable();
	}

	@Override
	public void stopMotor() {
		motor.stopMotor();
	}
	
	/**
	 * 
	 * @return Returns the upper limit switches in an arrayList
	 */
	public ArrayList<ILimitSwitch> getUpperSensor() {
		return upperLimitss;
	}
	
	/**
	 * 
	 * @return Returns the lower limit switches in an arrayList
	 */
	public ArrayList<ILimitSwitch> getLowerSensor() {
		return lowerLimitss;
	}

	/**
	 * 
	 * @return Returns true if an upperLimit has been reached
	 */
	public boolean isAtUpperLimit() {
		boolean upperLimit = false;
		for(ILimitSwitch upperSwitch : upperLimitss) {
			upperLimit |= upperSwitch.isAtLimit();
		}
		return upperLimit;
	}
	
	/**
	 * 
	 * @return Returns true if a lowerLimit has been reached
	 */
	public boolean isAtLowerLimit() {
		boolean lowerLimit = false;
		for(ILimitSwitch lowerSwitch : lowerLimitss) {
			lowerLimit |= lowerSwitch.isAtLimit();
		}
		return lowerLimit;
	}
	
}