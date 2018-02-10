package org.usfirst.frc.team871.util.joystick;

/**
 * Maps all the physical buttons on the XBox 360 controller to thier button
 * numbers in software.
 * 
 * @author Dave
 *
 */
public enum SaitekButtons {
    /**
     * The I button.
     */
    I(31),
    /**
     * The E button.
     */
    E(8),
    /**
     * The D button.
     */
    D(7),
    /**
     * The Mouse button.
     */
    MOUSE(16),
    /**
     * The function button.
     */
    FUNCTION(32),
    /**
     * The hat down button.
     */
    HAT_DOWN(26),
    /**
     * The hat up button.
     */
    HAT_UP(24),
    /**
     * The hat left button.
     */
    HAT_LEFT(27),
    /**
     * The hat right button.
     */
    HAT_RIGHT(25);
    
    /**
	 * the value
	 */
    private int value;

    SaitekButtons(int num) {
        value = num;
    }

    /**
     * Returns the mapping to the button number in software.
     * 
     * @return int containing the button number
     */
    int getValue() {
        return value;
    }
}
