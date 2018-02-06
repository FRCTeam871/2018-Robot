package org.usfirst.frc.team871.subsystems.lifter;

import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;

/**
 * This class controls one part of the lift
 * @author Team871
 */
public class SubLift {
	private CompositeLimitedSpeedController liftMotor;
	private Encoder encoder;
	private PIDController pidPosition;
	
	/**
	 * Controls the bottom part of the lift
	 * @param liftMotor the motor that controls the lift 
	 * @param encoder Measures the distance of the bottom part of the lift
	 */
	protected SubLift(CompositeLimitedSpeedController liftMotor, Encoder encoder) {
		this.liftMotor = liftMotor;
		this.encoder = encoder;
		
		pidPosition = new PIDController(0, 0, 0, encoder, liftMotor);
	}
	
	/**
	 * Controls the speed of the lift.<br>
	 * <marquee>Disables the PID.</marquee>
	 * @param speed How fast the lift moves
	 */
	protected void moveLift(double speed) {
		pidPosition.disable();
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
		pidPosition.enable();
		pidPosition.setSetpoint(setPoint);
	}
	
	/**
	 * Enables and disables the PID.
	 */
	protected void setEnablePID(boolean enable) {
		pidPosition.setEnabled(enable);
	}
	
}