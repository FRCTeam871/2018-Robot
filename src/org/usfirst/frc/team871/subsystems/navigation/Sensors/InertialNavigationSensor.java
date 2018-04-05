package org.usfirst.frc.team871.subsystems.navigation.Sensors;

import com.kauailabs.navx.frc.AHRS;
import org.opencv.video.KalmanFilter;
import org.usfirst.frc.team871.subsystems.navigation.Coordinate;
import org.usfirst.frc.team871.subsystems.navigation.ICredible;
import org.usfirst.frc.team871.subsystems.navigation.Waypoint;
import org.usfirst.frc.team871.util.units.DistanceUnit;

/**
 * Measures displacement of robot using accelerometer.
 *  @author Team871-TPfaffe
 */
public class InertialNavigationSensor extends Thread implements IDisplacementSensor, ICredible {

    Coordinate lastVeloComponents;
    Coordinate displacementComponents;

    private AHRS navX;
    private Waypoint currentPos;
    private KalmanFilter filter;

    private double counts; //count of navX poll that you get from the Navx itself.
    private double countOffset;
    private double credibility;

    private final double CREDIBILITY_COEFFICIENT; //Coefficient
    private final int UPDATE_RATE_HZ; //frequency
    private final long LOOP_TIME; //period

    public InertialNavigationSensor(AHRS navX) {
        this.navX = navX;

        UPDATE_RATE_HZ = 60;
        LOOP_TIME = (1 / UPDATE_RATE_HZ) * 1000;
        CREDIBILITY_COEFFICIENT = 0.0;//should never be above 1 unless it gets better over time, It shouldn't...

        countOffset = 0;

        resetSensor();
        filter = new KalmanFilter();
    }

    @Override
    public void run() {
        Long startTime = System.currentTimeMillis();

        update();

        Long closeTime = System.currentTimeMillis();
        Long diff = LOOP_TIME - (closeTime - startTime);
        try {
            Thread.sleep(diff);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        // get integral of acceleration to get update
        // navX.getDisplacementX(); //dont use becuase It appears to not utilize a
        // filter.

        counts = navX.getUpdateCount() - countOffset;
        credibility = Math.max(credibility - (CREDIBILITY_COEFFICIENT * counts), 0); //keeps it above 0

        double filteredXAccel = (navX.getRawAccelX());// in terms of g
        double filteredYAccel = (navX.getRawAccelY());// in terms of g

        updateDisplace_g(filteredXAccel, filteredYAccel, UPDATE_RATE_HZ);

    }

    /**
     * @param xAccel_m_s2   X component of acceleration in m/s^2
     * @param yAccel_m_s2   Y component of acceleration in m/s^2
     * @param updateRate_hz Poling rate in hertz.
     */
    private void updateDisplace_m_s2(double xAccel_m_s2, double yAccel_m_s2, int updateRate_hz) {
        double sampleTime = 1.0 / updateRate_hz;

        double currentVeloX = (this.lastVeloComponents.getX() + xAccel_m_s2 * sampleTime);
        double currentVeloY = (this.lastVeloComponents.getY() + yAccel_m_s2 * sampleTime);
        Coordinate curVeloComponents = new Coordinate(currentVeloX, currentVeloY);

        double dispX = (this.displacementComponents.getX() + (this.lastVeloComponents.getX() * sampleTime) + (0.5 * xAccel_m_s2 * sampleTime * sampleTime));
        double dispY = (this.displacementComponents.getY() + (this.lastVeloComponents.getY() * sampleTime) + (0.5 * xAccel_m_s2 * sampleTime * sampleTime));

        this.displacementComponents.setX(dispX);
        this.displacementComponents.setY(dispY);
        this.lastVeloComponents = curVeloComponents;
    }

    /**
     * @param xAccel_g      X component of acceleration in g's
     * @param yAccel_g      Y component of acceleration in g's
     * @param updateRate_hz Poling rate in hertz.
     */
    private void updateDisplace_g(double xAccel_g, double yAccel_g, int updateRate_hz) {
        double accelGrav = 9.80665;

        // Converts to m/s^2
        double xAccel_m_s2 = (xAccel_g * accelGrav);
        double yAccel_m_s2 = (yAccel_g * accelGrav);
        updateDisplace_m_s2(xAccel_m_s2, yAccel_m_s2, updateRate_hz);
    }

    @Override
    public void resetSensor() {
        lastVeloComponents = new Coordinate(0.0, 0.0);
        displacementComponents = new Coordinate(0.0, 0.0);

        countOffset += counts;
        credibility = 0;
    }

    @Override
    public Coordinate getVelocity(DistanceUnit unit) {
        //starts as meters/second
        double x = DistanceUnit.METER.convertTo(unit, lastVeloComponents.getX());// x comp to unit
        double y = DistanceUnit.METER.convertTo(unit, lastVeloComponents.getY());// y comp to unit
        return new Coordinate(x, y);
    }

    @Override
    public Coordinate getDisplacement(DistanceUnit unit) {
        //starts as a meter
        double x = DistanceUnit.METER.convertTo(unit, displacementComponents.getX());// x comp to unit
        double y = DistanceUnit.METER.convertTo(unit, displacementComponents.getY());// y comp to unit
        return new Coordinate(x, y);
    }

    @Override
    public double getCrediblity() {

        return credibility;
    }
}
