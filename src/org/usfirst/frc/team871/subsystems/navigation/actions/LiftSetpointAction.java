package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.subsystems.lifter.SuperLift;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift.SetpointHeights;

public class LiftSetpointAction implements IAction {
	private final SuperLift lift;
	private final SetpointHeights setpoint;

	public LiftSetpointAction(SuperLift lift, SetpointHeights setpoint) {
		this.lift = lift;
		this.setpoint = setpoint;
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void execute() {
		lift.setHeight(setpoint);
	}

	@Override
	public boolean isComplete() {
		return lift.isAtSetpoint();
	}

	@Override
	public void abort() {

	}

	@Override
	public void halt() {

	}

	@Override
	public String toString() {
		return "{ LiftSetpointAction to "+setpoint.toString()+" }";
	}
}
