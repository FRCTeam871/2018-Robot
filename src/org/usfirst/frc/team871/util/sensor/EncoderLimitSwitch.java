package org.usfirst.frc.team871.util.sensor;

import edu.wpi.first.wpilibj.Encoder;

public class EncoderLimitSwitch implements ILimitSwitch {
	boolean triggerAboveThreshHold;
	
	int threshHold;
	
	Encoder input;
	
	public EncoderLimitSwitch(Encoder input, int threshHold, boolean triggerAboveThreshHold) {
		this.input = input;
		this.threshHold = threshHold;
		this.triggerAboveThreshHold = triggerAboveThreshHold;
	}
	
	public void setThreshHold(int threshHold) {
		this.threshHold = threshHold;
	}
	
	public void setTrigger(boolean triggerAboveThreshHold) {
		this.triggerAboveThreshHold = triggerAboveThreshHold;
	}
	
	@Override
	public boolean isAtLimit() {
		return triggerAboveThreshHold ? input.get() > threshHold : input.get() < threshHold;
	}
}
