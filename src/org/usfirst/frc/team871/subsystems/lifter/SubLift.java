package org.usfirst.frc.team871.subsystems.lifter;

import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * This class controls one part of the lift
 * @author Team871
 */
public class SubLift {
	
	private static final double MAX_VELOCITY = 6; //inches per second
	private CompositeLimitedSpeedController liftMotor;
	private Encoder encoder;
	private PIDController pidDisplacement;
	private PIDController pidRate; // TODO: make the displacement one use velocity
	
	private ControlMode currentMode = null;
	
	/**
	 * Controls the bottom part of the lift
	 * @param liftMotor the motor that controls the lift 
	 * @param encoder Measures the distance of the bottom part of the lift
	 */
	protected SubLift(String name, CompositeLimitedSpeedController liftMotor, Encoder encoder) {
		this.liftMotor = liftMotor;
		this.encoder = encoder;
		
		this.encoder.setPIDSourceType(PIDSourceType.kDisplacement);
		
		pidDisplacement = new PIDController(0, 0, 0, encoder, liftMotor);
		pidRate = new PIDController(0.2360, 0.000420, 0.0666, encoder, liftMotor);
		pidRate.setOutputRange(-1, 1);
		pidRate.setInputRange(-MAX_VELOCITY, MAX_VELOCITY);
		pidRate.disable();
		
		pidDisplacement.setName("Lifter", name+" - Displacement PID");
		pidRate.setName("Lifter", name+" - RatePid");
		liftMotor.setName("Lifter", name+" - Motor");
		encoder.setName("Lifter", name+"- Encoder");
		
		LiveWindow.add(pidDisplacement);
		LiveWindow.add(pidRate);
		LiveWindow.add(liftMotor);
		LiveWindow.add(encoder);
	}
	
	private void ensureMode(ControlMode mode) {
		if(currentMode == mode) {
			return;
		}
		
		currentMode = mode;
		switch(mode) {
			case Position:
				pidRate.disable();
				pidDisplacement.enable();
				break;
			case Velocity:
				pidDisplacement.disable();
				pidRate.enable();			
				break;
		}
	}
	
	/**
	 * Controls the speed of the lift.<br>
	 * <marquee>Disables the PID.</marquee>
	 * @param speed How fast the lift moves (-1 to 1)
	 */
	protected void moveLift(double speed) {
		ensureMode(ControlMode.Velocity);
		pidRate.setSetpoint(speed * MAX_VELOCITY);
		maybeResetEncoder();
	}
	
	/**
	 * Resets the encoder if it's at the lower limit.
	 * @return returns true if encoder is successfully reset
	 */
	protected boolean maybeResetEncoder() {
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
		ensureMode(ControlMode.Position);
		pidDisplacement.setSetpoint(setPoint);
	}
}