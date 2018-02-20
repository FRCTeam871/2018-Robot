package org.usfirst.frc.team871.subsystems.navigation;

import org.usfirst.frc.team871.subsystems.Grabber;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift.SetpointHeights;
import org.usfirst.frc.team871.subsystems.navigation.actions.LiftSetpointAction;
import org.usfirst.frc.team871.subsystems.navigation.actions.SetGrabberAction;
import org.usfirst.frc.team871.subsystems.navigation.actions.TootTootAction;
import org.usfirst.frc.team871.subsystems.navigation.actions.WaitAction;
import org.usfirst.frc.team871.util.config.IRobotConfiguration;

import java.util.HashMap;
import java.util.Map;

public enum WaypointProviderFactory {
    DEFAULT;
	
    private boolean initialized = false;
    private final Map<String, Waypoint[]> paths = new HashMap<>();

    public void init(Grabber grabber, SuperLift lift, IRobotConfiguration config) {
        Waypoint[] pts = new Waypoint[] {
                new Waypoint(0, 0, 0, 0.3, new SetGrabberAction(grabber, true)),
                new Waypoint(12 * 19, 0, 0, 0.6),
                new Waypoint(12 * 19, (12 * 10) - 6, 0, 0.4, new LiftSetpointAction(lift, SuperLift.SetpointHeights.SCALE_MID)),
                new Waypoint(12 * 19, 11 * 12, 0, 0.3, new SetGrabberAction(grabber, false)),
                new Waypoint(12 * 19, (12 * 10) - 6, 0, 0.3, new LiftSetpointAction(lift, SuperLift.SetpointHeights.GROUND)),
                new Waypoint(12 * 19, 0, 0, 0.4),
                new Waypoint(-12, 0, 0, 0.6),
                new Waypoint(0, 0, 0, 0.3, new TootTootAction(config.getTootToot()))
        };

        Waypoint lSwitchMiddle = new Waypoint(180, 55, 0, 0);
        Waypoint lSwitchMiddleInner = new Waypoint(180, 80, 0, 0);
        Waypoint rSwitchMiddle = new Waypoint(180, 270, 0, 0);
        Waypoint rSwitchMiddleInner = new Waypoint(180, 270 - 24, 0, 0);
        Waypoint rSwitchFar = new Waypoint(12 * 21, 216, 0, 0);
        Waypoint rSwitchFarInner = new Waypoint(12 * 19, 216, 0, 0);
        Waypoint lSwitchFar = new Waypoint(12 * 21, 108, 0, 0);
        Waypoint lSwitchFarInner = new Waypoint(12 * 19, 108, 0, 0);
        Waypoint lScale = new Waypoint(312, 34, 0, 0);
        Waypoint lScaleInner = new Waypoint(312, 34 + 24, 0, 0);
        Waypoint rScale = new Waypoint(328, 290, 0, 0);
        Waypoint rScaleInner = new Waypoint(328, 290 - 28, 0, 0);
        Waypoint rScaleClose = new Waypoint(298 - 24, 236, 0, 0); //230 252
        Waypoint rScaleCloseInner = new Waypoint(298, 236, 0, 0);
        Waypoint lScaleClose = new Waypoint(320 - 24, 80, 0, 0); //230 252
        Waypoint lScaleCloseInner = new Waypoint(320, 80, 0, 0);
        
        Waypoint lCenterLine = new Waypoint(12 * 21, 34, 0, 0);
        Waypoint lCenterLineCloser = new Waypoint(12 * 21, 64, 0, 0);
        Waypoint rCenterLine = new Waypoint(12 * 21, 290, 0, 0);
        
        addPath("WoodshopDrop", pts);
        
        addPath("LSwitch", new Waypoint[]{
        	new Waypoint(lSwitchMiddle, 0.7, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH, true)),
        	new Waypoint(lSwitchMiddleInner, 0.3, new SetGrabberAction(grabber, false)),
        	new Waypoint(lSwitchMiddle, -0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND))
        });
        
        addPath("LScale", new Waypoint[] {
//			new Waypoint(lSwitchMiddle, 0.7, null),
//			new Waypoint(228.735, 36, 0, 0.7),
			new Waypoint(lScale, 0.7, null),
			new Waypoint(312, 34 + 0.1, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.SCALE_HIGH, true)),
			new Waypoint(lScaleInner, 0.3, new SetGrabberAction(grabber, false)),
			new Waypoint(lScale, -0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND))
        });
        
        //TODO: do this one
        addPath("LSwitchRScale", new Waypoint[] {
			new Waypoint(168, 55, 0, 0.3), 
			new Waypoint(228.735, 36, 0, 0.3), 
			new Waypoint(228.735, 228, 0, 0.3),
			new Waypoint(335.65, 228, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.SCALE_HIGH)),
			new Waypoint(335.65, 36 + 12, 0, 0.3, new SetGrabberAction(grabber, false)),
			new Waypoint(335.65, 36, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND))
		});
        
        addPath("LScaleRSwitch", new Waypoint[]{
        	new Waypoint(lScale, 0.6, null),
        	new Waypoint(lCenterLine, 0.6, null),
        	new Waypoint(rSwitchFar, 0.6, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH)),
        	new Waypoint(rSwitchFarInner, 0.3, new SetGrabberAction(grabber, false))
//        	new Waypoint(12 * 20, 212, 0, -0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND))
        });
        
        //new ones;

        addPath("RSwitch", new Waypoint[]{ // y=262
        		new Waypoint(rSwitchMiddle, 0.7, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH, true)),
            	new Waypoint(rSwitchMiddleInner, 0.3, new SetGrabberAction(grabber, false)),
            	new Waypoint(rSwitchMiddle, -0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND))
        });

        addPath("RScale", new Waypoint[]{ //y=287
        		new Waypoint(rScale, 0.7, null),
    			new Waypoint(328, 324 - 40, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.SCALE_HIGH, true)),
    			new Waypoint(rScaleInner, 0.3, new SetGrabberAction(grabber, false)),
    			new Waypoint(rScale, -0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND))
        });

        addPath("LStartRSwitch", new Waypoint[]{
        		new Waypoint(lCenterLine, 0.6, null),
            	new Waypoint(rSwitchFar, 0.6, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH)),
            	new Waypoint(rSwitchFarInner, 0.3, new SetGrabberAction(grabber, false))
        });
        
        addPath("LStartRSwitchDirect", new Waypoint[]{
        		new Waypoint(36, 64, 0, 0.3, new WaitAction(1000)),
        		new Waypoint(36, 210, 0, 0.6),
        		new Waypoint(130, 210, 0, 0.6, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH)),
                new Waypoint(130 + 24, 210, 0, 0.3, new SetGrabberAction(grabber, false))
        });

        addPath("LStartRScale", new Waypoint[]{
        		new Waypoint(lCenterLine, 0.6, null),
//        		new Waypoint(rCenterLine, 0.6, null),
        		new Waypoint(12 * 21, 236, 0, 0.6),
            	new Waypoint(rScaleClose, 0.6, new LiftSetpointAction(lift, SetpointHeights.SCALE_HIGH)),
            	new Waypoint(rScaleCloseInner, 0.3, new SetGrabberAction(grabber, false))
        });

        addPath("RStartLSwitch", new Waypoint[]{
        		new Waypoint(rCenterLine, 0.6, null),
        		new Waypoint(lCenterLineCloser, 0.7, null),
        		new Waypoint(lSwitchMiddle, 0.7, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH, true)),
            	new Waypoint(lSwitchMiddleInner, 0.3, new SetGrabberAction(grabber, false)),
            	new Waypoint(lSwitchMiddle, -0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND))
        });
        
        addPath("RStartLSwitchDirect", new Waypoint[]{
        		new Waypoint(36, 260, 0, 0.3, new WaitAction(1000)),
        		new Waypoint(36, 107, 0, 0.6),
        		new Waypoint(130, 107, 0, 0.6, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH)),
                new Waypoint(130 + 24, 107, 0, 0.3, new SetGrabberAction(grabber, false))
        });

        addPath("RStartLScale", new Waypoint[]{
        		new Waypoint(rCenterLine, 0.6, null),
        		new Waypoint(12 * 21, 80, 0, 0.6),
            	new Waypoint(lScaleClose, 0.6, new LiftSetpointAction(lift, SetpointHeights.SCALE_HIGH)),
            	new Waypoint(lScaleCloseInner, 0.3, new SetGrabberAction(grabber, false))
        });
        
        //140 107
        //140 210

        addPath("MStartLSwitch", new Waypoint[]{
        		new Waypoint(36, 160, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH, true)),
        		new Waypoint(110, 107, 0, 0.6),
                new Waypoint(120 + 24, 107, 0, 0.3, new SetGrabberAction(grabber, false))
        });

        addPath("MStartRSwitch", new Waypoint[]{
        		new Waypoint(36, 160, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH, true)),
        		new Waypoint(110, 210, 0, 0.6),
                new Waypoint(120 + 24, 210, 0, 0.3, new SetGrabberAction(grabber, false))
        });
        
        
        // untested (not needed)
        
        addPath("MStartLScale", new Waypoint[]{
        		new Waypoint(140 - 24, 80, 0, 0.6, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH)),
        		new Waypoint(lSwitchMiddle, 0.7, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH, true)),
        		new Waypoint(lSwitchMiddleInner, 0.3, new SetGrabberAction(grabber, false)),
        		new Waypoint(lSwitchMiddle, -0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND)),
        });

        addPath("MStartRScale", new Waypoint[]{
        		new Waypoint(140 - 24, 244, 0, 0.6, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH)),
                new Waypoint(rSwitchMiddle, 0.7, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH, true)),
            	new Waypoint(rSwitchMiddleInner, 0.3, new SetGrabberAction(grabber, false)),
            	new Waypoint(rSwitchMiddle, -0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND)),
        });
        
        //test paths
        
        addPath("TestHome", new Waypoint[]{
        		new Waypoint(33/2.0 - 4, 64, 0, 0.3),
        		new Waypoint(33/2.0, 64, 0, 0.3)
        });
        
        addPath("RStart", new Waypoint[] {
        		new Waypoint(33/2.0, 260, 0, 0.3),
        		new Waypoint(33/2.0 - 10, 260, 0, 0.3)
        });
        
        addPath("TestReverse", new Waypoint[]{
        	new Waypoint(12 * 12, 0, 0, 0.3),
        	new Waypoint(0, 0, 0, -0.3)
        });
        
        addPath("TestReverse2", new Waypoint[]{
        		new Waypoint(0, 12 * 4, 0, 0.3),
        		new Waypoint(0, 0, 0, -0.3),
            	new Waypoint(0, -12 * 4, 0, 0.3),
            	new Waypoint(0, 0, 0, -0.3),
            	new Waypoint(12*3, -12 * 4, 0, 0.3),
            	new Waypoint(0, 0, 0, -0.3),
            	new Waypoint(12*1, 12 * 4, 0, 0.3),
            	new Waypoint(0, 0, 0, -0.3)
            });
        
        initialized = true;
    }

    public void addPath(String name, Waypoint[] points) {
        paths.putIfAbsent(name, points);
    }

    public IWaypointProvider getProvider(String path) {
        if(!initialized) {
            throw new IllegalStateException("Factory has not been initialized!");
        }

        final Waypoint[] pts = paths.get(path);

        if(pts == null) {
            throw new IllegalArgumentException("No such path " + path);
        }

        return new WaypointProvider(pts);
    }
}
