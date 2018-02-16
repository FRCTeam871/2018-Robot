package org.usfirst.frc.team871.subsystems.lifter;

import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * This class controls one part of the lift
 * @author Team871
 */
public class SubLift extends SendableBase implements Sendable {
	
	private static final double MAX_VELOCITY = 6; //inches per second
	private CompositeLimitedSpeedController liftMotor;
	private Encoder encoder;
	private PIDController pid;
	private ControlMode currentMode = ControlMode.Startup;
	
	/**
	 * Controls the bottom part of the lift
	 * @param liftMotor the motor that controls the lift 
	 * @param encoder Measures the distance of the bottom part of the lift
	 */
	SubLift(String name, CompositeLimitedSpeedController liftMotor, Encoder encoder) {
		this.liftMotor = liftMotor;
		this.encoder = encoder;

		pid = new PIDController(0, 0, 0, encoder, liftMotor);
		pid.setOutputRange(-1, 1);
		pid.disable();
		ensureMode(ControlMode.Position);

		pid.setName("SuperLift", "PID-" + name);
		
		LiveWindow.add(pid);
//		addChild(pid);
//		addChild(liftMotor);
//		addChild(encoder);
		setName("Lifter-"+name);
	}
	
	private void ensureMode(ControlMode mode) {
		if(currentMode == mode) {
			return;
		}
		
		currentMode = mode;
		pid.disable();
		switch(mode) {
			case Position:
			    pid.setPID(-0.12, 0, 0.04);
			    pid.setInputRange(0, 0);
                encoder.setPIDSourceType(PIDSourceType.kDisplacement);
				break;
			default:
				break;
		}
		pid.enable();
	}
	
	/**
	 * Resets the encoder if it's at the lower limit.
	 * @return returns true if encoder is successfully reset
	 */
	private boolean maybeResetEncoder() {
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
		pid.setSetpoint(setPoint);
//		maybeResetEncoder();
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("SubLift");
		builder.setSafeState(() -> {
			pid.disable();
			liftMotor.stopMotor();
		});
		
		builder.addStringProperty("Mode", currentMode::toString, null);
		builder.addDoubleProperty("error", pid::getError, null);
		builder.addDoubleProperty("SetPoint", pid::getSetpoint, null);
		builder.addDoubleProperty("Position", this::getHeight, null);
	}

	public boolean isAtSetpoint() {
		pid.setAbsoluteTolerance(3);
		return pid.onTarget();
	}
}