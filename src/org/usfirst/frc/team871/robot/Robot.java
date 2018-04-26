
package org.usfirst.frc.team871.robot;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.usfirst.frc.team871.subsystems.DriveTrain;
import org.usfirst.frc.team871.subsystems.Grabber;
import org.usfirst.frc.team871.subsystems.PixelStripMode;
import org.usfirst.frc.team871.subsystems.Sound;
import org.usfirst.frc.team871.subsystems.Teensy;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift;
import org.usfirst.frc.team871.subsystems.navigation.Coordinate;
import org.usfirst.frc.team871.subsystems.navigation.IWaypointProvider;
import org.usfirst.frc.team871.subsystems.navigation.ManualFieldSetup;
import org.usfirst.frc.team871.subsystems.navigation.Navigation;
import org.usfirst.frc.team871.subsystems.navigation.RealFieldSetup;
import org.usfirst.frc.team871.subsystems.navigation.WaypointProvider;
import org.usfirst.frc.team871.subsystems.navigation.WaypointProviderFactory;
import org.usfirst.frc.team871.subsystems.navigation.WaypointSelector;
import org.usfirst.frc.team871.util.config.IControlScheme;
import org.usfirst.frc.team871.util.config.IRobotConfiguration;
import org.usfirst.frc.team871.util.config.MainRobotConfiguration;
import org.usfirst.frc.team871.util.config.SecondRobotConfiguration;
import org.usfirst.frc.team871.util.config.SuperSaitekControlScheme;
import org.usfirst.frc.team871.util.config.ThrustmasterControlScheme;
import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;
import org.usfirst.frc.team871.util.control.LimitedSpeedController;
import org.usfirst.frc.team871.util.joystick.EnhancedXBoxController;
import org.usfirst.frc.team871.util.joystick.POVDirections;
import org.usfirst.frc.team871.util.joystick.SaitekButtons;
import org.usfirst.frc.team871.util.joystick.SaitekX52;
import org.usfirst.frc.team871.util.joystick.XBoxButtons;
import org.usfirst.frc.team871.util.sensor.ILimitSwitch;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
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
	private WaypointSelector pathFinder;
	
	private NetworkTable dashboardTable;
	
	private long startTime = 0;

	private Queue<IWaypointProvider> navQueue = new ArrayDeque<>();
	
	private boolean waypointSelectorTestMode = false;
	public CompositeLimitedSpeedController lsc1;
	
	long lastLEDWrite = 0;
	
	POVDirections lastPOV = null;
	
	@Override
	public void robotInit() {
		controls = ThrustmasterControlScheme.DEFAULT;
		NetworkTableInstance defaultInstance = NetworkTableInstance.getDefault();
		defaultInstance.setNetworkIdentity("Robot");
		defaultInstance.startClientTeam(871);
		
		dashboardTable = defaultInstance.getTable("Dashboard");

		config   = MainRobotConfiguration.DEFAULT;
        navX     = config.getGyroscope();
		drive    = new DriveTrain(config.getRearRightMotor(), config.getRearLeftMotor(), config.getFrontRightMotor(), config.getFrontLeftMotor(), config.getGyroscope(), dashboardTable);
		grabber  = new Grabber(config.getGrabPiston(), config.getEjectPiston(), config.getCubeDetector());
		
		List<ILimitSwitch> upperUpperLimits = Collections.singletonList(config.getupperUpperLimit());
		List<ILimitSwitch> upperLowerLimits = Collections.singletonList(config.getupperLowerLimit());
		List<ILimitSwitch> lowerUpperLimits = Collections.singletonList(config.getlowerUpperLimit());
		List<ILimitSwitch> lowerLowerLimits = Collections.singletonList(config.getlowerLowerLimit());
		
		CompositeLimitedSpeedController limitedSpeedControllerUp = new CompositeLimitedSpeedController(config.getLiftMotorUp(), 
				upperUpperLimits, upperLowerLimits);
		CompositeLimitedSpeedController limitedSpeedControllerDown = new CompositeLimitedSpeedController(config.getLiftMotorBtm(), 
				lowerUpperLimits, lowerLowerLimits);
		
		lsc1 = limitedSpeedControllerDown;
		
		lift = new SuperLift(limitedSpeedControllerUp, config.getEncoderUp(), limitedSpeedControllerDown, config.getEncoderBtm(), dashboardTable, config);

//		Coordinate origin = new Coordinate(0, 0);
		Coordinate startM = new Coordinate(33/2.0, 160);
		
		Coordinate startR = new Coordinate(33/2.0, 260);
		
		// Waypoints
		WaypointProviderFactory.DEFAULT.init(grabber, lift, config);
		
		pathFinder = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), new RealFieldSetup(dashboardTable)); 
		
//		navQueue.add(WaypointProviderFactory.DEFAULT.getProvider("RStart"));
//		navQueue.add(WaypointProviderFactory.DEFAULT.getProvider("LScaleRSwitch"));
		
		
		//CameraServer.getInstance().startAutomaticCapture(0); 
		//CameraServer.getInstance().startAutomaticCapture(1);
		
		teensyWeensy = new Teensy();
		teensyWeensy.setVolume(1);
		
		if(controls instanceof ThrustmasterControlScheme) {
			EnhancedXBoxController xbox = ((ThrustmasterControlScheme)controls).getXbox();
			
			if(!xbox.getValue(XBoxButtons.START)) teensyWeensy.playSound(Sound.STARTUP);
		}else {
			teensyWeensy.playSound(Sound.STARTUP);
		}
		teensyWeensy.setPixelStripMode(4, PixelStripMode.RAINBOW);
		teensyWeensy.setPixelStripMode(6, PixelStripMode.RAINBOW);
		
	}

	@Override
	public void robotPeriodic() {
		super.robotPeriodic();
		
		updateDashboard();

		if(controls.test()) {
			teensyWeensy.setVolume(.9);
			Sound s = null;
			Sound[] sounds = Sound.values();
			Random r = new Random();
			while(s == null) {
				s = sounds[r.nextInt(sounds.length)];
				if(s.getPath().startsWith("music/")) s = null;
			}
			
			teensyWeensy.playSound(s);
		}
		 
		if(controls instanceof SuperSaitekControlScheme) {
			SaitekX52 saitek = ((SuperSaitekControlScheme) controls).getSaitek();
				
			if(saitek.getDebouncedButton(SaitekButtons.SOUND_L_UP)) {
				teensyWeensy.playSound(Sound.LEEROY_JENKINS);
			}else if(saitek.getDebouncedButton(SaitekButtons.SOUND_L_DOWN)) {
				teensyWeensy.playSound(Sound.GALAGA_SPIDER);
			}else if(saitek.getDebouncedButton(SaitekButtons.SOUND_M_UP)) {
				teensyWeensy.playSound(Sound.MARIO_JINGLE);
			}else if(saitek.getDebouncedButton(SaitekButtons.SOUND_M_DOWN)) {
				teensyWeensy.playSound(Sound.TETRIS_THEME);
			}else if(saitek.getDebouncedButton(SaitekButtons.SOUND_R_UP)) {
				teensyWeensy.playSound(Sound.NEVER_GONNA_GIVE_YOU_UP);
			}else if(saitek.getDebouncedButton(SaitekButtons.SOUND_R_DOWN)) {
				teensyWeensy.playSound(Sound.TAKE_ON_ME);
			}
			
		}else if(controls instanceof ThrustmasterControlScheme) {
			EnhancedXBoxController xbox = ((ThrustmasterControlScheme)controls).getXbox();
			
			POVDirections dir = xbox.getEnhancedPOV();
			
			if(lastPOV != dir) {
				if(dir == POVDirections.UP) {
					teensyWeensy.playSound(Sound.LEEROY_JENKINS);
				}else if(dir == POVDirections.UP_RIGHT) {
					teensyWeensy.playSound(Sound.TETRIS_THEME);
				}else if(dir == POVDirections.RIGHT) {
					teensyWeensy.playSound(Sound.GALAGA_SPIDER);
				}else if(dir == POVDirections.DOWN_RIGHT) {
					teensyWeensy.playSound(Sound.MARIO_JINGLE);
				}else if(dir == POVDirections.DOWN) {
					teensyWeensy.playSound(Sound.NEVER_GONNA_GIVE_YOU_UP);
				}else if(dir == POVDirections.DOWN_LEFT) {
					teensyWeensy.playSound(Sound.IM_A_COMPUTER);
				}else if(dir == POVDirections.LEFT) {
					teensyWeensy.playSound(Sound.ITS_STILL_ROCK_AND_ROLL_TO_ME);
				}else if(dir == POVDirections.UP_LEFT) {
					teensyWeensy.playSound(Sound.TAKE_ON_ME);
				}
			}
			
			lastPOV = dir;
			
		}
		
		
		
		teensyWeensy.update();
	}

	@Override
	public void autonomousInit() {
		
		teensyWeensy.playSound(Sound.LEEROY_JENKINS);
		teensyWeensy.setPixelStripMode(4, PixelStripMode.CHASE_B_FAST);
		teensyWeensy.setPixelStripMode(6, PixelStripMode.CHASE_B_FAST);
		
		new Thread(() -> {
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			teensyWeensy.playSound(Sound.TETRIS_THEME);
		}).start();
		
		pathFinder.setup();
		pathFinder.chooseEndpoint();
		
		WaypointProvider path = pathFinder.getPath();
		if(path != null) {
			Coordinate startL = new Coordinate(33/2.0, 64);
			Coordinate startM = new Coordinate(33/2.0, 160);
			Coordinate startR = new Coordinate(33/2.0, 260);
			
			Coordinate start = startM;
			
			switch(pathFinder.getFieldSetup().getStartPosition()) {
			case 0:
				start = startL;
				break;
			case 1:
				start = startM;
				break;
			case 2:
				start = startR;
				break;
			}
			
			nav = new Navigation(drive, drive, path, start);
		}
		
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
		if(!waypointSelectorTestMode) {
			if(nav.hasPath()) {
				nav.navigate();
				if(nav.isDone() && !navQueue.isEmpty()) {
					nav.setWaypointProvider(navQueue.poll());
				}
			}
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
	public void updateDashboard() {
		lift.updateData();
		dashboardTable.getEntry("liftMode").setString(controls.getManualLiftModeButton() ? "Manual" : "Automatic");
		drive.updateData();
	}
	
	@Override
	public void disabledInit() {
		
		teensyWeensy.setPixelStripMode(4, PixelStripMode.RAINBOW);
		teensyWeensy.setPixelStripMode(6, PixelStripMode.RAINBOW);
//		teensyWeensy.setPixelStripMode(4, PixelStripMode.PULSE_R);
//		teensyWeensy.setPixelStripMode(6, PixelStripMode.PULSE_R);
		teensyWeensy.stopSound();
	}
	
	@Override
	public void disabledPeriodic() {
		

		
		long now = System.currentTimeMillis();
		
		if(now - lastLEDWrite > 500) {
			lastLEDWrite = now;
			
//			teensyWeensy.setPixelStripMode(4, PixelStripMode.RAINBOW);
//			teensyWeensy.setPixelStripMode(6, PixelStripMode.RAINBOW);
			

			teensyWeensy.setPixelStripMode(4, PixelStripMode.RAINBOW);
			teensyWeensy.setPixelStripMode(6, PixelStripMode.RAINBOW);
			
//			if(DriverStation.getInstance().getAlliance() == Alliance.Blue) {
//				teensyWeensy.setPixelStripMode(4, PixelStripMode.PULSE_CHASE_B_FAST);
//				teensyWeensy.setPixelStripMode(6, PixelStripMode.PULSE_CHASE_B_FAST);
//			}else {
//				teensyWeensy.setPixelStripMode(4, PixelStripMode.PULSE_CHASE_R_FAST);
//				teensyWeensy.setPixelStripMode(6, PixelStripMode.PULSE_CHASE_R_FAST);
//			}
		}
	}
}
