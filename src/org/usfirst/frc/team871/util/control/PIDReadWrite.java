package org.usfirst.frc.team871.util.control;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDReadWrite implements PIDSource, PIDOutput{

	
	private double pidIn; //Input of error
	private double pidOut; //Out put from PID
	private PIDSourceType pidSrc;
		
	public PIDReadWrite(PIDSourceType pidSrc) {
		this.pidIn  = 0; 
		this.pidOut = 0;
		this.pidSrc = pidSrc;
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSrc = pidSource;
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return this.pidSrc;
	}

	@Override
	public double pidGet() {
		return this.pidIn;
	}

	@Override
	public void pidWrite(double output) {
		this.pidOut = output;
	}
	
	/**
	 * 
	 * @param error input for PID
	 */
	public void errorWrite(double error) {
		this.pidIn = error;
	}

	/**
	 * 
	 * @return output from the PID calculation
	 */
	public double getPIDOut() {
		return pidOut;
	}
}
