package org.usfirst.frc.team871.subsystems.navigation.actions;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team871.robot.Robot;

public class ActionHandler{

    private IAction action;
    private Robot robot;
    private final long MAX_TIME;
    Timer timer;
    private boolean firstRun;

    /**
     *
     * @param robot object for control of robot parts such as lift or drive
     * @param maxTime
     */
    public ActionHandler(Robot robot, long maxTime){
        this.robot = robot;
        this.MAX_TIME = maxTime;
        this.action   = new NullAction(); //starts it with a dummy action

        this.firstRun = true;
    }

    public void loadNewAction(IAction action){
        this.action = action;

        firstRun = true;
    }

    /**
     * runs the currently loaded action
     */
    public void runAction() {
        if(firstRun){//only do this once on first call after new load
            firstRun = false;
            timer.reset();
            action.init(this.robot, this.timer);
        }

        if (!timer.hasPeriodPassed(MAX_TIME)) {//if max time hasnt passed continue executing
            if (action.isComplete()) {//if action says is complete with its action.
               //done with action
                action.halt();//stop
            }
            else {
                action.execute(); //do action
            }
        }

        if (!action.isComplete()) { //if the action hasnt already been completed start halt process
                action.halt();//will attemp to stop. If cant will abort everything.
            }
    }

    /**
     * immediately stops action
     */
    public void stopAction(){
        action.abort();
    }

}
