package org.usfirst.frc.team871.util;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * @deprecated This class is deprecated because it never should have existed(It's bad practice)...
 * @author Team871
 *
 */
public class PIDReadWrite implements PIDSource, PIDOutput{

	
	private double pidIn; //Input of error
	private double pidOut; //Out put from PID
	private PIDSourceType pidSrc;

	/**
	 * @deprecated This method belongs to a deprecated class
	 */
	public PIDReadWrite(PIDSourceType pidSrc) {
		this.pidIn  = 0; 
		this.pidOut = 0;
		this.pidSrc = pidSrc;
	}
	
	@Override
	/**
	 * @deprecated This method belongs to a deprecated class
	 */
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSrc = pidSource;
		
	}

	@Override
	/**
	 * @deprecated This method belongs to a deprecated class
	 */
	public PIDSourceType getPIDSourceType() {
		return this.pidSrc;
	}

	@Override
	/**
	 * @deprecated This method belongs to a deprecated class
	 */
	public double pidGet() {
		return this.pidIn;
	}

	@Override
	/**
	 *
	 */
	public void pidWrite(double output) {
		this.pidOut = output;
	}
	
	/**
	 * @deprecated This method belongs to a deprecated class
	 * @param error input for PID
	 */
	public void errorWrite(double error) {
		this.pidIn = error;
	}

	/**
	 * @deprecated This method belongs to a deprecated class
	 * @return output from the PID calculation
	 */
	public double getPIDOut() {
		return pidOut;
	}
}
