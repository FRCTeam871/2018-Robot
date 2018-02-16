package org.usfirst.frc.team871.subsystems.navigation.actions;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team871.robot.Robot;

public class PlaceOnScaleAction implements IAction{
    private boolean isDone;
    private Robot robot;
    private boolean isAtSetpoint = false;

    private Timer timer;
    private Timer haltTimer;

    private boolean firstHalt;

    private final double SCALE_HEIGHT;
    private final double SETPOINT;

    public PlaceOnScaleAction() {
        SCALE_HEIGHT = 18.75;//TODO: get scale height
        SETPOINT = SCALE_HEIGHT + 3;// 3 inch error;

        this.timer = new Timer();
        firstHalt = true;
    }

    @Override
    public void init(Robot robot, Timer timer) {
        this.isDone = false;
        this.robot = robot;
        this.timer = timer;//syncs timer with Action handler's

        robot.getDrive().driveFieldOriented(0, 0, 0);//stop driving
        robot.getGrabber().setGrab(true);//make sure cube is grabbed
        robot.getLift().setHeight(SCALE_HEIGHT);
    }

    @Override
    public boolean isComplete() { //are we done yet
        return isDone;
    }

    @Override
    public void abort() { //hard stop
        if(this.isDone){
            return;
        }
        robot.getGrabber().ejectCube();
        robot.getLift().setBottom();
        isDone = true;

    }

    @Override
    public void halt() { //soft stop
        if(firstHalt){
            firstHalt = !firstHalt;
            haltTimer.start();
        }


        //wait a certain amount of time to see if we can complete then stop if we dont
        if(haltTimer.hasPeriodPassed(2)){
            abort();
        }
        else{
            execute();
        }

    }

    @Override
    public void execute() {

        robot.getDrive().driveFieldOriented(0, 0, 0);//keep updating stop drive
        if (robot.getLift().isAtSetpoint()) {
            isAtSetpoint = true;
        }

        if (isAtSetpoint) {

            robot.getGrabber().ejectCube();//release cube
            robot.getLift().setBottom();
            isDone = true;
        }

    }

}