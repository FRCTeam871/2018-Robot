package org.usfirst.frc.team871.subsystems;

import org.usfirst.frc.team871.subsystems.navigation.Coordinate;
import org.usfirst.frc.team871.subsystems.navigation.IDisplacementSensor;

import com.kauailabs.navx.frc.AHRS;
import com.sun.javafx.collections.MappingChange.Map;

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
	
	private long lastTimeRan, curTime, dTime;
	private Coordinate displacement;
	private double xSpeed, ySpeed;
	
	private PIDController headingPID;
	private double pidRotation = 0;
	private final AHRS gyro;
	
	private double speedProfiles[] = {0, 0, 8.796, 18.6, 29.16, 39, 58.8, 66.84, 75.96, 86.04};
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

		headingPID = new PIDController(0, 0, 0, gyro, this);
		headingPID.setInputRange(-180, 180);
		headingPID.setOutputRange(-1, 1);
		headingPID.setContinuous();
		headingPID.disable();
		gyro.setName("DriveTrain", "Gyro");
		headingPID.setName("Heading PID");
		LiveWindow.add(headingPID);
		LiveWindow.add(gyro);
//		addChild(headingPID);
//		addChild(gyro);

		setName("DriveTrain", "DriveTrain");
	}

	/**
	 * Drives in any direction relative to the field
	 * @param x Drives along the X axis 
	 * @param y Drives along the Y axis
	 * @param r Rotation of the Robot 
	 */
	public void driveFieldOriented(double x, double y, double r) {
		updateDisplacementCoordinate(x,y);
		driveCartesian(y, x, r + (headingPID.isEnabled() ? pidRotation : 0), -gyro.getAngle());
	}

	/**
	 * Drives in any direction relative to the robot
	 * @param x Drives along the X axis
	 * @param y Drives along the Y axis
	 * @param r Rotation of the Robot
	 */
	public void driveRobotOriented(double x, double y, double r) {
		//add updateDisplacementCoordinate(x,y) that compensates for rotation
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
	
	/**
	 * 
	 * @Return the displacement from the starting position in inches as stored in Coordinate displacement 
	 */
	@Override
	public Coordinate getDisplacement() {
		updateDisplacementCoordinate();
		return displacement;
	}
	/**
	 * Sets new x and y motor values to update the current location 
	 * @param x X motor value
	 * @param y y motor value
	 */
	private void updateDisplacementCoordinate(double x, double y) {
		xSpeed = x;
		ySpeed = y;
		curTime = System.currentTimeMillis(); 
		dTime =  curTime - lastTimeRan;
		lastTimeRan = curTime;
		//Sets the new displacement values as the change in displacement added to the last displacement
		displacement.setX(displacement.getX()+calculateDistance(xSpeed, dTime));
		displacement.setY(displacement.getY()+calculateDistance(ySpeed, dTime));
		
	}
	
	/**
	 * Updates the displacement of the robot when x and y motor values haven't changed
	 */
	private void updateDisplacementCoordinate() {
		curTime = System.currentTimeMillis(); 
		dTime =  curTime - lastTimeRan;
		lastTimeRan = curTime;
		//Sets the new displacement values as the change in displacement added to the last displacement
		displacement.setX(displacement.getX()+calculateDistance(xSpeed, dTime));
		displacement.setY(displacement.getY()+calculateDistance(ySpeed, dTime));
		
	}
	/**
	 * Determines distance travelled between now and the last time this method was called in inches for either the x or y axes 
	 * @param speed The -1 to 1 joystick value
	 * @param dTime The time passed between now and last time this method was called
	 * @return the distance travelled between now and the last time this method was called in inches
	 */
	private double calculateDistance(double speed, long dTime) {
		
		return speed * dTime * (calculateVelocity(speed));
		
	}
	/**
	 * Interpolates the velocity in in/sec of the robot based off of experimental data.
	 * This data shows the relationship between motor speeds and velocities of the robot.
	 * @param speed The motor speed
	 * @return The velocity in in/sec
	 */
	private double calculateVelocity(double speed) {
		//The motor speed points that the current motor speed lies between
		int lowerRangeIndex = (int) ((speed)*10);
		int upperRangeIndex = lowerRangeIndex + 1;
		//Elements for linear interpolation of the velocity 
		double relative = (speed - (lowerRangeIndex / 10)) / ((upperRangeIndex / 10) - (lowerRangeIndex / 10));
		double scale = speedProfiles[upperRangeIndex] - speedProfiles[lowerRangeIndex];
		double transformation = speedProfiles[lowerRangeIndex];
	
		return (relative * scale) + transformation;
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

}