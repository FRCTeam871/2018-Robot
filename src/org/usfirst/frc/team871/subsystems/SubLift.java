package org.usfirst.frc.team871.subsystems;

import org.usfirst.frc.team871.util.PIDControl;
import org.usfirst.frc.team871.util.control.LimitedSpeedController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class SubLift {
	private LimitedSpeedController liftMotor;
	private DigitalInput upperLimit;
	private DigitalInput lowerLimit;
	private Encoder encoder;
	private PIDControl pid;
	
	public SubLift(LimitedSpeedController liftMotor) {
		this.liftMotor = liftMotor;
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
	
	public void setHeight(double setPoint) {
		double error = encoder.getDistance() - setPoint;
	}
	
}