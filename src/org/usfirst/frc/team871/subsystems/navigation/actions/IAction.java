package org.usfirst.frc.team871.subsystems.navigation.actions;

/**
 * Is an interface for performing actions within auton at specified waypoints
 *  @author Team871-TPfaffe
 */
public interface IAction {

    /**
     * does this method have a purpose now. That's a point up for contention.
     */
    void init();

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
