package org.usfirst.frc.team871.subsystems;

import org.usfirst.frc.team871.util.control.LimitedSpeedController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;

public class SuperLift {
	private SubLift upperLift;
	private SubLift lowerLift;
	private final double baseHeight = -1; //TODO find this one
	
	public SuperLift(LimitedSpeedController upperLiftMotor, 
			DigitalInput upperUpperLimit, 
			DigitalInput upperLowerLimit, 
			Encoder upperEncoder, 
			LimitedSpeedController lowerLiftMotor, 
			DigitalInput lowerUpperLimit, 
			DigitalInput lowerLowerLimit, 
			Encoder lowerEncoder) {
		
		upperLift = new SubLift(upperLiftMotor, upperUpperLimit, upperLowerLimit, upperEncoder);
		lowerLift = new SubLift(lowerLiftMotor, lowerUpperLimit, lowerLowerLimit, lowerEncoder);
		
	}
	
	public void moveLift(double speed) {
		upperLift.moveLift(speed);
		lowerLift.moveLift(speed);
	}
	
	public void resetEncoder() {
		upperLift.resetEncoder();
		lowerLift.resetEncoder();
	}
	
	public void setEnable(boolean isEnable) {
		upperLift.setEnable(isEnable);
		lowerLift.setEnable(isEnable);
	}
	
	public void setHeight(double setPoint) {
		setPoint -= baseHeight;
		setPoint = setPoint/2;
		upperLift.setHeight(setPoint);
		lowerLift.setHeight(setPoint);
	}
	
}
