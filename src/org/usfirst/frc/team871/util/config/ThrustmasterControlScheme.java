package org.usfirst.frc.team871.util.config;

import org.usfirst.frc.team871.util.joystick.ButtonTypes;
import org.usfirst.frc.team871.util.joystick.EnhancedXBoxController;
import org.usfirst.frc.team871.util.joystick.POVDirections;
import org.usfirst.frc.team871.util.joystick.SaitekAxes;
import org.usfirst.frc.team871.util.joystick.SaitekButtons;
import org.usfirst.frc.team871.util.joystick.SaitekX52;
import org.usfirst.frc.team871.util.joystick.XBoxAxes;
import org.usfirst.frc.team871.util.joystick.XBoxButtons;

public enum ThrustmasterControlScheme implements IControlScheme{
	DEFAULT;
	
	private EnhancedXBoxController xbox;
	private SaitekX52 saitek;
	
	ThrustmasterControlScheme() {
		System.out.println("init thrust");
		xbox = new EnhancedXBoxController(0);
		saitek = new SaitekX52(1);
		xbox.setButtonMode(XBoxButtons.START, ButtonTypes.TOGGLE);
		xbox.setButtonMode(XBoxButtons.BACK, ButtonTypes.RISING);
		xbox.setButtonMode(XBoxButtons.Y, ButtonTypes.MOMENTARY);
		xbox.setAxisDeadband(XBoxAxes.LEFTX, 0.2);
		xbox.setAxisDeadband(XBoxAxes.LEFTY, 0.2);
		xbox.setAxisDeadband(XBoxAxes.RIGHTX, 0.2);
		xbox.setAxisDeadband(XBoxAxes.RIGHTY, 0.2);
		saitek.setButtonMode(SaitekButtons.MOUSE, ButtonTypes.RISING);
		saitek.setButtonMode(SaitekButtons.FUNCTION, ButtonTypes.RISING);
		saitek.setButtonMode(SaitekButtons.D, ButtonTypes.RISING);
		saitek.setButtonMode(SaitekButtons.I, ButtonTypes.RISING);
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
		return saitek.getValue(SaitekButtons.MOUSE);
	}

	@Override
	public boolean getCubeEjectButton() {
		return saitek.getValue(SaitekButtons.FUNCTION);
	}

	@Override
	public boolean getDecreaseSetpointButton() {
		return saitek.getValue(SaitekButtons.I);
	}

	@Override
	public boolean getIncreaseSetpointButton() {
		return saitek.getValue(SaitekButtons.D);
	}

	@Override
	public double getLiftAxis() {
		return -saitek.getValue(SaitekAxes.THROTTLE);
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
		return saitek.getEnhancedPOV();
	}
	
	@Override
	public boolean getManualLiftModeButton() {
//		debugPrint();
		return saitek.getValue(SaitekAxes.SLIDER) > 0;
	}
	
	public void debugPrint() {
		System.out.println("===================");
		for(int but = 0; but < 32; but++) {
			System.out.print(saitek.getRawButton(but) + " ");
		}
		System.out.println();
//		for(int ax = 0; ax < 10; ax++) {
//			System.out.print(saitek.getRawAxis(ax) + " ");
//		}
	}

	@Override
	public boolean toottoot() {
		return xbox.getValue(XBoxButtons.Y);
	}
	
	@Override
	public double getLowerLiftTrim() {
		return saitek.getValue(SaitekAxes.I_AXIS);
	}
	
	@Override
	public double getUpperLiftTrim() {
		return saitek.getValue(SaitekAxes.E_AXIS);
	}
		
}
