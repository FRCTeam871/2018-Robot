package org.usfirst.frc.team871.subsystems.navigation;

/**
 *  @author Team871-TPfaffe
 * Its a coordinate with an x and y component
 */
public class Coordinate {
    private double xVal;
    private double yVal;

    public Coordinate(double xVal, double yVal) {
        this.xVal = xVal;
        this.yVal = yVal;
    }

    public double getX() {
        return this.xVal;
    }

    public void setX(double xVal) {
        this.xVal = xVal;
    }

    public double getY() {
        return this.yVal;
    }

    public void setY(double yVal) {
        this.yVal = yVal;
    }

    @Override
    public String toString() {
        return '(' + xVal + " , " + yVal + ')';
    }
}
