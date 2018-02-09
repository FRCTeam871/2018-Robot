package org.usfirst.frc.team871.util.config;

import org.usfirst.frc.team871.util.sensor.DigitalLimitSwitch;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Used to fetch robot components. Implementations of this interface can be swapped out for different robots
 * @author Team871
 *
 */
public interface IRobotConfiguration {	
	/**
	 * @return The front left motor of the robot
	 */
	SpeedController getFrontLeftMotor();

	/**
	 * @return The rear left motor of the robot
	 */
	SpeedController getRearLeftMotor();

	/**
	 * @return The front right motor of the robot
	 */
	SpeedController getFrontRightMotor();

	/**
	 * @return The rear right motor of the robot
	 */
	SpeedController getRearRightMotor();

	/**
	 * @return The upper lift motor of the robot
	 */
	SpeedController getLiftMotorUp();

	/**
	 * @return The lower lift motor of the robot
	 */
	SpeedController getLiftMotorBtm();

	/**
	 * @return The gyroscope of the robot
	 */
	AHRS getGyroscope();

	/**
	 * @return The cube detecting sensor of the robot
	 */
	DigitalInput getCubeDetector();

	/**
	 * @return The upper limit sensor of the upper lift of the robot
	 */
	DigitalLimitSwitch getupperUpperLimit();

	/**
	 * @return The lower limit sensor of the upper lift of the robot
	 */
	DigitalLimitSwitch getupperLowerLimit();

	/**
	 * @return The upper limit sensor of the lower lift of the robot
	 */
	DigitalLimitSwitch getlowerUpperLimit();

	/**
	 * @return The lower limit sensor of the lower lift of the robot
	 */
	DigitalLimitSwitch getlowerLowerLimit();

	/**
	 * @return The encoder sensor of the upper lift of the robot
	 */
	Encoder getEncoderUp();

	/**
	 * @return The encoder sensor of the lower lift of the robot
	 */
	Encoder getEncoderBtm();

	/**
	 * @return The piston of the grabber of the robot
	 */
	DoubleSolenoid getGrabPiston();

	/**
	 * @return The eject piston of the grabber of the robot
	 */
	DoubleSolenoid getEjectPiston();
}
