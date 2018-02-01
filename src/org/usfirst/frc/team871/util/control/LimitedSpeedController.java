package org.usfirst.frc.team871.util.control;

import org.usfirst.frc.team871.util.sensor.ILimitSwitch;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * This class is able to control the motor with the use of limit switches
 * @author Team871
 *
 */
public class LimitedSpeedController implements SpeedController {
    private SpeedController motor;

    private ILimitSwitch upper;
    private ILimitSwitch lower;
    
    /**
     *
     * @param motor The speed controller being limited
     * @param upper The upper limit switch
     * @param lower The lower limit switch
     * @param inverted Determines if the speed controller output should be negated
     */
    public LimitedSpeedController(SpeedController motor, ILimitSwitch upper, ILimitSwitch lower, boolean inverted) {
        this.motor = motor;
        this.upper = upper;
        this.lower = lower;

        motor.setInverted(inverted);
    }

    /**
     * The motor is not inverted by default
     * @param motor The speed controller being limited
     * @param upper The upper limit switch
     * @param lower The lower limit switch
     */
    public LimitedSpeedController(SpeedController motor, ILimitSwitch upper, ILimitSwitch lower) {
        this.motor = motor;
        this.upper = upper;
        this.lower = lower;

        motor.setInverted(false);
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
        if ((upper.isAtLimit() && (speed > 0.0)) || (lower.isAtLimit() && (speed < 0.0))) {
            motor.set(0.0);
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
     * 
     * @return Returns the upper limit
     */
    public ILimitSwitch getUpperLimit() {
    	return upper;
    }
    
    /**
     * 
     * @return Returns the lower limit
     */
    public ILimitSwitch getLowerLimit() {
    	return lower;
    }
}