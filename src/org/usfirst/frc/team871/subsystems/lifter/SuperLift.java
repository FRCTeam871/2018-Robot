package org.usfirst.frc.team871.subsystems.lifter;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team871.util.config.IRobotConfiguration;
import org.usfirst.frc.team871.util.config.ThrustmasterControlScheme;
import org.usfirst.frc.team871.util.control.CompositeLimitedSpeedController;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * This class controls both parts of the lift
 * 
 * @author Team871 w/ pancake
 *
 */
public class  SuperLift extends SendableBase implements Sendable {
	private SubLift upperLift;
	private SubLift lowerLift;
	private final int baseHeight = 0; // TODO find this one
	private SetpointHeights lifterHeight;
	private final Map<SetpointHeights, Double> setpointVals = new HashMap<>();
	private NetworkTable table;
	private IRobotConfiguration config;

	/**
	 * Describes all states of the lifts
	 * 
	 * @author Team871
	 */
	public enum SetpointHeights {
		GROUND,
		LOW_SWITCH,
		SCALE_LOW,
		SCALE_MID,
		SCALE_HIGH,
		MAXIMUM,
		MANUAL
	}
	
	/**
	 * @param upperLiftMotor
	 *            The Motor that controls the upper part of the lift
	 * @param upperEncoder
	 *            Measures the distance of the upper part of the lift
	 * @param lowerLiftMotor
	 *            The Motor that controls the lower part of the lift
	 * @param lowerEncoder
	 *            Measures the distance of the lower part of the lift
     * @param table The network table to which state information will be posted.  Will also be passed to sublifts.
	 */
	public SuperLift(CompositeLimitedSpeedController upperLiftMotor, Encoder upperEncoder,
			CompositeLimitedSpeedController lowerLiftMotor, Encoder lowerEncoder, NetworkTable table, IRobotConfiguration config) {

		upperLift = new SubLift("Upper", upperLiftMotor, upperEncoder, table, config);
		lowerLift = new SubLift("Lower", lowerLiftMotor, lowerEncoder, table, config);
		this.config = config;
		this.table = table;

		lifterHeight = SetpointHeights.GROUND;

		addChild(upperLift);
		addChild(lowerLift);
		setName("SuperLift", "SuperLift");
		
		configureSetpoints();
	}
	
	private void configureSetpoints() {
		setpointVals.put(SetpointHeights.GROUND		,(0.0 * 12.0) + 0.00);
		setpointVals.put(SetpointHeights.LOW_SWITCH	,(2.0 * 12.0) + 6.75);
		setpointVals.put(SetpointHeights.SCALE_LOW	,(4.0 * 12.0) + 4.00);
		setpointVals.put(SetpointHeights.SCALE_MID	,(5.0 * 12.0) + 4.00);
		setpointVals.put(SetpointHeights.SCALE_HIGH	,(6.0 * 12.0) + 4.00);
		setpointVals.put(SetpointHeights.MAXIMUM	, 85.0);
		setpointVals.put(SetpointHeights.MANUAL		, 0.0);	
	}

	/**
	 * Controls the speed of the lift on the superLift
	 * 
	 * @param position The position of the lift, in the range [-1, 1]
	 */
	public void moveLift(double position) {
		lifterHeight = SetpointHeights.MANUAL;
		double min = setpointVals.get(SetpointHeights.GROUND);
		double max = setpointVals.get(SetpointHeights.MAXIMUM);
		double height = ((position + 1) / 2.0) * (max - min) + min;
		setHeight(height);
	}

	/**
	 * 
	 * @param setPoint Sets the height of the lift in inches above the floor
	 */
	public void setHeight(double setPoint) {
		boolean doEncoderReset = false;
		if(doEncoderReset) {
	//		System.out.println("========================");
	//		System.out.println("Lower");
			lowerLift.maybeResetEncoder(config.getResetLowerEncoderSwitch());
	//		System.out.println();
	//		System.out.println("Upper");
			upperLift.maybeResetEncoder(config.getResetUpperEncoderSwitch());
		}
		
		setPoint -= baseHeight;
		setPoint /= 2;
		upperLift.setHeight(setPoint);
		lowerLift.setHeight(setPoint);
		table.getEntry("liftSetpoint").setDouble(setPoint);
	}
	
	public void setHeight(SetpointHeights setPoint) {
		setHeight(getSetpointHeight(setPoint));
	}

	/**
	 * Moves the lifter to the highest setpoint
	 */
	public void setTop() {
		lifterHeight = SetpointHeights.MAXIMUM;
		setHeight(setpointVals.get(lifterHeight));
	}

	/**
	 * Moves the lifter to the lowest setpoint
	 */
	public void setBottom() {
		lifterHeight = SetpointHeights.GROUND;
		setHeight(setpointVals.get(lifterHeight));
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
		case MAXIMUM:

			break;
		case SCALE_HIGH:
			lifterHeight = SetpointHeights.MAXIMUM;
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
		case MAXIMUM:
			lifterHeight = SetpointHeights.SCALE_HIGH;
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
	
	/**
	 * Update the dashboard data.  Should be called periodically to ensure timely values.
	 * 
	 * @author The Jack
	 */
	public void updateData() {
		lowerLift.updateData();
		upperLift.updateData();
		
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("SuperLift");
		builder.addStringProperty("currentSetpoint", () -> lifterHeight.toString(), null);
		setpointVals.forEach((k, v) -> builder.addDoubleProperty(k.toString(), () -> setpointVals.get(k), val -> setpointVals.put(k,  val)));
		
		builder.addDoubleProperty("height", this::getHeight, this::setHeight);
		builder.addDoubleProperty("speed", null, this::moveLift);
	}

	public boolean isAtSetpoint() {
		return lowerLift.isAtSetpoint() && upperLift.isAtSetpoint();
	}
	
	public double getSetpointHeight(SetpointHeights height) {
		return setpointVals.get(height);
	}
	
	public void setTrim(double upper, double lower) {
		lowerLift.setTrim(lower);
		upperLift.setTrim(upper);
	}
	
}
