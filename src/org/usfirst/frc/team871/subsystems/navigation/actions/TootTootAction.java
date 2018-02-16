package org.usfirst.frc.team871.subsystems.navigation.actions;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team871.robot.Robot;

public class TootTootAction implements IAction {
    private final DoubleSolenoid tooter;
    private long startTime = 0;
    private int tootCount = 0;
    private boolean tooting = false;

    public TootTootAction(DoubleSolenoid tooter) {
        this.tooter = tooter;
    }

    @Override
    public void init(Robot robot, Timer timer) {
    	
    }

    @Override
    public void execute() {
        if(System.currentTimeMillis() - startTime > 200) {
            startTime = System.currentTimeMillis();
            tooting = !tooting;
            tooter.set(tooting ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
            System.out.println(tooting + " " + startTime);

            tootCount++;
        }
    }

    @Override
    public boolean isComplete() {
    	if(tootCount > 3) {
    		tooter.set(DoubleSolenoid.Value.kReverse);
    	}
    	
        return tootCount > 3;
    }

    @Override
    public void abort() {
        tooter.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void halt() {
        tooter.set(DoubleSolenoid.Value.kReverse);
    }
}
