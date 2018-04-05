package org.usfirst.frc.team871.util.joystick;

/**
 * Maps all the axes on the XBox 360 controller to their axis numbers in
 * software.
 * 
 * @author Dave
 *
 */
public enum SaitekAxes {
   
	X_AXIS(0),
	Y_AXIS(1),
	THROTTLE (2),
	I_AXIS (3),
	E_AXIS (4),
	ROTATION(5),
	SLIDER(6);

    private int value;

    SaitekAxes(int num) {
        value = num;
    }

    /**
     * Returns the mapping to the axis number in software.
     * 
     * @return Int containing the axis number
     */
    int getValue() {
        return value;
    }
}
