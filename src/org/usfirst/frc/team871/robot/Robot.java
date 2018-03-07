
package org.usfirst.frc.team871.robot;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import org.usfirst.frc.team871.subsystems.DriveTrain;
import org.usfirst.frc.team871.subsystems.Grabber;
import org.usfirst.frc.team871.subsystems.PixelStripMode;
import org.usfirst.frc.team871.subsystems.Sound;
import org.usfirst.frc.team871.subsystems.Teensy;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift;
import org.usfirst.frc.team871.subsystems.navigation.Coordinate;
import org.usfirst.frc.team871.subsystems.navigation.IWaypointProvider;
import org.usfirst.frc.team871.subsystems.navigation.Navigation;
import org.usfirst.frc.team871.subsystems.navigation.WaypointProviderFactory;
import org.usfirst.frc.team871.util.config.IControlScheme;
import org.usfirst.frc.team871.util.config.IRobotConfiguration;
import org.usfirst.frc.team871.util.config.MainRobotConfiguration;
import org.usfirst.frc.team871.util.config.SuperSaitekControlScheme;
import org.usfirst.frc.team871.util.config.ThrustmasterControlScheme;
import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;
import org.usfirst.frc.team871.util.joystick.POVDirections;
import org.usfirst.frc.team871.util.sensor.ILimitSwitch;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
		
	private DriveTrain drive;
	private IRobotConfiguration config;
	private Grabber grabber;
	private SuperLift lift;
	private IControlScheme controls;
	private AHRS navX;
	private Navigation nav;
	private Teensy teensyWeensy;
	
	private NetworkTable dashboardTable;
	
	private long startTime = 0;

	private Queue<IWaypointProvider> navQueue = new ArrayDeque<>();
	
	@Override
	public void robotInit() {
		controls = SuperSaitekControlScheme.DEFAULT;
		config   = MainRobotConfiguration.DEFAULT;
        navX     = config.getGyroscope();
        NetworkTable nt = NetworkTableInstance.getDefault().getTable("default");
		drive    = new DriveTrain(config.getRearRightMotor(), config.getRearLeftMotor(), config.getFrontRightMotor(), config.getFrontLeftMotor(), config.getGyroscope(), nt);
		grabber  = new Grabber(config.getGrabPiston(), config.getEjectPiston(), config.getCubeDetector());

		List<ILimitSwitch> upperUpperLimits = Collections.singletonList(config.getupperUpperLimit());
		List<ILimitSwitch> upperLowerLimits = Collections.singletonList(config.getupperLowerLimit());
		List<ILimitSwitch> lowerUpperLimits = Collections.singletonList(config.getlowerUpperLimit());
		List<ILimitSwitch> lowerLowerLimits = Collections.singletonList(config.getlowerLowerLimit());
		
		CompositeLimitedSpeedController limitedSpeedControllerUp = new CompositeLimitedSpeedController(config.getLiftMotorUp(), 
				upperUpperLimits, upperLowerLimits);
		CompositeLimitedSpeedController limitedSpeedControllerDown = new CompositeLimitedSpeedController(config.getLiftMotorBtm(), 
				lowerUpperLimits, lowerLowerLimits);
		
		lift = new SuperLift(limitedSpeedControllerUp, config.getEncoderUp(), limitedSpeedControllerDown, config.getEncoderBtm(), nt);

//		Coordinate origin = new Coordinate(0, 0);
		Coordinate startL = new Coordinate(33/2.0, 64);
		Coordinate startM = new Coordinate(33/2.0, 160);
		
		Coordinate startR = new Coordinate(33/2.0, 260);
		
		// Waypoints
		WaypointProviderFactory.DEFAULT.init(grabber, lift, config);
		
		
		nav = new Navigation(drive, drive, WaypointProviderFactory.DEFAULT.getProvider("MStartLSwitch"), startM);
//		navQueue.add(WaypointProviderFactory.DEFAULT.getProvider("RStart"));
//		navQueue.add(WaypointProviderFactory.DEFAULT.getProvider("LScaleRSwitch"));
		
//		CameraServer.getInstance().startAutomaticCapture(0);
//		CameraServer.getInstance().startAutomaticCapture(1);
		
		teensyWeensy = new Teensy();
		teensyWeensy.setVolume(.9);
		teensyWeensy.playSound(Sound.LEEROY_JENKINS);
		
		NetworkTableInstance defaultInstance = NetworkTableInstance.getDefault();
		defaultInstance.setNetworkIdentity("Robot");
		defaultInstance.startClientTeam(871);
		
		dashboardTable = defaultInstance.getTable("Dashboard");
	}

	@Override
	public void robotPeriodic() {
		super.robotPeriodic();
		
		updateDashboard();

		if(controls.test()) {
			teensyWeensy.setVolume(.9);
			teensyWeensy.playSound(Sound.LEEROY_JENKINS);
		}
		
		teensyWeensy.update();
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
		if(nav.isDone() && !navQueue.isEmpty()) {
			nav.setWaypointProvider(navQueue.poll());
		}
	}

	@Override
	public void teleopInit() {
		drive.disableHeadingHold();
		config.getTootToot().set(Value.kReverse);
		super.teleopInit();
	}

	@Override
	public void teleopPeriodic() {
//		System.out.println(controls.getToggleOrientationButton());
		if(controls.getToggleOrientationButton()) {
			drive.driveRobotOriented(-controls.getYAxis(), controls.getXAxis(), controls.getRotationAxis());
		} else {
			drive.driveFieldOriented(-controls.getYAxis(), controls.getXAxis(), controls.getRotationAxis());
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
		
		lift.setTrim(controls.getUpperLiftTrim(), controls.getLowerLiftTrim());
		
//		if(controls.getPOV() ==  POVDirections.UP || controls.getPOV() ==  POVDirections.UP_RIGHT || controls.getPOV() ==  POVDirections.UP_LEFT) {
//			lift.setTop();
//		}
//		
//		if(controls.getPOV() ==  POVDirections.DOWN || controls.getPOV() ==  POVDirections.DOWN_RIGHT || controls.getPOV() ==  POVDirections.DOWN_LEFT) {
//			lift.setBottom();
//		}
		
		if(controls.getKickButton()) {
			grabber.ejectCube();
		}
		
		
		config.getTootToot().set(controls.toottoot() ? Value.kForward : Value.kReverse);
	}

	@Override
	public void testPeriodic() {
		
	}
	
	/**
	 * Update the network table containing the dashboard variables.
	 * 
	 * @author The Jack
	 */
	//TODO:  Add method call to update position and gyro data from DriveTrain
	public void updateDashboard() {
		lift.updateData();
		drive.updateData();
	}
}
