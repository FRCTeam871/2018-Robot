
//TODO: MAKE VARS AN INTERFACE THAT CAN  SWICTH BETWEEN EACH ROBOT INTERFACES

package org.usfirst.frc.team871.robot;

import org.usfirst.frc.team871.subsystems.DriveTrain;
import org.usfirst.frc.team871.subsystems.Grabber;
import org.usfirst.frc.team871.util.config.IRobotConfiguration;
import org.usfirst.frc.team871.util.config.MainRobotConfiguration;
import org.usfirst.frc.team871.util.joystick.ButtonTypes;
import org.usfirst.frc.team871.util.joystick.EnhancedXBoxController;
import org.usfirst.frc.team871.util.joystick.XBoxAxes;
import org.usfirst.frc.team871.util.joystick.XBoxButtons;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
		
	private DriveTrain drive;
	private IRobotConfiguration config;
	private EnhancedXBoxController xbox;
	private Grabber grabber;
	
	@Override
	public void robotInit() {
		xbox = new EnhancedXBoxController(0);
		config = MainRobotConfiguration.getConfiguration();
		drive = new DriveTrain(config.getRearRightMotor(), config.getRearLeftMotor(), config.getFrontRightMotor(), config.getFrontLeftMotor(), config.getGyroscope());
		xbox.setButtonMode(XBoxButtons.START, ButtonTypes.TOGGLE);
		xbox.setButtonMode(XBoxButtons.BACK, ButtonTypes.RISING);
		grabber = new Grabber(config.getGrabPiston(), config.getEjectPiston(), config.getCubeDetector());
	}

	@Override
	public void autonomousInit() {
		
	}

	@Override
	public void autonomousPeriodic() {

	}

	@Override
	public void teleopPeriodic() {
		if (xbox.getValue(XBoxButtons.START)) {
			drive.driveRobotOriented(xbox.getValue(XBoxAxes.LEFTX), xbox.getValue(XBoxAxes.LEFTY), xbox.getValue(XBoxAxes.RIGHTX));
		} else {
			drive.driveFieldOriented(xbox.getValue(XBoxAxes.LEFTX), xbox.getValue(XBoxAxes.LEFTY), xbox.getValue(XBoxAxes.RIGHTX));
		}
		if (xbox.getValue(XBoxButtons.BACK)) {
			drive.resetGyro();
		}
		if(xbox.getValue(XBoxButtons.A)) {
			grabber.toggleGrabber();
		}
		if(xbox.getValue(XBoxButtons.B)) {
			grabber.ejectCube();
		}
		
	}

	@Override
	public void testPeriodic() {
		
	}
}
