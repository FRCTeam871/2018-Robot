package org.usfirst.frc.team871.subsystems;


import org.usfirst.frc.team871.robot.CompositeLimitedSpeedController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;

public class SubLift {
	private CompositeLimitedSpeedController liftMotor;
	private Encoder encoder;
	private PIDController pid;
	
	public SubLift(CompositeLimitedSpeedController liftMotor, Encoder encoder) {
		this.liftMotor = liftMotor;
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
		if(liftMotor.isAtLowerLimit()) {
			encoder.reset();
			return true;
		} else {
			liftMotor.set(-1);
			return false;
		}
	}
	
	public int getHeight() {
		return (int) encoder.getDistance();
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
	public void setEnablePID(boolean enable) {
		
		if(enable) {
			pid.enable();
		}else {
			pid.disable();
		}
		
	}
	
}