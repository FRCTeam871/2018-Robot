package org.usfirst.frc.team871.subsystems.navigation.actions;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team871.robot.Robot;

/**
 * Is a dummy action that donts
 *  @author Team871-TPfaffe
 */
public class NullAction implements IAction {


    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public void abort() {
        dont();
    }

    @Override
    public void execute() {
        dont();
    }

    @Override
    public void init(Robot robot, Timer timer) {
        dont();

    }

    @Override
    public void halt() {
        dont();
    }

    /**
     * It don'ts
     */
    public void dont() {
        return;
    }

}
