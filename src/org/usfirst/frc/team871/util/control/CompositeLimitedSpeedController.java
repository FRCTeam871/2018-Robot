package org.usfirst.frc.team871.util.control;

import java.util.List;

import org.usfirst.frc.team871.util.sensor.ILimitSwitch;

import edu.wpi.first.wpilibj.SpeedController;

public class CompositeLimitedSpeedController implements SpeedController {

	private SpeedController motor;
	
	private List<ILimitSwitch> upperLimitss;
	private List<ILimitSwitch> lowerLimitss;
	
	
	public CompositeLimitedSpeedController(SpeedController motor, List<ILimitSwitch> upperLimitss, List<ILimitSwitch> lowerLimitss, boolean inverted) {
		this.motor = motor;
		this.upperLimitss = upperLimitss;
		this.lowerLimitss = lowerLimitss;
		
		motor.setInverted(inverted);
	}
	
	public CompositeLimitedSpeedController(SpeedController motor, List<ILimitSwitch> upperLimitss, List<ILimitSwitch> lowerLimitss) {
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
	
	public List<ILimitSwitch> getUpperSensor() {
		return upperLimitss;
	}
	
	public List<ILimitSwitch> getLowerSensor() {
		return lowerLimitss;
	}

	public boolean isAtUpperLimit() {
		boolean upperLimit = false;
		for(ILimitSwitch upperSwitch : upperLimitss) {
			upperLimit |= upperSwitch.isAtLimit();
		}
		return upperLimit;
	}
	
	public boolean isAtLowerLimit() {
		boolean lowerLimit = false;
		for(ILimitSwitch lowerSwitch : lowerLimitss) {
			lowerLimit |= lowerSwitch.isAtLimit();
		}
		return lowerLimit;
	}
	
}