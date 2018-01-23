package org.usfirst.frc.team871.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class DriveTrain extends MecanumDrive{
	
	private final AHRS gyro;
	
	public DriveTrain(SpeedController rearRight, SpeedController rearLeft, SpeedController frontRight, SpeedController frontLeft, AHRS gyro ) {
		super(frontLeft, rearLeft, frontRight, rearRight);
		this.gyro = gyro;
	}
	
	public void driveFieldOriented(double x, double y, double r) {
		driveCartesian(y, x, r, gyro.getAngle());
	}
	
	public void driveRobotOriented(double x, double y, double r) {
		driveCartesian(y, x, r);
	}
	
	public void resetGyro() {
		gyro.zeroYaw();
	}
}