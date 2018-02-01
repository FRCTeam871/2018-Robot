package org.usfirst.frc.team871.util.sensor;

import edu.wpi.first.wpilibj.Encoder;

/**
 * Can set the limits, whether they are upper or lower limits, and can tell if the limit has been reached
 * @author Team871
 *
 */
public class EncoderLimitSwitch implements ILimitSwitch {
	private boolean triggerAboveThreshHold;
	
	private int threshHold;
	
	private Encoder input;
	
	/**
	 * 
	 * @param input The sensor
	 * @param threshHold The limit
	 * @param triggerAboveThreshHold True for upper limit sensor and false for lower limit sensor.
	 */
	public EncoderLimitSwitch(Encoder input, int threshHold, boolean triggerAboveThreshHold) {
		this.input = input;
		this.threshHold = threshHold;
		this.triggerAboveThreshHold = triggerAboveThreshHold;
	}
	
	/**
	 * 
	 * @param threshHold The limit
	 */
	public void setThreshold(int threshHold) {
		this.threshHold = threshHold;
	}
	
	/**
	 * 
	 * @param triggerAboveThreshHold True for upper limit sensor and false for lower limit sensor.
	 */
	public void setTrigger(boolean triggerAboveThreshHold) {
		this.triggerAboveThreshHold = triggerAboveThreshHold;
	}
	
	@Override
	public boolean isAtLimit() {
		return triggerAboveThreshHold ? input.get() > threshHold : input.get() < threshHold;
	}
}
