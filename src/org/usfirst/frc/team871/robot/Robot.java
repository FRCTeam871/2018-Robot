
package org.usfirst.frc.team871.robot;

import java.util.ArrayList;
import java.util.Arrays;

import org.usfirst.frc.team871.subsystems.DriveTrain;
import org.usfirst.frc.team871.subsystems.Grabber;
import org.usfirst.frc.team871.subsystems.SuperLift;
import org.usfirst.frc.team871.util.config.IControlScheme;
import org.usfirst.frc.team871.util.config.IRobotConfiguration;
import org.usfirst.frc.team871.util.config.InitialControlScheme;
import org.usfirst.frc.team871.util.config.MainRobotConfiguration;
import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;
import org.usfirst.frc.team871.util.joystick.ButtonTypes;
import org.usfirst.frc.team871.util.joystick.EnhancedXBoxController;
import org.usfirst.frc.team871.util.joystick.POVDirections;
import org.usfirst.frc.team871.util.joystick.XBoxAxes;
import org.usfirst.frc.team871.util.joystick.XBoxButtons;
import org.usfirst.frc.team871.util.sensor.EncoderLimitSwitch;
import org.usfirst.frc.team871.util.sensor.ILimitSwitch;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
		
	private DriveTrain drive;
	private IRobotConfiguration config;
	private Grabber grabber;
	private SuperLift lift;
	private IControlScheme controls;
	
	@Override
	public void robotInit() {
		
		controls = InitialControlScheme.DEFAULT;
		config = MainRobotConfiguration.DEFAULT;
		drive = new DriveTrain(config.getRearRightMotor(), config.getRearLeftMotor(), config.getFrontRightMotor(), config.getFrontLeftMotor(), config.getGyroscope());
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
		if(controls.getToggleOrientationButton()) {
			drive.driveRobotOriented(controls.getXAxis(), controls.getYAxis(), controls.getRotationAxis());
		} else {
			drive.driveFieldOriented(controls.getXAxis(), controls.getYAxis(), controls.getRotationAxis());
		}
		if(controls.getResetGyroButton()) {
			drive.resetGyro();
		}
		if(controls.getToggleGrabberButton()) {
			grabber.toggleGrabber();
		}
		if(controls.getCubeEjectButton()) {
			grabber.ejectCube();
		}
		
		if(controls.getDecreaseSetpointButton()) {
			lift.decreaseSetpoint();
		}
		if(controls.getIncreaseSetpointButton()) {
			lift.increaseSetpoint();
		}
		
		if(controls.getLiftAxis() != 0) {
			lift.moveLift(controls.getLiftAxis());
		}
		
		if(controls.getPOV() ==  POVDirections.UP || controls.getPOV() ==  POVDirections.UP_RIGHT || controls.getPOV() ==  POVDirections.UP_LEFT) {
			lift.setTop();
		}
		
		if(controls.getPOV() ==  POVDirections.DOWN || controls.getPOV() ==  POVDirections.DOWN_RIGHT || controls.getPOV() ==  POVDirections.DOWN_LEFT) {
			lift.setBottom();
		}
		
	}

	@Override
	public void testPeriodic() {
		
	}
	//CONTRIBUTED BY 
}