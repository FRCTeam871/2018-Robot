package org.usfirst.frc.team871.subsystems.navigation;

/**
 *  @author Team871-TPfaffe
 * Its a coordinate with an x and y component
 */
public class Coordinate {
    private double xVal;
    private double yVal;

    public Coordinate(Coordinate other) {
        copy(other);
    }

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

    public void copy(Coordinate other) {
        xVal = other.xVal;
        yVal = other.yVal;
    }

    public void plus(Coordinate other) {
        xVal += other.xVal;
        yVal += other.yVal;
    }

    /**
     * Gets distance between two waypoints
     * @param other The waypoint to get the distance TO
     * @return a number
     */
    public double getDistance(Coordinate to) {
        final double dx = to.xVal - xVal;
        final double dy = to.yVal - yVal;

        //distance = Math.sqrt(Math.pow(waypoint1.getX(), 2) + Math.pow(waypoint1.getY(), 2)); // greatest code of the year by the pfaffanater
        //pathagaras theorem- a^2 +b^2 = c^2
        //^ Larry spelled it that way so we are going to keep it to shame him
        return Math.sqrt((dx * dx) + (dy * dy));
    }


    /**
     * Gets angle based on waypoint coordinate system which is initialized at the beginning of the match with the y axis perpendicular to the back wall
     * @param to The waypoint to get the angle to
     * @return
     */
    public double getAngle(Coordinate to) {
        //the x and y arguments are reversed because of the coordinate transformation between the gyro angle and the field coordinate system
        return Math.toDegrees(Math.atan2(to.yVal - yVal, to.xVal - xVal));
    }


    @Override
    public String toString() {
        return '(' + xVal + " , " + yVal + ')';
    }
}
