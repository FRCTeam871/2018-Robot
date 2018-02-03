package org.usfirst.frc.team871.util.sensor;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class EnhancedGyro extends AHRS{

	
	public EnhancedGyro(Port i2c_port_id) {
		super(i2c_port_id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getYaw() {
		return super.getYaw() % 360;
	}
	  
	@Override
	public double pidGet() {
		float returnVal  = getYaw();		
		
		return returnVal;
		}
	
}
