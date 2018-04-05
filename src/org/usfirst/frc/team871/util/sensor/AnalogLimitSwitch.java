package org.usfirst.frc.team871.util.sensor;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * A limit switch that uses analog sensors
 * @author Team871
 *
 */
public class AnalogLimitSwitch implements ILimitSwitch {
    private boolean triggerAboveThreshold;

    private double threshold;

    private AnalogInput input;

    /**
     * 
     * @param input The analog sensors that are used
     * @param threshold The limit
     * @param triggerAboveThreshold True for upper limit sensor and false for lower limit sensor.
     */
    public AnalogLimitSwitch(AnalogInput input, double threshold, boolean triggerAboveThreshold) {
        this.input = input;
        this.threshold = threshold;
        this.triggerAboveThreshold = triggerAboveThreshold;
    }

    /**
     * 
     * @param threshold The limit
     */
    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    /**
     * 
     * @param triggerAboveThreshold True for upper limit sensor and false for lower limit sensor.
     */
    public void setTrigger(boolean triggerAboveThreshold) {
        this.triggerAboveThreshold = triggerAboveThreshold;
    }

    @Override
    public boolean isAtLimit() {
        if (triggerAboveThreshold) {
            return input.getVoltage() > threshold;
        } else {
            return input.getVoltage() < threshold;
        }
    }
}