package org.usfirst.frc.team871.subsystems.navigation.actions;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team871.robot.Robot;

public class TootTootAction implements IAction {
    private final DoubleSolenoid tooter;
    private long startTime;
    private int tootCount = 0;
    private boolean tooting = false;

    public TootTootAction(DoubleSolenoid tooter) {
        this.tooter = tooter;
    }

    @Override
    public void init(Robot robot, Timer timer) {
        startTime = 0;
    }

    @Override
    public void execute() {
        if(System.currentTimeMillis() - startTime > 500) {
            startTime = System.currentTimeMillis();
            tooter.set(tooting ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kOff);
            tooting = !tooting;

            tootCount++;
        }
    }

    @Override
    public boolean isComplete() {
        return tootCount < 3;
    }

    @Override
    public void abort() {
        tooter.set(DoubleSolenoid.Value.kOff);
    }

    @Override
    public void halt() {
        tooter.set(DoubleSolenoid.Value.kOff);
    }
}
