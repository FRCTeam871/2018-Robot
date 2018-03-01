package org.usfirst.frc.team871.subsystems.navigation;

import org.usfirst.frc.team871.subsystems.navigation.actions.IAction;
import org.usfirst.frc.team871.subsystems.navigation.actions.NullAction;

import java.text.DecimalFormat;

/**
 *  @author Team871-TPfaffe
 * Contains parameters for navigation to a single point.
 * Can include an Action to perform at said single point.
 */
public class Waypoint extends Coordinate {
    private static final DecimalFormat angleFormatter = new DecimalFormat("000.00");
    private static final DecimalFormat speedFormatter = new DecimalFormat("0.000");

    private final double speed;
    private final double angle;
    private final IAction action;

    public Waypoint(double xVal, double yVal, double angle, double speed, IAction action) {
        super(xVal, yVal);
        this.speed = speed;
        this.angle = angle;
        this.action = action;

    }

    public Waypoint(double xVal, double yVal, double angle, double speed) {
        this(xVal, yVal, angle, speed, new NullAction());
    }
    
    public Waypoint(Waypoint copy, double speed, IAction action) {
    	this(copy.getX(), copy.getY(), copy.getAngle(), speed, action == null ? copy.getAction() : action);
    }

    public Waypoint(double xVal, double yVal, double angle) {
        this(xVal, yVal, angle, 1.0, new NullAction());
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getAngle() {
        return this.angle;
    }

    public IAction getAction() {
        return this.action;
    }

    @Override
    public String toString() {
        return "{ Waypoint " + super.toString() + " / " + angleFormatter.format(angle) + " @ " + speedFormatter.format(speed)
                + " Doing " + action.toString() + " }";
    }
}