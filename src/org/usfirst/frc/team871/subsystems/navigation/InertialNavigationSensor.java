package org.usfirst.frc.team871.subsystems.navigation;

import org.opencv.video.KalmanFilter;

import com.kauailabs.navx.frc.AHRS;

public class InertialNavigationSensor extends Thread implements IDisplacementSensor{

	private AHRS navX;
    private Waypoint currentPos;
    private KalmanFilter filter;
        
    Coordinate lastVeloComponets;
    Coordinate displacementComponets;
	
    private final int updateRate_hz;
    
    public InertialNavigationSensor(AHRS navX){
    	this.navX = navX;

        filter = new KalmanFilter();
        updateRate_hz = 60;

        resetDisplace();
    }
	
	 @Override
	 public void run(){
	        update();
	   }
	 
	 private void update(){
	        //get integral of acceleration to get update
	            //navX.getDisplacementX(); //dont use becuase It appears to not utilize a filter.

	       double filteredXAccel = (navX.getRawAccelX());// in terms of g
	       double filteredYAccel = (navX.getRawAccelY());// in terms of g

	       updateDisplace_g(filteredXAccel,filteredYAccel, updateRate_hz);

	   }
	 
	 /**
	  * 
	  * @param xAccel_m_s2 X component of acceleration in m/s^2
	  * @param yAccel_m_s2 Y component of acceleration in m/s^2
	  * @param updateRate_hz Poling rate in hertz. 
	  */
	 private void updateDisplace_m_s2(double xAccel_m_s2, double yAccel_m_s2, int updateRate_hz){
	        double sampleTime = 1.0 / updateRate_hz;

	        double currentVeloX = (this.lastVeloComponets.getX() + xAccel_m_s2 * sampleTime);
	        double currentVeloY = (this.lastVeloComponets.getY() + yAccel_m_s2 * sampleTime);
	        Coordinate curVeloComponents = new Coordinate(currentVeloX, currentVeloY);

	        double dispX = (this.displacementComponets.getX() + (this.lastVeloComponets.getX()*sampleTime) + ( 0.5 * xAccel_m_s2 * sampleTime*sampleTime));
	        double dispY = (this.displacementComponets.getY() + (this.lastVeloComponets.getY()*sampleTime) + ( 0.5 * xAccel_m_s2 * sampleTime*sampleTime));
	        
	        this.displacementComponets.setX(dispX);
	        this.displacementComponets.setY(dispY);
	        this.lastVeloComponets = curVeloComponents;
	   }
	 
	 /**
	  * 
	  * @param xAccel_m_s2 X component of acceleration in g's
	  * @param yAccel_m_s2 Y component of acceleration in g's
	  * @param updateRate_hz Poling rate in hertz. 
	  */
	 private void updateDisplace_g(double xAccel_g, double yAccel_g, int updateRate_hz){
	        double accelGrav =  9.80665;

	        //Converts to m/s^2
	        double xAccel_m_s2 =  (xAccel_g * accelGrav);
	        double yAccel_m_s2 =  (yAccel_g * accelGrav);
	        updateDisplace_m_s2(xAccel_m_s2, yAccel_m_s2, updateRate_hz);
	    }

	 public void resetDisplace(){
	        lastVeloComponets     = new Coordinate(0.0, 0.0);
	        displacementComponets = new Coordinate(0.0, 0.0);
	   }

	 @Override
	 public Coordinate getVelocity(){
	        return lastVeloComponets;
	    }

	   public Waypoint getCurrentPos(){

	        return currentPos;
	   }

	@Override
	public Coordinate getDisplacement_m() {
		return displacementComponets;
	}

	@Override
	public Coordinate getDisplacement_in() {
		// TODO Auto-generated method stub
		double mToIn = 39.37008;
		double xIn = displacementComponets.getX() * mToIn;//x comp to inches
		double yIn = displacementComponets.getY() * mToIn;//y comp to inches
		return new Coordinate (xIn, yIn);
	}  
}
