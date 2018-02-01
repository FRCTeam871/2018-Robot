package org.usfirst.frc.team871.util.config;

import org.usfirst.frc.team871.util.joystick.ButtonTypes;
import org.usfirst.frc.team871.util.joystick.EnhancedXBoxController;
import org.usfirst.frc.team871.util.joystick.POVDirections;
import org.usfirst.frc.team871.util.joystick.XBoxAxes;
import org.usfirst.frc.team871.util.joystick.XBoxButtons;

public enum InitialControlScheme implements IControlScheme{
	DEFAULT;
	
	private EnhancedXBoxController xbox;
	private EnhancedXBoxController xbox2;
	
	private InitialControlScheme() {
		xbox = new EnhancedXBoxController(0);
		xbox2 = new EnhancedXBoxController(1);
		xbox.setButtonMode(XBoxButtons.START, ButtonTypes.TOGGLE);
		xbox.setButtonMode(XBoxButtons.BACK, ButtonTypes.RISING);
		xbox2.setButtonMode(XBoxButtons.A, ButtonTypes.RISING);
		xbox2.setButtonMode(XBoxButtons.B, ButtonTypes.RISING);
		xbox2.setButtonMode(XBoxButtons.LBUMPER, ButtonTypes.RISING);
		xbox2.setButtonMode(XBoxButtons.RBUMPER, ButtonTypes.RISING);
	}
	@Override
	public boolean getToggleOrientationButton() {
		return xbox.getValue(XBoxButtons.START);
	}

	@Override
	public boolean getResetGyroButton() {
		return xbox.getValue(XBoxButtons.BACK);
	}

	@Override
	public boolean getToggleGrabberButton() {
		return xbox2.getValue(XBoxButtons.A);
	}

	@Override
	public boolean getCubeEjectButton() {
		return xbox2.getValue(XBoxButtons.B);
	}

	@Override
	public boolean getDecreaseSetpointButton() {
		return xbox2.getValue(XBoxButtons.LBUMPER);
	}

	@Override
	public boolean getIncreaseSetpointButton() {
		return xbox2.getValue(XBoxButtons.RBUMPER);
	}

	@Override
	public double getLiftAxis() {
		return xbox2.getValue(XBoxAxes.TRIGGER);
	}

	@Override
	public double getXAxis() {
		return xbox.getValue(XBoxAxes.LEFTX);
	}

	@Override
	public double getYAxis() {
		return xbox.getValue(XBoxAxes.LEFTY);
	}

	@Override
	public double getRotationAxis() {
		return xbox.getValue(XBoxAxes.RIGHTX);
	}
	@Override
	public POVDirections getPOV() {
		return xbox2.getEnhancedPOV();
	}


		
}
