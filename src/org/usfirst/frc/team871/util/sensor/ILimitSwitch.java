package org.usfirst.frc.team871.util.sensor;

/**
 * Applied to all limit switches
 * @author Team871
 *
 */
public interface ILimitSwitch {
	
	/**
	 * 
	 * @return If limit has been reached
	 */
    public boolean isAtLimit();
}
