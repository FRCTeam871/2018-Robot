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
	
	InitialControlScheme() {
		System.out.println("init inital");
		xbox = new EnhancedXBoxController(0);
		xbox2 = new EnhancedXBoxController(1);
		xbox.setButtonMode(XBoxButtons.START, ButtonTypes.TOGGLE);
		xbox.setButtonMode(XBoxButtons.BACK, ButtonTypes.RISING);
		xbox.setAxisDeadband(XBoxAxes.LEFTX, 0.2);
		xbox.setAxisDeadband(XBoxAxes.LEFTY, 0.2);
		xbox.setAxisDeadband(XBoxAxes.RIGHTX, 0.2);
		xbox.setAxisDeadband(XBoxAxes.RIGHTY, 0.2);
		xbox2.setButtonMode(XBoxButtons.A, ButtonTypes.RISING);
		xbox2.setButtonMode(XBoxButtons.B, ButtonTypes.RISING);
		xbox2.setButtonMode(XBoxButtons.LBUMPER, ButtonTypes.RISING);
		xbox2.setButtonMode(XBoxButtons.RBUMPER, ButtonTypes.RISING);
		xbox2.setAxisDeadband(XBoxAxes.LEFTX, 0.2);
		xbox2.setAxisDeadband(XBoxAxes.LEFTY, 0.2);
		xbox2.setAxisDeadband(XBoxAxes.RIGHTX, 0.2);
		xbox2.setAxisDeadband(XBoxAxes.RIGHTY, 0.2);
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
	
	@Override
	public boolean getManualLiftModeButton() {
		return false;
	}
	
	@Override
	public boolean toottoot() {
		return false;
	}

	@Override
	public double getLowerLiftTrim() {
		// TODO
		return 0;
	}
	
	@Override
	public double getUpperLiftTrim() {
		// TODO
		return 0;
	}
	
	@Override
	public boolean getKickButton() {
		// TODO
		return false;
	}
	
	@Override
	public boolean test() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getLifterResetButton() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
