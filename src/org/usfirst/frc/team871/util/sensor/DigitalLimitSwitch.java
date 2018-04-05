package org.usfirst.frc.team871.util.sensor;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * A limit switch that uses digital sensors
 * @author Team871
 *
 */
public class DigitalLimitSwitch implements ILimitSwitch {
    private boolean activeLow;
    
    private DigitalInput input;

    /**
     * 
     * @param input The digital sensor
     * @param activeLow If the sensor is active low or not
     */
    public DigitalLimitSwitch(DigitalInput input, boolean activeLow) {
        this.input = input;
        this.activeLow = activeLow;
    }

    /**
     * Defaults to active high
     * @param input The digital sensor
     */
    public DigitalLimitSwitch(DigitalInput input) {
        this.input = input;
        this.activeLow = false;
    }

    /**
     * 
     * @param activeLow If the sensor is active low or not
     */
    public void setActiveLow(boolean activeLow) {
        this.activeLow = activeLow;
    }

    @Override
    public boolean isAtLimit() {
        return (activeLow) != input.get();
    }
    
    /**
     * 
     * @return Returns the sensor
     */
    public DigitalInput getRawInput() {
    	return input;
    }
}