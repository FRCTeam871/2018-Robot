package org.usfirst.frc.team871.subsystems.navigation;

import org.usfirst.frc.team871.subsystems.Grabber;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift.SetpointHeights;
import org.usfirst.frc.team871.subsystems.navigation.actions.LiftSetpointAction;
import org.usfirst.frc.team871.subsystems.navigation.actions.SetGrabberAction;
import org.usfirst.frc.team871.subsystems.navigation.actions.TootTootAction;
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
        Waypoint rSwitchFar = new Waypoint(12 * 21, 216, 0, 0);
        Waypoint rSwitchFarInner = new Waypoint(12 * 19, 216, 0, 0);
        Waypoint lScale = new Waypoint(312, 34, 0, 0);
        Waypoint lScaleInner = new Waypoint(312, 34 + 24, 0, 0);
        Waypoint rScale = new Waypoint(328, 280, 0, 0);
        Waypoint rScaleInner = new Waypoint(328, 280 - 24, 0, 0);
        Waypoint rScaleClose = new Waypoint(298 - 24, 236, 0, 0); //230 252
        Waypoint rScaleCloseInner = new Waypoint(298, 236, 0, 0);
        
        Waypoint lCenterLine = new Waypoint(12 * 21, 34, 0, 0);
        Waypoint rCenterLine = new Waypoint(12 * 21, 290, 0, 0);
        
        addPath("WoodshopDrop", pts);
        
        addPath("LSwitch", new Waypoint[]{
        	new Waypoint(lSwitchMiddle, 0.7, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH, true)),
        	new Waypoint(lSwitchMiddleInner, 0.3, new SetGrabberAction(grabber, false)),
        	new Waypoint(lSwitchMiddle, -0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND)),
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
        	new Waypoint(rSwitchFarInner, 0.3, new SetGrabberAction(grabber, false)),
//        	new Waypoint(12 * 20, 212, 0, -0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND))
        });
        
        //new ones;

        addPath("RStartRSwitch", new Waypoint[]{
                new Waypoint(156, 288, 0, 0.3),
        });

        addPath("RStartRScale", new Waypoint[]{
                new Waypoint(324, 288, 0, 0.3),
        });

        addPath("LStartRSwitch", new Waypoint[]{
        		new Waypoint(lCenterLine, 0.6, null),
            	new Waypoint(rSwitchFar, 0.6, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH)),
            	new Waypoint(rSwitchFarInner, 0.3, new SetGrabberAction(grabber, false)),
        });

        addPath("LStartRScale", new Waypoint[]{
        		new Waypoint(lCenterLine, 0.6, null),
//        		new Waypoint(rCenterLine, 0.6, null),
        		new Waypoint(12 * 21, 236, 0, 0.6),
            	new Waypoint(rScaleClose, 0.6, new LiftSetpointAction(lift, SetpointHeights.SCALE_HIGH)),
            	new Waypoint(rScaleCloseInner, 0.3, new SetGrabberAction(grabber, false)),
        });

        addPath("RStartLSwitch", new Waypoint[]{
                new Waypoint(72, 288, 0, 0.3),
                new Waypoint(72, 36, 0, 0.3),
                new Waypoint(156, 36, 0, 0.3),
        });

        addPath("RStartLScale", new Waypoint[]{
                new Waypoint(72, 288, 0, 0.3),
                new Waypoint(72, 36, 0, 0.3),
                new Waypoint(324, 36, 0, 0.3),
        });

        addPath("MStartLSwitch", new Waypoint[]{
                new Waypoint(72, 162, 0, 0.3),
                new Waypoint(72, 36, 0, 0.3),
                new Waypoint(156, 36, 0, 0.3),
        });

        addPath("MStartLScale", new Waypoint[]{
                new Waypoint(72, 162, 0, 0.3),
                new Waypoint(72, 36, 0, 0.3),
                new Waypoint(324, 36, 0, 0.3),
        });

        addPath("MStartRSwitch", new Waypoint[]{
                new Waypoint(72, 162, 0, 0.3),
                new Waypoint(72, 288, 0, 0.3),
                new Waypoint(156, 288, 0, 0.3),
        });

        addPath("MStartRScale", new Waypoint[]{
                new Waypoint(72, 162, 0, 0.3),
                new Waypoint(72, 288, 0, 0.3),
                new Waypoint(324, 288, 0, 0.3),
        });
        
        //test paths
        
        addPath("TestHome", new Waypoint[]{
        		new Waypoint(33/2.0 - 4, 64, 0, 0.3),
        		new Waypoint(33/2.0, 64, 0, 0.3)
        });
        
        addPath("TestReverse", new Waypoint[]{
        	new Waypoint(12 * 12, 0, 0, 0.3),
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
