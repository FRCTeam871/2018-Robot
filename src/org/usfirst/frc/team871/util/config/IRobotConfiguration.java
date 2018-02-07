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
	 * The front left motor of the robot
	 * @return
	 */
	public SpeedController getFrontLeftMotor();
	/**
	 * The rear left motor of the robot
	 * @return
	 */
	public SpeedController getRearLeftMotor();
	/**
	 * The front right motor of the robot
	 * @return
	 */
	public SpeedController getFrontRightMotor();
	/**
	 * The rear right motor of the robot
	 * @return
	 */
	public SpeedController getRearRightMotor();
	/**
	 * The upper lift motor of the robot
	 * @return
	 */
	public SpeedController getLiftMotorUp();	
	/**
	 * The lower lift motor of the robot
	 * @return
	 */
	public SpeedController getLiftMotorBtm();
	/**
	 * The gyroscope of the robot
	 * @return
	 */
	public AHRS getGyroscope();
	/**
	 * The cube detecting sensor of the robot
	 * @return
	 */
	public DigitalInput getCubeDetector();
	/**
	 * The upper limit sensor of the upper lift of the robot
	 * @return
	 */
	public DigitalLimitSwitch getupperUpperLimit();
	/**
	 * The lower limit sensor of the upper lift of the robot
	 * @return
	 */
	public DigitalLimitSwitch getupperLowerLimit();
	/**
	 * The  upper limit sensor of the lower lift of the robot
	 * @return
	 */
	public DigitalLimitSwitch getlowerUpperLimit();
	/**
	 * The lower limit sensor of the lower lift of the robot
	 * @return
	 */
	public DigitalLimitSwitch getlowerLowerLimit();
	/**
	 * The encoder sensor of the upper lift of the robot
	 * @return
	 */
	public Encoder getEncoderUp();
	/**
	 * The encoder sensor of the lower lift of the robot
	 * @return
	 */
	public Encoder getEncoderBtm();
	/**
	 * The piston of the grabber of the robot
	 * @return
	 */
	public DoubleSolenoid getGrabPiston();
	/**
	 * The eject piston of the grabber of the robot
	 * @return
	 */
	public DoubleSolenoid getEjectPiston();
	
	
}
