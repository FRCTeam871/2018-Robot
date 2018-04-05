package org.usfirst.frc.team871.subsystems.navigation;

public class ManualFieldSetup implements FieldSetup {

	String fieldConfig;
	boolean scaleLOfflimits;
	boolean scaleROfflimits;
	boolean switchLOfflimits;
	boolean switchROfflimits;
	boolean switchRInboard;
	boolean switchLInboard;
	int startPos;
	
	public ManualFieldSetup(String fieldConfig, boolean scaleLOfflimits, boolean scaleROfflimits,
			boolean switchLOfflimits, boolean switchROfflimits, boolean switchRInboard, boolean switchLInboard,
			int startPos) {
		this.fieldConfig = fieldConfig;
		this.scaleLOfflimits = scaleLOfflimits;
		this.scaleROfflimits = scaleROfflimits;
		this.switchLOfflimits = switchLOfflimits;
		this.switchROfflimits = switchROfflimits;
		this.switchRInboard = switchRInboard;
		this.switchLInboard = switchLInboard;
		this.startPos = startPos;
	}
	
	
	@Override
	public String getFieldConfiguration() {
		return fieldConfig;
	}

	@Override
	public boolean getScaleLOffLimits() {
		return scaleLOfflimits;
	}

	@Override
	public boolean getScaleROffLimits() {
		return scaleROfflimits;
	}

	@Override
	public boolean getSwitchLOffLimits() {
		return switchLOfflimits;
	}

	@Override
	public boolean getSwitchROffLimits() {
		return switchROfflimits;
	}

	@Override
	public boolean getSwitchLInboard() {
		return switchLInboard;
	}

	@Override
	public boolean getSwitchRInboard() {
		return switchRInboard;
	}

	@Override
	public int getStartPosition() {
		return startPos;
	}

}
