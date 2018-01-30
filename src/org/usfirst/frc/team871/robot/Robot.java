
//TODO: MAKE VARS AN INTERFACE THAT CAN SWICTH BETWEEN EACH ROBOT INTERFACES

package org.usfirst.frc.team871.robot;

import java.util.ArrayList;
import java.util.Arrays;

import org.usfirst.frc.team871.subsystems.DriveTrain;
import org.usfirst.frc.team871.subsystems.Grabber;
import org.usfirst.frc.team871.subsystems.SuperLift;
import org.usfirst.frc.team871.util.config.IRobotConfiguration;
import org.usfirst.frc.team871.util.config.MainRobotConfiguration;
import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;
import org.usfirst.frc.team871.util.joystick.ButtonTypes;
import org.usfirst.frc.team871.util.joystick.EnhancedXBoxController;
import org.usfirst.frc.team871.util.joystick.XBoxAxes;
import org.usfirst.frc.team871.util.joystick.XBoxButtons;
import org.usfirst.frc.team871.util.sensor.EncoderLimitSwitch;
import org.usfirst.frc.team871.util.sensor.ILimitSwitch;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
		
	private DriveTrain drive;
	private IRobotConfiguration config;
	private EnhancedXBoxController xbox;
	private EnhancedXBoxController xbox2;
	private Grabber grabber;
	private SuperLift lift;
	
	@Override
	public void robotInit() {
		xbox = new EnhancedXBoxController(0);
		xbox2 = new EnhancedXBoxController(1);
		config = MainRobotConfiguration.DEFAULT;
		drive = new DriveTrain(config.getRearRightMotor(), config.getRearLeftMotor(), config.getFrontRightMotor(), config.getFrontLeftMotor(), config.getGyroscope());
		xbox.setButtonMode(XBoxButtons.START, ButtonTypes.TOGGLE);
		xbox.setButtonMode(XBoxButtons.BACK, ButtonTypes.RISING);
		xbox2.setButtonMode(XBoxButtons.A, ButtonTypes.RISING);
		xbox2.setButtonMode(XBoxButtons.B, ButtonTypes.RISING);
		xbox2.setButtonMode(XBoxButtons.LBUMPER, ButtonTypes.RISING);
		xbox2.setButtonMode(XBoxButtons.RBUMPER, ButtonTypes.RISING);
		
		
		
		grabber = new Grabber(config.getGrabPiston(), config.getEjectPiston(), config.getCubeDetector());
		
		
		ArrayList<ILimitSwitch> upperUpperLimits = new ArrayList<ILimitSwitch>(Arrays.asList(config.getupperUpperLimit(), new EncoderLimitSwitch(config.getEncoderUp(), -1, true)));
		ArrayList<ILimitSwitch> upperLowerLimits = new ArrayList<ILimitSwitch>(Arrays.asList(config.getupperLowerLimit(), new EncoderLimitSwitch(config.getEncoderUp(), -1, false)));
		ArrayList<ILimitSwitch> lowerUpperLimits = new ArrayList<ILimitSwitch>(Arrays.asList(config.getlowerUpperLimit(), new EncoderLimitSwitch(config.getEncoderBtm(), -1, true)));
		ArrayList<ILimitSwitch> lowerLowerLimits = new ArrayList<ILimitSwitch>(Arrays.asList(config.getlowerLowerLimit(), new EncoderLimitSwitch(config.getEncoderBtm(), -1, false)));
		
		
		CompositeLimitedSpeedController limitedSpeedControllerUp = new CompositeLimitedSpeedController(config.getLiftMotorUp(), 
				upperUpperLimits, upperLowerLimits);
		CompositeLimitedSpeedController limitedSpeedControllerDown = new CompositeLimitedSpeedController(config.getLiftMotorBtm(), 
				lowerUpperLimits, lowerLowerLimits);
		
		lift = new SuperLift(limitedSpeedControllerUp, config.getEncoderUp(), limitedSpeedControllerDown, config.getEncoderBtm());
		
		
	}

	@Override
	public void autonomousInit() {
		
	}

	@Override
	public void autonomousPeriodic() {

	}

	@Override
	public void teleopPeriodic() {
		if(xbox.getValue(XBoxButtons.START)) {
			drive.driveRobotOriented(xbox.getValue(XBoxAxes.LEFTX), xbox.getValue(XBoxAxes.LEFTY), xbox.getValue(XBoxAxes.RIGHTX));
		} else {
			drive.driveFieldOriented(xbox.getValue(XBoxAxes.LEFTX), xbox.getValue(XBoxAxes.LEFTY), xbox.getValue(XBoxAxes.RIGHTX));
		}
		if(xbox.getValue(XBoxButtons.BACK)) {
			drive.resetGyro();
		}
		if(xbox2.getValue(XBoxButtons.A)) {
			grabber.toggleGrabber();
		}
		if(xbox2.getValue(XBoxButtons.B)) {
			grabber.ejectCube();
		}
		
		if(xbox2.getValue(XBoxButtons.LBUMPER)) {
			lift.decreaseSetpoint();
		}
		if(xbox2.getValue(XBoxButtons.RBUMPER)) {
			lift.increaseSetpoint();
		}
		
		if(xbox2.getValue(XBoxAxes.TRIGGER) != 0) {
			lift.moveLift(xbox2.getValue(XBoxAxes.TRIGGER));
		}
		
		if(xbox2.getPOV() < 46 && xbox2.getPOV() > 314) {
			lift.setTop();
		}
		
		if(xbox2.getPOV() < 226 && xbox2.getPOV() > 134) {
			lift.setBottom();
		}
		
	}

	@Override
	public void testPeriodic() {
		
	}
}
