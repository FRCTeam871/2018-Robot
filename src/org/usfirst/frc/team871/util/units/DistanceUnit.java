package org.usfirst.frc.team871.util.units;

public enum DistanceUnit {

    METER(1.0),
    CENTIMETER(0.01),
    MILLIMETER(0.001),
    FOOT(0.3048),
    INCH(0.0254);


    /**
     * Its conversion value to meters
     */
    private double toMeters;

    DistanceUnit(double toMeters) {
        this.toMeters = toMeters;
    }

    double getToMeters() {
        return this.toMeters;
    }

    public double convertTo(DistanceUnit outUnit, double value) {
        return (this.toMeters * value) / outUnit.toMeters;
    }
}
