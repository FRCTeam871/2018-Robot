package org.usfirst.frc.team871.util.config;

import org.usfirst.frc.team871.util.sensor.DigitalLimitSwitch;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.SpeedController;

/*******************
 * Contains all var*ables of robor 
 *                 *
 *******************/
public enum MainRobotConfiguration implements IRobotConfiguration {
	DEFAULT;
	
	private final SpeedController frontLeft;
	private final SpeedController rearLeft;
	private final SpeedController frontRight;
	private final SpeedController rearRight;
	private final SpeedController liftMotorUp;
	private final SpeedController liftMotorBtm;
	private final AHRS gyro;
	private final DigitalInput cubeDetect;
	private final Encoder encoderBtm; // 256 ticks/rot
	private final Encoder encoderUp; // 256 ticks/rot
	private final DoubleSolenoid grabPiston;
	private final DoubleSolenoid ejectPiston;
	private final DigitalLimitSwitch upperUpperLimit;
	private final DigitalLimitSwitch upperLowerLimit;
	private final DigitalLimitSwitch lowerUpperLimit;
	private final DigitalLimitSwitch lowerLowerLimit;
	
	private MainRobotConfiguration() {
		//TODO get actual pins!

		frontLeft    = new WPI_VictorSPX(12); //TODO: Find port#
		frontRight   = new WPI_VictorSPX(10); //TODO: Find port#
		rearLeft     = new WPI_VictorSPX(13); //TODO: Find port#
		rearRight    = new WPI_VictorSPX(11); //TODO: Find port#
		liftMotorUp  = new WPI_TalonSRX(1); //TODO: Find port#
		liftMotorBtm = new WPI_TalonSRX(0); //TODO: Find port#
		gyro         = new AHRS(Port.kMXP); //TODO: Find port#
		cubeDetect   = new DigitalInput(20);//TODO: Find port#
		
		encoderUp    = new Encoder(4,5);  //TODO: Find port#
		// diam = 1.6
		// circum = diam * PI
		// ticksPerPulse = 256
		// distPerPulse = circum / ticksPerPulse
		encoderUp.setDistancePerPulse(0.019634954084936);
		
		encoderBtm   = new Encoder(6,7);  //TODO: Find port#
		// diam = 1.6
		// circum = diam * PI
		// ticksPerPulse = 256
		// distPerPulse = circum / ticksPerPulse
		encoderBtm.setDistancePerPulse(0.019634954084936);
		
		grabPiston   = new DoubleSolenoid(0, 1); //TODO: Find port#
		ejectPiston  = new DoubleSolenoid(2, 3); //TODO: Find port#
		upperUpperLimit = new DigitalLimitSwitch(new DigitalInput(2)); //TODO: Find port#
		upperLowerLimit = new DigitalLimitSwitch(new DigitalInput(3)); //TODO: Find port#
		lowerUpperLimit = new DigitalLimitSwitch(new DigitalInput(0)); //TODO: Find port#
		lowerLowerLimit = new DigitalLimitSwitch(new DigitalInput(1)); //TODO: Find port#
		
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
	@Override
	public DoubleSolenoid getGrabPiston() {
		// TODO Auto-generated method stub
		return grabPiston;
	}
	@Override
	public DoubleSolenoid getEjectPiston() {
		// TODO Auto-generated method stub
		return ejectPiston;
	}

	@Override
	public DigitalLimitSwitch getupperUpperLimit() {
		// TODO Auto-generated method stub
		return upperUpperLimit;
	}

	@Override
	public DigitalLimitSwitch getupperLowerLimit() {
		// TODO Auto-generated method stub
		return upperLowerLimit;
	}

	@Override
	public DigitalLimitSwitch getlowerUpperLimit() {
		// TODO Auto-generated method stub
		return lowerUpperLimit;
	}

	@Override
	public DigitalLimitSwitch getlowerLowerLimit() {
		// TODO Auto-generated method stub
		return lowerLowerLimit;
	}
	
}
