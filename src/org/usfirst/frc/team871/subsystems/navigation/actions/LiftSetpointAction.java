package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.subsystems.lifter.SuperLift;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift.SetpointHeights;

public class LiftSetpointAction implements IAction {
	private final SuperLift lift;
	private final SetpointHeights setpoint;
	private boolean initMode = false;

	public LiftSetpointAction(SuperLift lift, SetpointHeights setpoint) {
		this(lift, setpoint, false);
	}
	
	public LiftSetpointAction(SuperLift lift, SetpointHeights setpoint, boolean initMode) {
		this.initMode = initMode;
		this.lift = lift;
		this.setpoint = setpoint;
	}
	
	@Override
	public void init() {
		if(initMode) lift.setHeight(setpoint);
	}

	@Override
	public void execute() {
		if(!initMode) lift.setHeight(setpoint);
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
