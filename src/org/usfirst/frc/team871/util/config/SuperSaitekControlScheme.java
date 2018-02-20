package org.usfirst.frc.team871.util.config;

import org.usfirst.frc.team871.util.joystick.ButtonTypes;
import org.usfirst.frc.team871.util.joystick.EnhancedXBoxController;
import org.usfirst.frc.team871.util.joystick.POVDirections;
import org.usfirst.frc.team871.util.joystick.SaitekAxes;
import org.usfirst.frc.team871.util.joystick.SaitekButtons;
import org.usfirst.frc.team871.util.joystick.SaitekX52;
import org.usfirst.frc.team871.util.joystick.XBoxAxes;
import org.usfirst.frc.team871.util.joystick.XBoxButtons;

public enum SuperSaitekControlScheme implements IControlScheme{
	DEFAULT;
	
	private SaitekX52 saitek;
	
	SuperSaitekControlScheme() {
		System.out.println("init super thrust");
		saitek = new SaitekX52(1);
		saitek.setButtonMode(SaitekButtons.FUNCTION, ButtonTypes.RISING);
		saitek.setButtonMode(SaitekButtons.E, ButtonTypes.RISING);
		saitek.setButtonMode(SaitekButtons.D, ButtonTypes.RISING);
		saitek.setButtonMode(SaitekButtons.HAT_RIGHT, ButtonTypes.RISING);
		saitek.setButtonMode(SaitekButtons.A, ButtonTypes.TOGGLE);
		saitek.setButtonMode(SaitekButtons.C, ButtonTypes.RISING);
		saitek.setButtonMode(SaitekButtons.FIRE, ButtonTypes.MOMENTARY);
		saitek.setButtonMode(SaitekButtons.TRIGGER_1, ButtonTypes.RISING);
	}
	@Override
	public boolean getToggleOrientationButton() {
		return saitek.getValue(SaitekButtons.A);
	}

	@Override
	public boolean getResetGyroButton() {
		return saitek.getValue(SaitekButtons.C);
	}

	@Override
	public boolean getToggleGrabberButton() {
		return saitek.getValue(SaitekButtons.TRIGGER_1);
	}

	@Override
	public boolean getCubeEjectButton() {
		return saitek.getValue(SaitekButtons.FUNCTION);
	}

	@Override
	public boolean getDecreaseSetpointButton() {
		return saitek.getValue(SaitekButtons.D);
	}

	@Override
	public boolean getIncreaseSetpointButton() {
		return saitek.getValue(SaitekButtons.E);
	}

	@Override
	public double getLiftAxis() {
		return -saitek.getValue(SaitekAxes.THROTTLE);
	}

	@Override
	public double getXAxis() {
		return saitek.getValue(SaitekAxes.X_AXIS);
	}

	@Override
	public double getYAxis() {
		return saitek.getValue(SaitekAxes.Y_AXIS);
	}

	@Override
	public double getRotationAxis() {
		return saitek.getValue(SaitekAxes.ROTATION);
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
		return saitek.getValue(SaitekButtons.FIRE);
	}
	
	@Override
	public double getLowerLiftTrim() {
		return saitek.getValue(SaitekAxes.I_AXIS);
	}
	
	@Override
	public double getUpperLiftTrim() {
		return saitek.getValue(SaitekAxes.E_AXIS);
	}
	
	@Override
	public boolean getKickButton() {
		return saitek.getValue(SaitekButtons.HAT_RIGHT);
	}
		
}
