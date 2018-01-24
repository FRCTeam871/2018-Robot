package org.usfirst.frc.team871.util.config;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/*******************
 * Contains all var*ables of robör 
 *                 *
 *******************/
public class MainRobotConfiguration implements IRobotConfiguration{
	private static MainRobotConfiguration config = null;
	
	private final SpeedController frontLeft;
	private final SpeedController rearLeft;
	private final SpeedController frontRight;
	private final SpeedController rearRight;
	private final SpeedController liftMotorUp;
	private final SpeedController liftMotorBtm;
	private final AHRS gyro;
	private final DigitalInput cubeDetect;
	private final Encoder encoderBtm;
	private final Encoder encoderUp;

	
	private MainRobotConfiguration() {
		//TODO get actual pins!
		frontLeft    = new Talon(0);
		frontRight   = new Talon(1);
		rearLeft     = new Talon(2);
		rearRight    = new Talon(3);
		liftMotorUp  = new Talon(4);
		liftMotorBtm = new Talon(4);
		gyro         = new AHRS(Port.kMXP); //TODO: Find port#
		cubeDetect   = new DigitalInput(-1);//TODO: Find port#
		encoderUp    = new Encoder(-1,-1);  //TODO: Find port#
		encoderBtm   = new Encoder(-1,-1);  //TODO: Find port#
		
		
	}
	/**
	 * 
	 * @return singleton of config that contains all robot objects
	 */
	public static IRobotConfiguration getConfiguration() {
		return config != null ? config : (config = new MainRobotConfiguration());
	}
	
	@Override
	public SpeedController getFrontLeftMotor() {
		return frontLeft;
	}

	@Override
	public SpeedController getRearLeftMotor() {
		return rearLeft;
	}

	@Override
	public SpeedController getFrontRightMotor() {
		return frontRight;
	}

	@Override
	public SpeedController getRearRightMotor() {
		return rearRight;
	}

	@Override
	public AHRS getGyroscope() {
		return gyro;
	}
	
	@Override
	public DigitalInput getCubeDetector() {
		return cubeDetect;
	}

	@Override
	public Encoder getEncoderBtm() {
		return encoderBtm;
	}

	@Override
	public Encoder getEncoderUp() {
		return encoderUp;
	}

	@Override
	public SpeedController getLiftMotorBtm() {
		return liftMotorBtm;
	}

	@Override
	public SpeedController getLiftMotorUp() {
		return liftMotorUp;
	}
	
}
