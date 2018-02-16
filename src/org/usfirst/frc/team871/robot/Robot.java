
package org.usfirst.frc.team871.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.usfirst.frc.team871.subsystems.DriveTrain;
import org.usfirst.frc.team871.subsystems.Grabber;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift.SetpointHeights;
import org.usfirst.frc.team871.subsystems.navigation.Coordinate;
import org.usfirst.frc.team871.subsystems.navigation.Navigation;
import org.usfirst.frc.team871.subsystems.navigation.Waypoint;
import org.usfirst.frc.team871.subsystems.navigation.WaypointProvider;
import org.usfirst.frc.team871.subsystems.navigation.actions.LiftSetpointAction;
import org.usfirst.frc.team871.subsystems.navigation.actions.SetGrabberAction;
import org.usfirst.frc.team871.subsystems.navigation.actions.TootTootAction;
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
//		WaypointProvider squareProvider = new WaypointProvider(new Waypoint(1, 0, 0, .3),
//				new Waypoint(size,size,0,.3),
//				new Waypoint(0, size, 0, .3),
//				new Waypoint(0,0,0,.3, new TootTootAction(config.getTootToot())));
//		WaypointProvider prov = new WaypointProvider(new Waypoint(0, 0, 0, 0.3), new Waypoint(12 * 3, 0, 0, 0.3), new Waypoint(12 * 3, -12 * 5, 0, 0.3), new Waypoint(12 * 6, -12 * 5, 0, 0.3), new Waypoint(12 * 6, -12 * 11, 0, 0.3), new Waypoint(12 * 3, -12 * 11, 0, 0.3), new Waypoint(12 * 3, -12 * 16, 0, 0.3), new Waypoint(12 * 0, -12 * 16, 0, 0.3));
//		WaypointProvider prov = new WaypointProvider(new Waypoint(0, 0, 0, 0.3), new Waypoint(12 * 6, 0, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH)), new Waypoint(0, 0, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.SCALE_MID)), new Waypoint(0, 0, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND)));
		
		WaypointProvider prov = new WaypointProvider(new Waypoint(0, 0, 0, 0.3, new SetGrabberAction(grabber, true)),
				new Waypoint(12 * 19, 0, 0, 0.6),
				new Waypoint(12 * 19, (12 * 10) - 6, 0, 0.4, new LiftSetpointAction(lift, SetpointHeights.SCALE_MID)),
				new Waypoint(12 * 19, 11 * 12, 0, 0.3, new SetGrabberAction(grabber, false)),
				new Waypoint(12 * 19, (12 * 10) - 6, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND)),
				new Waypoint(12 * 19, 0, 0, 0.4),
				new Waypoint(-12, 0, 0, 0.6),
				new Waypoint(0, 0, 0, 0.3, new TootTootAction(config.getTootToot())));

		nav = new Navigation(drive, drive, prov, new Coordinate(0,0));
		
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

	public DriveTrain getDrive() {
		return this.drive;
	}
}