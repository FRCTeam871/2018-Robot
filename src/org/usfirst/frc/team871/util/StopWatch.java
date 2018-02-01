package org.usfirst.frc.team871.util;

/**
 * Is used to determine when the time is up
 * @author Team871
 *
 */
public class StopWatch {
    
    private long appriseTime;
    /**
     * sets the time at which the timer will be up
     * @param waitTime The amount of time the timer waits until being up 
     */
    public StopWatch(long waitTime) {
        appriseTime = System.currentTimeMillis() + waitTime;
    }

    /**
     * Determines if the wait time is up
     * @return Returns true if the appriseTime has passed
     */
    public boolean timeUp() {
        return (appriseTime <= System.currentTimeMillis());
    }

}
