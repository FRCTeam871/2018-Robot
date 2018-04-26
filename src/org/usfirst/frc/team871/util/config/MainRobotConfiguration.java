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
	private final DoubleSolenoid tootToot;
	private final DigitalLimitSwitch upperUpperLimit;
	private final DigitalLimitSwitch upperLowerLimit;
	private final DigitalLimitSwitch lowerUpperLimit;
	private final DigitalLimitSwitch lowerLowerLimit;
	
	MainRobotConfiguration() {
		//TODO get actual pins!

		frontLeft    = new WPI_VictorSPX(12);
		frontRight   = new WPI_VictorSPX(10);
		rearLeft     = new WPI_VictorSPX(13);
		rearRight    = new WPI_VictorSPX(11);
		liftMotorUp  = new WPI_TalonSRX(1);
		liftMotorBtm = new WPI_TalonSRX(0);
		liftMotorBtm.setInverted(true);
		gyro         = new AHRS(Port.kMXP);
		cubeDetect   = new DigitalInput(20); //TODO: Find port#

		//41 26.5

		encoderUp    = new Encoder(6,7);  //TODO: Find port#
		// diam = 1.6
		// circum = diam * PI
		// ticksPerPulse = 256
		// distPerPulse = circum / ticksPerPulse
//		encoderUp.setDistancePerPulse(0.019634954084936);
//		encoderUp.setDistancePerPulse(1);
		encoderUp.setDistancePerPulse(41 / 1682.75);

		//1682.75 41in

		encoderBtm   = new Encoder(4,5);  //TODO: Find port#
		// diam = 1.6
		// circum = diam * PI
		// ticksPerPulse = 256
		// distPerPulse = circum / ticksPerPulse
//		encoderBtm.setDistancePerPulse(0.019634954084936);
//		encoderBtm.setDistancePerPulse(1);
		encoderBtm.setDistancePerPulse(41 / 1865.75);
		encoderBtm.setReverseDirection(true);
		//1865.75 41

		grabPiston   = new DoubleSolenoid(0, 1);
		ejectPiston  = new DoubleSolenoid(2, 3);
		tootToot  = new DoubleSolenoid(4, 5);
		upperUpperLimit = new DigitalLimitSwitch(new DigitalInput(2), true); //TODO: Find port#
		upperLowerLimit = new DigitalLimitSwitch(new DigitalInput(3), true); //TODO: Find port#
		lowerUpperLimit = new DigitalLimitSwitch(new DigitalInput(0), true); //TODO: Find port#
		lowerLowerLimit = new DigitalLimitSwitch(new DigitalInput(1), true); //TODO: Find port#
		
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
		return grabPiston;
	}

	@Override
	public DoubleSolenoid getEjectPiston() {
		return ejectPiston;
	}

	@Override
	public DoubleSolenoid getTootToot() {
		return tootToot;
	}

	@Override
	public DigitalLimitSwitch getupperUpperLimit() {
		return upperUpperLimit;
	}

	@Override
	public DigitalLimitSwitch getupperLowerLimit() {
		return upperLowerLimit;
	}

	@Override
	public DigitalLimitSwitch getlowerUpperLimit() {
		return lowerUpperLimit;
	}

	@Override
	public DigitalLimitSwitch getlowerLowerLimit() {
		return lowerLowerLimit;
	}

	@Override
	public boolean getResetUpperEncoderSwitch() {
		return upperUpperLimit.isAtLimit();
	}
	
	@Override
	public boolean getResetLowerEncoderSwitch() {
		return lowerUpperLimit.isAtLimit();
	}
	
}
