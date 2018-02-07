package org.usfirst.frc.team871.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * Drive class used for driving mecanum drive with additional features.
 * @author Not Jack Langhorn
 */
public class DriveTrain extends MecanumDrive implements PIDOutput {
	
	private PIDController headingPID;
	private double pidRotation = 0;
	private final AHRS gyro;
	
	/**
	 * 	Initializes the gyro and drive motors with the SpeedControllers
	 * @param rearRight The rear right speed controller
	 * @param rearLeft The rear left speed controller
	 * @param frontRight The front right speed controller
	 * @param frontLeft The front left speed controller
	 * @param gyro A NavX that is used for field oriented driving 
	 */
	public DriveTrain(SpeedController rearRight, SpeedController rearLeft, SpeedController frontRight, SpeedController frontLeft, AHRS gyro ) {
		super(frontLeft, rearLeft, frontRight, rearRight);
		this.gyro = gyro;
		
		headingPID = new PIDController(0, 0, 0, gyro, this);
		headingPID.setInputRange(-180, 180);
		headingPID.setOutputRange(-1, 1);
		headingPID.setContinuous();
		
		setName("DriveTrain", "Main Drive");
		headingPID.setName("DriveTrain", "Heading PID");
		LiveWindow.add(headingPID);
		LiveWindow.add(this);
	}
	
	/**
	 * Drives in any direction relative to the field
	 * @param x Drives along the X axis 
	 * @param y Drives along the Y axis
	 * @param r Rotation of the Robot 
	 */
	public void driveFieldOriented(double x, double y, double r) {
		driveCartesian(y, x, r + (headingPID.isEnabled() ? pidRotation : 0), gyro.getAngle());
	}
	
	/**
	 * Drives in any direction relative to the robot
	 * @param x Drives along the X axis
	 * @param y Drives along the Y axis
	 * @param r Rotation of the Robot
	 */
	public void driveRobotOriented(double x, double y, double r) {
		driveCartesian(y, x, r + (headingPID.isEnabled() ? pidRotation : 0));
	}
	
	public void setHeadingPIDEnabled(boolean enabled) {
		headingPID.setEnabled(enabled);
	}
	
	public void resetGyro() {
		gyro.zeroYaw();
	}
	
	@Override
	public void pidWrite(double output) {
		pidRotation = output;
	}
	
	/**
	 * @param heading - the heading hold, in degrees
	 */
	public void setHeadingHold(double heading) {
		headingPID.setSetpoint(heading);
	}
	
	public void getHeadingHold() {
		headingPID.getSetpoint();
	}
	
}