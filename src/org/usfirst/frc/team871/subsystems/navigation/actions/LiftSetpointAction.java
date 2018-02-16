package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.robot.Robot;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift.SetpointHeights;

import edu.wpi.first.wpilibj.Timer;

public class LiftSetpointAction implements IAction {

	protected SuperLift lift;
	protected SetpointHeights setpoint;
	
	public LiftSetpointAction(SuperLift lift, SetpointHeights setpoint) {
		this.lift = lift;
		this.setpoint = setpoint;
	}
	
	@Override
	public void init(Robot robot, Timer timer) {
		
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

}
