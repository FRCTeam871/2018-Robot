package org.usfirst.frc.team871.subsystems.navigation;

public interface FieldSetup {
	
	public String getFieldConfiguration();
	
	public boolean getScaleLOffLimits();
	public boolean getScaleROffLimits();
	public boolean getSwitchLOffLimits();
	public boolean getSwitchROffLimits();
	
	public boolean getSwitchLInboard();
	public boolean getSwitchRInboard();
	
	public int getStartPosition();
	
}
