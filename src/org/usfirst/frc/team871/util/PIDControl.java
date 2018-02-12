package org.usfirst.frc.team871.util;
/**
 * @deprecated This class is deprecated because FRC offers a superior PID class
 * @author Team871
 *
 */
public class PIDControl {

    private double setpoint;
    private double prevError;
    private double integral;
    private double kp;
    private double ki;
    private double kd;
    /**
     * @deprecated This method belongs to a deprecated class
     */
    public PIDControl(double Kp, double Ki, double Kd, double setpoint) {
        this.kp = Kp;
        this.ki = Ki;
        this.kd = Kd;

        this.setpoint = setpoint;

        prevError = 0;
        integral = 0;
    }
	/**
	 * @deprecated This method belongs to a deprecated class
	 */
    public void setSetpoing(double setpoint) {
        this.setpoint = setpoint;
    }
	/**
	 * @deprecated This method belongs to a deprecated class
	 */
    public void setKp(double kp) {
        this.kp = kp;
    }
	/**
	 * @deprecated This method belongs to a deprecated class
	 */
    public void setKi(double ki) {
        this.ki = ki;
    }
	/**
	 * @deprecated This method belongs to a deprecated class
	 */
    public void setKd(double kd) {
        this.kd = kd;
    }
    /**
     * @deprecated This method belongs to a deprecated class
     */
    private double CentralPID(double reading) {
        double error = reading - setpoint;
        integral += error;

        double output = (kp * error) + (ki * integral) + (kd * (error - prevError));

        prevError = error;

        return output;
    }
    /**
     * @deprecated This method belongs to a deprecated class
     */
    public double getPID(double error) {
        return this.CentralPID(error);
    }
    /**
     * @deprecated This method belongs to a deprecated class
     */
    public double getMotorPID(double error) {
        double output = this.CentralPID(error);

        if (output > 0.99) {
            output = 1;
        }
        if (output < -0.99) {
            output = -1;
        }

        return output;
    }
}
