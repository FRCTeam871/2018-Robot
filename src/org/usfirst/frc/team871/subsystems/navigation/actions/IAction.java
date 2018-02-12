package org.usfirst.frc.team871.subsystems.navigation.actions;

import org.usfirst.frc.team871.robot.Robot;
import edu.wpi.first.wpilibj.Timer;

/**
 * Is an interface for performing actions within auton at specified waypoints
 *  @author Team871-TPfaffe
 */
public interface IAction {

    /**
     * @param robot object so that the Action can read states
     * @param timer syncronizes the Actions time with the handler's time
     */
    void init(Robot robot, Timer timer);

    /**
     * continue performing the Action
     */
    void execute();

    /**
     * @return if the Action is complete with its task
     */
    boolean isComplete();

    /**
     * Force stop all action (Nuclear Option)
     */
    void abort();

    /**
     * Ask the Action to finish up ASAP
     */
    void halt();

}
