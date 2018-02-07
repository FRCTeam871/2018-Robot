package org.usfirst.frc.team871.subsystems.lifter;

import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * This class controls one part of the lift
 * @author Team871
 */
public class SubLift {
	
	private static final double MAX_VELOCITY = 1; //inches per second
	private CompositeLimitedSpeedController liftMotor;
	private Encoder encoder;
	private PIDController pidDisplacement;
	private PIDController pidRate;
	
	/**
	 * Controls the bottom part of the lift
	 * @param liftMotor the motor that controls the lift 
	 * @param encoder Measures the distance of the bottom part of the lift
	 */
	protected SubLift(CompositeLimitedSpeedController liftMotor, Encoder encoder) {
		this.liftMotor = liftMotor;
		this.encoder = encoder;
		
		this.encoder.setPIDSourceType(PIDSourceType.kDisplacement);
		
		// diam = 1.6
		// circum = diam * PI
		// ticksPerPulse = 256
		// distPerPulse = circum / ticksPerPulse
		this.encoder.setDistancePerPulse(0.019634954084936);
		
		pidDisplacement = new PIDController(0, 0, 0, encoder, liftMotor);
		pidRate = new PIDController(0, 0, 0, encoder, liftMotor);
		pidRate.disable();
	}
	
	/**
	 * Controls the speed of the lift.<br>
	 * <marquee>Disables the PID.</marquee>
	 * @param speed How fast the lift moves (-1 to 1)
	 */
	protected void moveLift(double speed) {
		pidDisplacement.disable();
		pidRate.enable();
		pidRate.setSetpoint(speed * MAX_VELOCITY);
		resetEncoder();
	}
	
	/**
	 * Resets the encoder if it's at the lower limit.
	 * @return returns true if encoder is successfully reset
	 */
	protected boolean resetEncoder() {
		if(liftMotor.isAtLowerLimit()) {
			encoder.reset();
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the height of the encoder.
	 * @return returns the distance of the encoder
	 */
	protected double getHeight() {
		return encoder.getDistance();
	}
	
	/**
	 * Gets the velocity of the encoder.
	 * @return returns the velocity of the encoder
	 */
	protected double getVelocity() {
		return encoder.getRate();
	}
	
	/**
	 * It used to set the set point of the lifter. This is a value in inches.<br>
	 * <marquee>Enables the PID.</marquee>
	 */
	protected void setHeight(double setPoint) {
		pidDisplacement.enable();
		pidDisplacement.setSetpoint(setPoint);
	}
	
	/**
	 * Enables and disables the PID.
	 */
	protected void setEnablePID(boolean enable) {
		pidDisplacement.setEnabled(enable);
	}
	
}