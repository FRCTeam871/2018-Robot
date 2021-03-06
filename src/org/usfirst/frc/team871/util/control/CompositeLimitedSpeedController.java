package org.usfirst.frc.team871.util.control;

import java.util.List;

import org.usfirst.frc.team871.util.sensor.ILimitSwitch;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
/**
 * Uses speed controllers while using microswitches and encoders as limit switches.<br>
 * TODO: change this class so that it extends LimitedSpeedController and uses a composite limit switch instead.
 * @author Team871
 */
public class CompositeLimitedSpeedController extends SendableBase implements SpeedController, Sendable {
	private SpeedController motor;
	private List<ILimitSwitch> upperLimitss;
	private List<ILimitSwitch> lowerLimitss;
	private boolean yoloMode = false;

	private static int instanceCnt = 0;

	/**
	 * 
	 * @param motor The speed controller being limited
	 * @param upperLimitss Arraylist of upper limit switches
	 * @param lowerLimitss Arraylist of lower limit switches
	 * @param inverted Determines if the speed controller output should be negated
	 */
	public CompositeLimitedSpeedController(SpeedController motor, List<ILimitSwitch> upperLimitss, List<ILimitSwitch> lowerLimitss) {
		this.motor = motor;
		this.upperLimitss = upperLimitss;
		this.lowerLimitss = lowerLimitss;
		
		addChild(motor);
		setName("CLSC", ++instanceCnt);
	}
	
	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public double get() {
		return motor.get();
	}
	
	@Override
	public void set(double speed) {
		if(isAtUpperLimit() && speed > 0 || isAtLowerLimit() && speed < 0) {
			motor.set(0);
		} else {
			motor.set(speed);
		}
		
	}

	@Override
	public void setInverted(boolean isInverted) {
		motor.setInverted(isInverted);
	}

	@Override
	public boolean getInverted() {
		return motor.getInverted();
	}

	@Override
	public void disable() {
		motor.disable();
	}

	@Override
	public void stopMotor() {
		motor.stopMotor();
	}
	
	/**
	 * @return Returns the upper limit switches in an arrayList
	 */
	public List<ILimitSwitch> getUpperSensor() {
		return upperLimitss;
	}
	
	/**
	 * @return Returns the lower limit switches in an arrayList
	 */
	public List<ILimitSwitch> getLowerSensor() {
		return lowerLimitss;
	}

	/**
	 * @return Returns true if an upperLimit has been reached
	 */
	public boolean isAtUpperLimit() {
		return !yoloMode && upperLimitss.stream().anyMatch(ILimitSwitch::isAtLimit);
	}
	
	/**
	 * @return Returns true if a lowerLimit has been reached
	 */
	public boolean isAtLowerLimit() {
		return !yoloMode && lowerLimitss.stream().anyMatch(ILimitSwitch::isAtLimit);
	}
	
	/**
	 * Are limits disabled?
	 */
	public boolean isYoloMode() {
		return yoloMode;
	}
	
	/**
	 * Enable or disable all limits -- Hence YOLO mode.
	 */
	public void setYoloMode(boolean shouldYolo) {
		yoloMode = shouldYolo;
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("CompositeLimitedSpeedController");
		builder.setSafeState(this::stopMotor);

		for(int i = 0; i<upperLimitss.size(); i++) {
			final ILimitSwitch limit = upperLimitss.get(i);
			builder.addBooleanProperty("ul"+i, limit::isAtLimit, null);
		}
		
		for(int i = 0; i<lowerLimitss.size(); i++) {
			final ILimitSwitch limit = lowerLimitss.get(i);
			builder.addBooleanProperty("ll"+i, limit::isAtLimit, null);
		}
		
		builder.addBooleanProperty("isAtUpperLimit", this::isAtUpperLimit, null);
		builder.addBooleanProperty("isAtLowerLimit", this::isAtLowerLimit, null);
		builder.addBooleanProperty("inverted", this::getInverted, this::setInverted);
		builder.addDoubleProperty("speed", this::get, null);
		builder.addBooleanProperty("yoloMode", this::isYoloMode, this::setYoloMode);
	}
}