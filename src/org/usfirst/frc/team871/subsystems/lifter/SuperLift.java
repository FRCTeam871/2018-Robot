package org.usfirst.frc.team871.subsystems.lifter;

import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;

import edu.wpi.first.wpilibj.Encoder;

/**
 * This class controls both parts of the lift
 * 
 * @author Team871
 *
 */
public class SuperLift {
	private SubLift upperLift;
	private SubLift lowerLift;
	private final int baseHeight = -1; // TODO find this one
	private SetpointHeights lifterHeight;

	/**
	 * 
	 * @param upperLiftMotor
	 *            The Motor that controls the upper part of the lift
	 * @param upperEncoder
	 *            Measures the distance of the upper part of the lift
	 * @param lowerLiftMotor
	 *            The Motor that controls the lower part of the lift
	 * @param lowerEncoder
	 *            Measures the distance of the lower part of the lift
	 */
	public SuperLift(CompositeLimitedSpeedController upperLiftMotor, Encoder upperEncoder,
			CompositeLimitedSpeedController lowerLiftMotor, Encoder lowerEncoder) {

		upperLift = new SubLift(upperLiftMotor, upperEncoder);
		lowerLift = new SubLift(lowerLiftMotor, lowerEncoder);

		lifterHeight = SetpointHeights.GROUND;

	}

	/**
	 * Controls the speed of the lift on the superLift
	 * 
	 * @param speed
	 *            The speed of the lift
	 */
	public void moveLift(double speed) {
		upperLift.moveLift(speed);
		lowerLift.moveLift(speed);
		lifterHeight = SetpointHeights.MANUAL;
	}
	
	/**
	 * Resets the encoder for the upper and lower lift
	 */
	public void resetEncoder() {
		upperLift.resetEncoder();
		lowerLift.resetEncoder();
	}

	/**
	 * 
	 * @param setPoint
	 *            sets the height of the lift in inches above the floor
	 */
	public void setHeight(double setPoint) {
		setPoint -= baseHeight;
		setPoint = setPoint / 2;
		upperLift.setHeight(setPoint);
		lowerLift.setHeight(setPoint);
	}

	/**
	 * Moves the lifter to the highest setpoint
	 */
	public void setTop() {
		lifterHeight = SetpointHeights.SCALE_HIGH;
	}

	/**
	 * Moves the lifter to the lowest setpoint
	 */
	public void setBottom() {
		lifterHeight = SetpointHeights.GROUND;
	}

	/**
	 * gets the height of the grabber off the ground in inches
	 * 
	 * @return returns the height of the grabber off the ground in inches
	 */
	public double getHeight() {
		return upperLift.getHeight() + lowerLift.getHeight() + baseHeight;
	}

	/**
	 * Enables PID in the upper and lower lifts
	 */
	public void setEnablePID() {
		upperLift.setEnablePID(true);
		lowerLift.setEnablePID(true);
	}

	/**
	 * Describes all states of the lifts
	 * 
	 * @author Team871
	 *
	 */
	private enum SetpointHeights {
		GROUND(-1), LOW_SWITCH(-1), SCALE_LOW(-1), SCALE_MID(-1), SCALE_HIGH(-1), MANUAL(-1);
		/**
		 * Describes the height of the setpoint in inches
		 */
		double height;

		private SetpointHeights(double height) {
			this.height = height;
		}
	}

	/**
	 * Increments the setpoint of the lift This method cannot increment from higher
	 * than the highest setpoint Manual will always set to the highest available
	 * state
	 */
	public void increaseSetpoint() {
		switch (lifterHeight) {
		case SCALE_HIGH:

			break;
		case SCALE_MID:
			lifterHeight = SetpointHeights.SCALE_HIGH;
			break;
		case SCALE_LOW:
			lifterHeight = SetpointHeights.SCALE_MID;
			break;
		case LOW_SWITCH:
			lifterHeight = SetpointHeights.SCALE_LOW;
			break;
		case GROUND:
			lifterHeight = SetpointHeights.LOW_SWITCH;
			break;
		case MANUAL:
			if (getHeight() < SetpointHeights.LOW_SWITCH.height) {
				lifterHeight = SetpointHeights.LOW_SWITCH;
			} else if (getHeight() < SetpointHeights.SCALE_LOW.height) {
				lifterHeight = SetpointHeights.SCALE_LOW;
			} else if (getHeight() < SetpointHeights.SCALE_MID.height) {
				lifterHeight = SetpointHeights.SCALE_MID;
			} else {
				lifterHeight = SetpointHeights.SCALE_HIGH;
			}
			break;
		}
		
		setHeight(lifterHeight.height);
	}

	/**
	 * Decrements the setpoint of the lift This method cannot decrement from lower
	 * than the ground setpoint' Manual case will always set to the nearest lowest
	 * state
	 */
	public void decreaseSetpoint() {

		switch (lifterHeight) {
		case GROUND:

			break;
		case LOW_SWITCH:
			lifterHeight = SetpointHeights.GROUND;
			break;
		case SCALE_LOW:
			lifterHeight = SetpointHeights.LOW_SWITCH;
			break;
		case SCALE_MID:
			lifterHeight = SetpointHeights.SCALE_LOW;
			break;
		case SCALE_HIGH:
			lifterHeight = SetpointHeights.SCALE_MID;
			break;
		case MANUAL:
			if (getHeight() > SetpointHeights.SCALE_MID.height) {
				lifterHeight = SetpointHeights.SCALE_MID;
			} else if (getHeight() > SetpointHeights.SCALE_LOW.height) {
				lifterHeight = SetpointHeights.SCALE_LOW;
			} else if (getHeight() > SetpointHeights.LOW_SWITCH.height) {
				lifterHeight = SetpointHeights.LOW_SWITCH;
			} else {
				lifterHeight = SetpointHeights.GROUND;
			}
			break;
		}
		setHeight(lifterHeight.height);
	}

	public boolean isAtSetpoint(double Setpoint) {
		double height = this.getHeight();
		double max = height+1.0;
		double min = height-1.0;
		
		if((height < max) && (height > min)) {
			return true;
		}
		else {
			return false;
		}
	}
}
