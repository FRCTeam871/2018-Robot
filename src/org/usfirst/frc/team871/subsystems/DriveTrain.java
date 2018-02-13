package org.usfirst.frc.team871.subsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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
		
		velDataPoints.add(new VelocityHolder(0,0));
		velDataPoints.add(new VelocityHolder(.1,0));
		velDataPoints.add(new VelocityHolder(.2,8.796));
		velDataPoints.add(new VelocityHolder(.3,18.6));
		velDataPoints.add(new VelocityHolder(.4,29.16));
		velDataPoints.add(new VelocityHolder(.5,39));
		velDataPoints.add(new VelocityHolder(.6,49.2));
		velDataPoints.add(new VelocityHolder(.7,58.8));
		velDataPoints.add(new VelocityHolder(.8,66.8));
		velDataPoints.add(new VelocityHolder(.9,76));
		velDataPoints.add(new VelocityHolder(1,86));

		headingPID = new PIDController(0, 0, 0, gyro, this);
		headingPID.setInputRange(-180, 180);
		headingPID.setOutputRange(-1, 1);
		headingPID.setContinuous();
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
		driveCartesian(y, x, r + (headingPID.isEnabled() ? pidRotation : 0), -gyro.getAngle());
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
		 * @param x X motor value
		 * @param y y motor value
		 */
		private void updatePosition() {
			// On the first iteration do nothing
			if(tPrevious == 0 || !enableIntegration) {
				tPrevious = System.currentTimeMillis();
				return;
			}
			
			final long curTime = System.currentTimeMillis();
			final long tDiff = curTime - tPrevious;
			final double xDistance = tDiff * calculateVelocity(lastXInput);
			final double yDistance = tDiff * calculateVelocity(lastYInput);
			
			// This isn't correct, it assumes that each component can be the same. In reality,
			// It's the resultant velocity that matters...
			displacement.setX(displacement.getX() + xDistance);
			displacement.setY(displacement.getY() + yDistance);
			
			tPrevious = curTime;
		}
		
		/**
		 * Interpolates the velocity in in/sec of the robot based off of experimental data.
		 * This data shows the relationship between motor speeds and velocities of the robot.
		 * @param speed The motor speed
		 * @return The velocity in in/sec
		 */
		private double calculateVelocity(double speed) {
			Optional<VelocityHolder> holderUpper = velDataPoints.stream().filter(dp -> speed >= dp.inputSpeed).findFirst();
			Optional<VelocityHolder> holderLower = velDataPoints.stream().filter(dp -> speed <= dp.inputSpeed).findFirst();
			
			if(!holderUpper.isPresent() || holderLower.isPresent()) {
				//Bad times, maybe should throw an exception
				return 0; 
			}
			
			final double relative = ((speed - holderLower.get().inputSpeed) / (holderUpper.get().inputSpeed - holderLower.get().inputSpeed));
			final double scale = holderUpper.get().outputSpeed - holderLower.get().outputSpeed;
			final double transformation = holderLower.get().outputSpeed;
		
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
	}

	public void getHeadingHold() {
		headingPID.getSetpoint();
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