package org.usfirst.frc.team871.subsystems;

import org.usfirst.frc.team871.util.PIDControl;
import org.usfirst.frc.team871.util.control.LimitedSpeedController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;

public class SubLift {
	private LimitedSpeedController liftMotor;
	private DigitalInput upperLimit;
	private DigitalInput lowerLimit;
	private Encoder encoder;
	private PIDController pid;
	
	public SubLift(LimitedSpeedController liftMotor, DigitalInput upperLimit, DigitalInput lowerLimit, Encoder encoder) {
		this.liftMotor = liftMotor;
		this.upperLimit = upperLimit;
		this.lowerLimit = lowerLimit;
		this.encoder = encoder;
		
		pid = new PIDController(1,1,1, encoder, liftMotor);
	}
	
	public void moveLift(double speed) {
		liftMotor.set(speed);
	}
	
	/**
	 * lowers the lift to the bottom then recalibrates the encoder
	 * 
	 * @return returns true if encoder is successfully reset
	 * 
	 */
	public boolean resetEncoder() {
		if(lowerLimit.get()) {
			encoder.reset();
			return true;
		} else {
			liftMotor.set(-1);
			return false;
		}
	}
	/**
	 * It used to set the set point of the lifter. This is a value in inches.
	 */
	public void setHeight(double setPoint) {
		pid.setSetpoint(setPoint);
		
	}
	/**
	 * Enables and disables the PID. 
	 */
	public void setEnable(boolean enable) {
		
		if(enable) {
			pid.enable();
		}else {
			pid.disable();
		}
		
	}
	
}