
package org.usfirst.frc.team871.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team871.subsystems.DriveTrain;	
import org.usfirst.frc.team871.subsystems.Grabber;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift;
import org.usfirst.frc.team871.util.config.IControlScheme;
import org.usfirst.frc.team871.util.config.IRobotConfiguration;
import org.usfirst.frc.team871.util.config.InitialControlScheme;
import org.usfirst.frc.team871.util.config.MainRobotConfiguration;
import org.usfirst.frc.team871.util.config.ThrustmasterControlScheme;
import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;
import org.usfirst.frc.team871.util.joystick.POVDirections;
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
		controls = ThrustmasterControlScheme.DEFAULT;
		config = MainRobotConfiguration.DEFAULT;
		drive = new DriveTrain(config.getRearRightMotor(), config.getRearLeftMotor(), config.getFrontRightMotor(), config.getFrontLeftMotor(), config.getGyroscope());
		grabber = new Grabber(config.getGrabPiston(), config.getEjectPiston(), config.getCubeDetector());
		
		List<ILimitSwitch> upperUpperLimits = Arrays.asList(config.getupperUpperLimit());
		List<ILimitSwitch> upperLowerLimits = Arrays.asList(config.getupperLowerLimit());
		List<ILimitSwitch> lowerUpperLimits = Arrays.asList(config.getlowerUpperLimit());
		List<ILimitSwitch> lowerLowerLimits = Arrays.asList(config.getlowerLowerLimit());
		
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
			drive.driveRobotOriented(controls.getYAxis(), -controls.getXAxis(), controls.getRotationAxis());
		} else {
			drive.driveFieldOriented(controls.getYAxis(), -controls.getXAxis(), controls.getRotationAxis());
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
		
		if(controls.getManualLiftModeButton()) {
			lift.moveLift(controls.getLiftAxis());
		}else {
			if(controls.getDecreaseSetpointButton()) {
				lift.decreaseSetpoint();
			}
			
			if(controls.getIncreaseSetpointButton()) {
				lift.increaseSetpoint();
			}
		}
		
//		if(controls.getLiftAxis() != 0) {
//		lift.moveLift(controls.getLiftAxis());
//		}
		
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
}
