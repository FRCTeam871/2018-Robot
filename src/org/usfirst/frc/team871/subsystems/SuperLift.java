package org.usfirst.frc.team871.subsystems;

import org.usfirst.frc.team871.robot.CompositeLimitedSpeedController;

import edu.wpi.first.wpilibj.Encoder;

public class SuperLift {
	private SubLift upperLift;
	private SubLift lowerLift;
	private final int baseHeight = -1; //TODO find this one
	private SetpointHeights lifterHeight;
	
	public SuperLift(CompositeLimitedSpeedController upperLiftMotor,  
			Encoder upperEncoder, 
			CompositeLimitedSpeedController lowerLiftMotor, 
			Encoder lowerEncoder) {
		
		upperLift = new SubLift(upperLiftMotor, upperEncoder);
		lowerLift = new SubLift(lowerLiftMotor, lowerEncoder);
		
		lifterHeight = SetpointHeights.GROUND;
		
	}
	
	public void moveLift(double speed) {
		upperLift.setEnablePID(false);
		lowerLift.setEnablePID(false);
		upperLift.moveLift(speed);
		lowerLift.moveLift(speed);
		lifterHeight = SetpointHeights.MANUAL;
	}
	
	public void resetEncoder() {
		upperLift.resetEncoder();
		lowerLift.resetEncoder();
	}
	
	public void setHeight(double setPoint) {
		setPoint -= baseHeight;
		setPoint = setPoint/2;
		upperLift.setHeight(setPoint);
		lowerLift.setHeight(setPoint);
	}
	
	public void setTop() {
		lifterHeight = SetpointHeights.SCALE_HIGH;
	}
	
	public void setBottom() {
		lifterHeight = SetpointHeights.GROUND;
	}
	
	public int getHeight() {
		return (upperLift.getHeight()+lowerLift.getHeight()+baseHeight);
	}
	
	public void setEnablePID() {
		upperLift.setEnablePID(true);
		lowerLift.setEnablePID(true);
		
	}
	
	private enum SetpointHeights{
		GROUND(-1),
		LOW_SWITCH(-1),
		SCALE_LOW(-1),
		SCALE_MID(-1),
		SCALE_HIGH(-1),
		MANUAL(-1);
		
		int height;
		
		private SetpointHeights(int height) {
			this.height = height;
		}
	}
	
	public void increaseSetpoint() {
		switch(lifterHeight) {
			case SCALE_HIGH:
				
				break;
			case SCALE_MID:
				lifterHeight = SetpointHeights.SCALE_HIGH;
				break;
			case SCALE_LOW:
				lifterHeight = SetpointHeights.SCALE_MID;
				break;
			case LOW_SWITCH:
				lifterHeight = SetpointHeights.SCALE_LOW;
				break;
			case GROUND:
				lifterHeight = SetpointHeights.LOW_SWITCH;
				break;
			case MANUAL:
				if(getHeight() < SetpointHeights.LOW_SWITCH.height) {
					lifterHeight = SetpointHeights.LOW_SWITCH;
				}else if(getHeight() < SetpointHeights.SCALE_LOW.height) {
					lifterHeight = SetpointHeights.SCALE_LOW;
				}else if(getHeight() < SetpointHeights.SCALE_MID.height) {
					lifterHeight = SetpointHeights.SCALE_MID;
				}else {
					lifterHeight = SetpointHeights.SCALE_HIGH;
				}
				break;
		}
		setHeight(lifterHeight.height);
	}
	
	public void decreaseSetpoint() {
		
		switch(lifterHeight) {
		case GROUND:
			
			break;
		case LOW_SWITCH:
			lifterHeight = SetpointHeights.GROUND;
			break;
		case SCALE_LOW:
			lifterHeight = SetpointHeights.LOW_SWITCH;
			break;
		case SCALE_MID:
			lifterHeight = SetpointHeights.SCALE_LOW;
			break;
		case SCALE_HIGH:
			lifterHeight = SetpointHeights.SCALE_MID;
			break;
		case MANUAL:
			if (getHeight() > SetpointHeights.SCALE_MID.height) {
				lifterHeight = SetpointHeights.SCALE_MID;
			}else if (getHeight() > SetpointHeights.SCALE_LOW.height) {
				lifterHeight = SetpointHeights.SCALE_LOW;
			}else if (getHeight() > SetpointHeights.LOW_SWITCH.height) {
				lifterHeight = SetpointHeights.LOW_SWITCH;
			}else{
				lifterHeight = SetpointHeights.GROUND;
			}
			break;
		}
		setHeight(lifterHeight.height);
	}
	
}
