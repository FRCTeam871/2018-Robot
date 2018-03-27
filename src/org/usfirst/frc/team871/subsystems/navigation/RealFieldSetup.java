package org.usfirst.frc.team871.subsystems.navigation;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DriverStation;

public class RealFieldSetup implements FieldSetup {

	NetworkTable table;
	
	public RealFieldSetup(NetworkTable table) {
		this.table = table;
	}
	
	@Override
	public String getFieldConfiguration() {
		return DriverStation.getInstance().getGameSpecificMessage();
	}

	@Override
	public boolean getScaleLOffLimits() {
		return table.getEntry("rScaleOffLimits").getBoolean(false);
	}

	@Override
	public boolean getScaleROffLimits() {
		return table.getEntry("rScaleOffLimits").getBoolean(false);
	}

	@Override
	public boolean getSwitchLOffLimits() {
		return table.getEntry("lSwitchOffLimits").getBoolean(false);
	}

	@Override
	public boolean getSwitchROffLimits() {
		return table.getEntry("rSwitchOffLimits").getBoolean(false);
	}

	@Override
	public boolean getSwitchLInboard() {
		return table.getEntry("isInboardSwitchL").getBoolean(false);
	}

	@Override
	public boolean getSwitchRInboard() {
		return table.getEntry("isInboardSwitchR").getBoolean(false);
	}
	
	@Override
	public int getStartPosition() {
		return table.getEntry("startPos").getNumber(0).intValue() + 1;
	}

}
