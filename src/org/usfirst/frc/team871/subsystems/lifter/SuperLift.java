package org.usfirst.frc.team871.subsystems.lifter;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * This class controls both parts of the lift
 * 
 * @author Team871
 *
 */
public class SuperLift extends SendableBase implements Sendable {
	private SubLift upperLift;
	private SubLift lowerLift;
	private final int baseHeight = -1; // TODO find this one
	private SetpointHeights lifterHeight;
	private final Map<SetpointHeights, Double> setpointVals = new HashMap<>();

	/**
	 * Describes all states of the lifts
	 * 
	 * @author Team871
	 */
	private enum SetpointHeights {
		GROUND,
		LOW_SWITCH,
		SCALE_LOW,
		SCALE_MID,
		SCALE_HIGH,
		MANUAL
	}
	
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

		upperLift = new SubLift("Upper", upperLiftMotor, upperEncoder);
		lowerLift = new SubLift("Lower", lowerLiftMotor, lowerEncoder);

		lifterHeight = SetpointHeights.GROUND;
		
		setName("SuperLift");
		addChild(upperLift);
		addChild(lowerLift);
		
		configureSetpoints();
	}
	
	private void configureSetpoints() {
		setpointVals.put(SetpointHeights.GROUND		,(0.0 * 12.0) + 0.00);
		setpointVals.put(SetpointHeights.LOW_SWITCH	,(1.0 * 12.0) + 6.75);
		setpointVals.put(SetpointHeights.SCALE_LOW	,(6.0 * 12.0) + 4.00);
		setpointVals.put(SetpointHeights.SCALE_MID	,(5.0 * 12.0) + 4.00);
		setpointVals.put(SetpointHeights.SCALE_HIGH	,(4.0 * 12.0) + 4.00);
		setpointVals.put(SetpointHeights.MANUAL		,0.0);	
	}

	/**
	 * Controls the speed of the lift on the superLift
	 * 
	 * @param speed The speed of the lift
	 */
	public void moveLift(double speed) {
		upperLift.moveLift(speed);
		lowerLift.moveLift(speed);
		lifterHeight = SetpointHeights.MANUAL;
	}

	/**
	 * 
	 * @param setPoint Sets the height of the lift in inches above the floor
	 */
	public void setHeight(double setPoint) {
		setPoint -= baseHeight;
		setPoint /= 2;
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
			if (getHeight() < setpointVals.get(SetpointHeights.LOW_SWITCH)) {
				lifterHeight = SetpointHeights.LOW_SWITCH;
			} else if (getHeight() < setpointVals.get(SetpointHeights.SCALE_LOW)) {
				lifterHeight = SetpointHeights.SCALE_LOW;
			} else if (getHeight() < setpointVals.get(SetpointHeights.SCALE_MID)) {
				lifterHeight = SetpointHeights.SCALE_MID;
			} else {
				lifterHeight = SetpointHeights.SCALE_HIGH;
			}
			break;
		}
		
		setHeight(setpointVals.get(lifterHeight));
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
			if (getHeight() > setpointVals.get(SetpointHeights.SCALE_MID)) {
				lifterHeight = SetpointHeights.SCALE_MID;
			} else if (getHeight() > setpointVals.get(SetpointHeights.SCALE_LOW)) {
				lifterHeight = SetpointHeights.SCALE_LOW;
			} else if (getHeight() > setpointVals.get(SetpointHeights.LOW_SWITCH)) {
				lifterHeight = SetpointHeights.LOW_SWITCH;
			} else {
				lifterHeight = SetpointHeights.GROUND;
			}
			break;
		}
		setHeight(setpointVals.get(lifterHeight));
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("SuperLift");
		builder.addStringProperty("currentSetpoint", () -> lifterHeight.toString(), null);
		setpointVals.forEach((k, v) -> {
			builder.addDoubleProperty(k.toString(), () -> setpointVals.get(k), val -> setpointVals.put(k,  val));
		});
		
		builder.addDoubleProperty("height", this::getHeight, this::setHeight);
		builder.addDoubleProperty("speed", null, this::moveLift);
	}

}
