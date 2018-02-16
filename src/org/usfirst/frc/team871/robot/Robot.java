
package org.usfirst.frc.team871.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.usfirst.frc.team871.subsystems.DriveTrain;
import org.usfirst.frc.team871.subsystems.Grabber;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift;
import org.usfirst.frc.team871.subsystems.navigation.Coordinate;
import org.usfirst.frc.team871.subsystems.navigation.Navigation;
import org.usfirst.frc.team871.subsystems.navigation.WaypointProviderFactory;
import org.usfirst.frc.team871.util.config.IControlScheme;
import org.usfirst.frc.team871.util.config.IRobotConfiguration;
import org.usfirst.frc.team871.util.config.MainRobotConfiguration;
import org.usfirst.frc.team871.util.config.ThrustmasterControlScheme;
import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;
import org.usfirst.frc.team871.util.joystick.POVDirections;
import org.usfirst.frc.team871.util.sensor.ILimitSwitch;

import java.util.Collections;
import java.util.List;

public class Robot extends IterativeRobot {
		
	private DriveTrain drive;
	private IRobotConfiguration config;
	private Grabber grabber;
	private SuperLift lift;
	private IControlScheme controls;
	private AHRS navX;
	private Navigation nav;
	
	private long startTime = 0;

	@Override
	public void robotInit() {
		controls = ThrustmasterControlScheme.DEFAULT;
		config   = MainRobotConfiguration.DEFAULT;
        navX     = config.getGyroscope();
		drive    = new DriveTrain(config.getRearRightMotor(), config.getRearLeftMotor(), config.getFrontRightMotor(), config.getFrontLeftMotor(), config.getGyroscope());
		grabber  = new Grabber(config.getGrabPiston(), config.getEjectPiston(), config.getCubeDetector());

		List<ILimitSwitch> upperUpperLimits = Collections.singletonList(config.getupperUpperLimit());
		List<ILimitSwitch> upperLowerLimits = Collections.singletonList(config.getupperLowerLimit());
		List<ILimitSwitch> lowerUpperLimits = Collections.singletonList(config.getlowerUpperLimit());
		List<ILimitSwitch> lowerLowerLimits = Collections.singletonList(config.getlowerLowerLimit());
		
		CompositeLimitedSpeedController limitedSpeedControllerUp = new CompositeLimitedSpeedController(config.getLiftMotorUp(), 
				upperUpperLimits, upperLowerLimits);
		CompositeLimitedSpeedController limitedSpeedControllerDown = new CompositeLimitedSpeedController(config.getLiftMotorBtm(), 
				lowerUpperLimits, lowerLowerLimits);
		
		lift = new SuperLift(limitedSpeedControllerUp, config.getEncoderUp(), limitedSpeedControllerDown, config.getEncoderBtm());

		// Waypoints
		WaypointProviderFactory.DEFAULT.init(grabber, lift, config);
		nav = new Navigation(drive, drive, WaypointProviderFactory.DEFAULT.getProvider("WoodshopDrop"), new Coordinate(0,0));
	}

	@Override
	public void autonomousInit() {
		grabber.setGrab(true);
		config.getTootToot().set(Value.kReverse);
		drive.resetSensor();
		drive.resetGyro();
		drive.setHeadingHold(0);
		startTime = System.currentTimeMillis();
		System.out.println("Robot driving started:\t"+ startTime + "\n");
	}

	@Override
	public void autonomousPeriodic() {
		nav.navigate();
	}

	@Override
	public void teleopInit() {
		drive.disableHeadingHold();
		config.getTootToot().set(Value.kReverse);
		super.teleopInit();
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

		if(controls.getPOV() ==  POVDirections.UP || controls.getPOV() ==  POVDirections.UP_RIGHT || controls.getPOV() ==  POVDirections.UP_LEFT) {
			lift.setTop();
		}
		
		if(controls.getPOV() ==  POVDirections.DOWN || controls.getPOV() ==  POVDirections.DOWN_RIGHT || controls.getPOV() ==  POVDirections.DOWN_LEFT) {
			lift.setBottom();
		}
		
		config.getTootToot().set(controls.toottoot() ? Value.kForward : Value.kReverse);
	}

	@Override
	public void testPeriodic() {
		
	}
}