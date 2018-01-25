package org.usfirst.frc.team871.subsystems;

import org.usfirst.frc.team871.util.control.LimitedSpeedController;
import org.usfirst.frc.team871.util.sensor.DigitalLimitSwitch;

import edu.wpi.first.wpilibj.Encoder;

public class SubLift {
	private Encoder encoder;
	private LimitedSpeedController liftMotor;
	
	public SubLift(Encoder encoder, LimitedSpeedController liftMotor) {
		this.encoder = encoder;
		this.liftMotor = liftMotor;
	}
	
	public void moveLift(double speed) {
		
	}
}