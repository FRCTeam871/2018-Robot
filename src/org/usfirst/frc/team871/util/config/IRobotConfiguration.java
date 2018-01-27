package org.usfirst.frc.team871.util.config;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;

public interface IRobotConfiguration {	
	public SpeedController getFrontLeftMotor();
	public SpeedController getRearLeftMotor();
	public SpeedController getFrontRightMotor();
	public SpeedController getRearRightMotor();
	public SpeedController getLiftMotorUp();	
	public SpeedController getLiftMotorBtm();
	public AHRS getGyroscope();
	public DigitalInput getCubeDetector();
	public Encoder getEncoderUp();
	public Encoder getEncoderBtm();
	public Solenoid getGrabPiston();
	public Solenoid getEjectPiston();
	
	
}
