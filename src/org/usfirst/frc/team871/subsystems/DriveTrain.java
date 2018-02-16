package org.usfirst.frc.team871.subsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team871.subsystems.navigation.Coordinate;
import org.usfirst.frc.team871.subsystems.navigation.Sensors.IDisplacementSensor;
import org.usfirst.frc.team871.util.units.DistanceUnit;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive class used for driving mecanum drive with additional features.
 * @author Jack Langhorn contributed to by Scott Demarest and JR DiBenedetto
 */
public class DriveTrain extends MecanumDrive implements IDisplacementSensor, PIDOutput {
	private volatile Coordinate displacement;
	
	private PIDController headingPID;
	private double pidRotation = 0;
	private final AHRS gyro;
	private final long integrationRate = 20; //in milliseconds
	private double lastXInput;
	private double lastYInput;
	
	private boolean enableIntegration = true;
	private Timer positionIntegrator;
	private final List<VelocityHolder> velDataPoints = new ArrayList<>();
	
	private class VelocityHolder {
		public double inputSpeed;
		public double outputSpeed;
		
		VelocityHolder(double input, double output) {
			this.inputSpeed = input;
			this.outputSpeed = output;
		}
	}
	
	/**
	 * 	Initializes the gyro and drive motors with the SpeedControllers
	 * @param rearRight The rear right speed controller
	 * @param rearLeft The rear left speed controller
	 * @param frontRight The front right speed controller
	 * @param frontLeft The front left speed controller
	 * @param gyro A NavX that is used for field oriented driving
	 */
	public DriveTrain(SpeedController rearRight, SpeedController rearLeft, SpeedController frontRight, SpeedController frontLeft, AHRS gyro ) {
		super(frontLeft, rearLeft, frontRight, rearRight);
		this.gyro = gyro;
		
		velDataPoints.add(new VelocityHolder(0,  0));
		velDataPoints.add(new VelocityHolder(.1, 0));
		velDataPoints.add(new VelocityHolder(.2, 15.8));
		velDataPoints.add(new VelocityHolder(.3, 28.2));
		velDataPoints.add(new VelocityHolder(.4, 40.95));
		velDataPoints.add(new VelocityHolder(.5, 53.95));
		velDataPoints.add(new VelocityHolder(.6, 64.7));
		velDataPoints.add(new VelocityHolder(.7, 72.33));
		velDataPoints.add(new VelocityHolder(.8, 82.34));
		velDataPoints.add(new VelocityHolder(.9, 92.167));
		velDataPoints.add(new VelocityHolder(1,  100));

		headingPID = new PIDController(0.03, 0, 0.03, gyro, this);
		headingPID.setInputRange(-180, 180);
		headingPID.setOutputRange(-0.5, 0.5);
		headingPID.setContinuous();
		headingPID.setAbsoluteTolerance(5);
		headingPID.disable();
		
		gyro.setName("DriveTrain", "Gyro");
		headingPID.setName("Heading PID");
		LiveWindow.add(headingPID);
		LiveWindow.add(gyro);
		
		positionIntegrator = new Timer();
		positionIntegrator.schedule(new IntegrationTask(), 0, integrationRate);

		setName("DriveTrain", "DriveTrain");
	}

	/**
	 * Drives in any direction relative to the field
	 * @param x Drives along the X axis 
	 * @param y Drives along the Y axis
	 * @param r Rotation of the Robot 
	 */
	public void driveFieldOriented(double x, double y, double r) {
		lastXInput = x;
		lastYInput = y;
		driveCartesian(y, x, r + (headingPID.isEnabled() ? pidRotation : 0), -gyro.getAngle());
	}
	
	@Override
	public void drivePolar(double magnitude, double angle, double r) {
		lastXInput = Math.cos(angle) * magnitude;
		lastYInput = Math.sin(angle) * magnitude;
		super.drivePolar(magnitude, angle, r + (headingPID.isEnabled() ? pidRotation : 0));
	}

	/**
	 * Drives in any direction relative to the robot
	 * @param x Drives along the X axis
	 * @param y Drives along the Y axis
	 * @param r Rotation of the Robot
	 */
	public void driveRobotOriented(double x, double y, double r) {
		lastXInput = x;
		lastYInput = y;
		driveCartesian(y, x, r + (headingPID.isEnabled() ? pidRotation : 0));
	}

	public void setHeadingPIDEnabled(boolean enabled) {
		headingPID.setEnabled(enabled);
	}
	

	/**
	 * resets gyro yaw to 0 at current yaw
	 */
	public void resetGyro() {
		gyro.zeroYaw();
	}

	/**
	 *
	 * @return -s the gyro for use elsewhere
	 */
	public AHRS getGyro() {
		return gyro;
	}
	
	private class IntegrationTask extends TimerTask {
		private long tPrevious = 0;
		
		/**
		 * Sets new x and y motor values to update the current location
		 */
		private void updatePosition() {
			SmartDashboard.putNumber("updStart", new Random().nextDouble());
			SmartDashboard.putBoolean("enableIntegration", enableIntegration);
			SmartDashboard.putNumber("tPrevious", tPrevious);
			SmartDashboard.putNumber("prevX", lastXInput);
			SmartDashboard.putNumber("prevY", lastYInput);
			// On the first iteration do nothing
			if(tPrevious == 0 || !enableIntegration) {
				tPrevious = System.currentTimeMillis();
				return;
			}
			
			if(displacement == null) {
				displacement = new Coordinate(0, 0);
			}
			
			
			final long curTime = System.currentTimeMillis();
			final double tDiff = (curTime - tPrevious) / 1000.0;
			SmartDashboard.putNumber("xVel", calculateVelocity(lastXInput));
			SmartDashboard.putNumber("yVel", calculateVelocity(lastYInput));
			final double xDistance = tDiff * calculateVelocity(lastXInput);
			final double yDistance = tDiff * calculateVelocity(lastYInput);
			
			Vector2d vec = new Vector2d(xDistance, yDistance);
			vec.rotate(gyro.getAngle());
			
			SmartDashboard.putNumber("vecX", vec.x);
			SmartDashboard.putNumber("vecY", vec.y);
			
			// This isn't correct, it assumes that each component can be the same. In reality,
			// It's the resultant velocity that matters...
			displacement.setX(displacement.getX() + vec.x);
			displacement.setY(displacement.getY() + vec.y);
			
			tPrevious = curTime;
			SmartDashboard.putNumber("upd", tDiff);
		}
		
		/**
		 * Interpolates the velocity in in/sec of the robot based off of experimental data.
		 * This data shows the relationship between motor speeds and velocities of the robot.
		 * @param speed The motor speed
		 * @return The velocity in in/sec
		 */
		private double calculateVelocity(double speed) {
			
			VelocityHolder holderUpper = null;
			VelocityHolder holderLower = null;
			
			for(int i = 0; i < velDataPoints.size(); i++) {
				VelocityHolder vel = velDataPoints.get(i);
				if(speed <= vel.inputSpeed && holderUpper == null) {
					holderUpper = vel;
					if(speed == vel.inputSpeed) {
						holderLower = holderUpper;
					}else {
						holderLower = velDataPoints.get(Math.max(0, Math.min(i - 1, velDataPoints.size()-1)));
					}
					break;
				}
			}
			
			if(holderLower == null || holderUpper == null) {
				throw new RuntimeException("YOU ARE BORKED");
				//Bad times, maybe should throw an exception
			}
			
			if(holderLower == holderUpper) {
				return holderLower.outputSpeed;
			}
			
			final double relative = ((speed - holderLower.inputSpeed) / (holderUpper.inputSpeed - holderLower.inputSpeed));
			final double scale = holderUpper.outputSpeed - holderLower.outputSpeed;
			final double transformation = holderLower.outputSpeed;
		
			return (relative * scale) + transformation;
		}

		@Override
		public void run() {
			updatePosition();
		}
	}
	
	@Override
	public void pidWrite(double output) {
		pidRotation = output;
	}

	/**
	 * @param heading - the heading hold, in degrees
	 */
	public void setHeadingHold(double heading) {
		headingPID.setSetpoint(heading);
		headingPID.enable();
	}
	
	public double getHeadingHold() {
		return headingPID.getSetpoint();
	}
	
	public boolean isAtSetpoint() {
		return headingPID.onTarget();
	}

	public void enableHeadingHold(){
		headingPID.enable();
	}

	public void disableHeadingHold(){
		headingPID.disable();
	}

	@Override
	public Coordinate getDisplacement(DistanceUnit unit) {
		return displacement;
	}

	@Override
	public Coordinate getVelocity(DistanceUnit unit) {
		return new Coordinate(0,0);
	}

	@Override
	public void resetSensor() {
		enableIntegration = false;
		displacement = new Coordinate(0,0);
		enableIntegration = true;
	}

}