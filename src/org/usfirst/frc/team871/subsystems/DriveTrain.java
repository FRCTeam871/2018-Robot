package org.usfirst.frc.team871.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
/**
 * Drive class used for driving mecanum drive with additional features.
 * @author Not Jack Langhorn
 */
public class DriveTrain extends MecanumDrive {
	
	private final AHRS gyro;
	/**
	 * 	Initializes the gyro and drive motors with the SpeedControllers
	 * @param rearRight The rear right speed controller
	 * @param rearLeft The rear left speed controller
	 * @param frontRight The front right speed controller
	 * @param frontLeft The front left speed controller
	 * @param gyro A Navx that is used for field oriented driving 
	 */
	public DriveTrain(SpeedController rearRight, SpeedController rearLeft, SpeedController frontRight, SpeedController frontLeft, AHRS gyro ) {
		super(frontLeft, rearLeft, frontRight, rearRight);
		this.gyro = gyro;
	}
	/**
	 * Drives in any direction relative to the field
	 * @param x Drives along the X axis 
	 * @param y Drives along the Y axis
	 * @param r Rotation of the Robot 
	 */
	public void driveFieldOriented(double x, double y, double r) {
		driveCartesian(y, x, r, gyro.getAngle());
	}
	/**
	 * Drives in any direction relative to the robot
	 * @param x Drives along the X axis
	 * @param y Drives along the Y axis
	 * @param r Rotation of the Robot
	 */
	public void driveRobotOriented(double x, double y, double r) {
		driveCartesian(y, x, r);
	}
	
	public void resetGyro() {
		gyro.zeroYaw();
	}
	
}