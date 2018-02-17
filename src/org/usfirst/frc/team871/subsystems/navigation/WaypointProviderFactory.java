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

//Google Sheets document containing paths: https://docs.google.com/spreadsheets/d/1tjnVjhbxHG2ng1csibecWShjA-qXf1AlzuT73m69ll4/edit?usp=sharing


public enum WaypointProviderFactory {
    DEFAULT;
	
    private boolean initialized = false;
    private final Map<String, Waypoint[]> paths = new HashMap<>();

    public void init(Grabber grabber, SuperLift lift, IRobotConfiguration config) {
        //		WaypointProvider squareProvider = new WaypointProvider(new Waypoint(1, 0, 0, .3),
        //				new Waypoint(size,size,0,.3),
        //				new Waypoint(0, size, 0, .3),
        //				new Waypoint(0,0,0,.3, new TootTootAction(config.getTootToot())));
        //		WaypointProvider prov = new WaypointProvider(new Waypoint(0, 0, 0, 0.3), new Waypoint(12 * 3, 0, 0, 0.3), new Waypoint(12 * 3, -12 * 5, 0, 0.3), new Waypoint(12 * 6, -12 * 5, 0, 0.3), new Waypoint(12 * 6, -12 * 11, 0, 0.3), new Waypoint(12 * 3, -12 * 11, 0, 0.3), new Waypoint(12 * 3, -12 * 16, 0, 0.3), new Waypoint(12 * 0, -12 * 16, 0, 0.3));
        //		WaypointProvider prov = new WaypointProvider(new Waypoint(0, 0, 0, 0.3), new Waypoint(12 * 6, 0, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.LOW_SWITCH)), new Waypoint(0, 0, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.SCALE_MID)), new Waypoint(0, 0, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND)));

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

        addPath("WoodshopDrop", pts);
        addPath("LSwitchLScale", new Waypoint[] {
			new Waypoint(168, 55, 0, 0.3), new Waypoint(228.735, 36, 0, 0.3),
			new Waypoint(324, 34, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.SCALE_HIGH)),
			new Waypoint(324, 34 + 24, 0, 0.3, new SetGrabberAction(grabber, false)),
			new Waypoint(324, 34, 0, -0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND))
        });
        
        addPath("LSwitchRScale", new Waypoint[] {
			new Waypoint(168, 55, 0, 0.3), new Waypoint(228.735, 36, 0, 0.3), new Waypoint(228.735, 228, 0, 0.3),
			new Waypoint(335.65, 228, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.SCALE_HIGH)),
			new Waypoint(335.65, 36 + 12, 0, 0.3, new SetGrabberAction(grabber, false)),
			new Waypoint(335.65, 36, 0, 0.3, new LiftSetpointAction(lift, SetpointHeights.GROUND))
		});
        
        addPath("LStartLSwitch", new Waypoint[]{
        	new Waypoint(156, 36, 0, 0.3)
        });
        
        addPath("LScaleRSwitch", new Waypoint[]{
        	new Waypoint(12 * 27, 12 * 6, 0, 0.3),
        	new Waypoint(12 * 18, 12 * 6, 0, 0.3),
        	new Waypoint(12 * 18, 12 * 14 + 33/2, 0, 0.3),
        	new Waypoint(12 * 14 , 12 * 14 + 33/2, 0, 0.3)
        });
        
        addPath("TestReverse", new Waypoint[]{
        	new Waypoint(12*12, 0, 0, 0.3),
        	new Waypoint(0, 0, 0, -0.3),
        });

        //start of new paths //TODO: these only get the robot to right next to their respective objects (switch/scale) must add actions and get closer
        addPath("Left Position to Left Switch", new Waypoint[]{
                new Waypoint(156, 36, 0, 0.3),
        });

        addPath("Left Positoin to Left Scale", new Waypoint[]{
                new Waypoint(324, 36, 0, 0.3),
        });

        addPath("Right Position to Right Switch", new Waypoint[]{
                new Waypoint(156, 288, 0, 0.3),
        });

        addPath("Right Position to Right Scale", new Waypoint[]{
                new Waypoint(324, 288, 0, 0.3),
        });

        addPath("Left Position to Right Switch", new Waypoint[]{
                new Waypoint(72, 36, 0, 0.3),
                new Waypoint(72, 288, 0, 0.3),
                new Waypoint(156, 288, 0, 0.3),
        });

        addPath("Left Postion to Right Scale", new Waypoint[]{
                new Waypoint(72, 36, 0, 0.3),
                new Waypoint(72, 288, 0, 0.3),
                new Waypoint(324, 288, 0, 0.3),
        });

        addPath("Right Position to Left Switch", new Waypoint[]{
                new Waypoint(72, 288, 0, 0.3),
                new Waypoint(72, 36, 0, 0.3),
                new Waypoint(156, 36, 0, 0.3),
        });

        addPath("Right Position to Left Scale", new Waypoint[]{
                new Waypoint(72, 288, 0, 0.3),
                new Waypoint(72, 36, 0, 0.3),
                new Waypoint(324, 36, 0, 0.3),
        });

        addPath("Center Position To Left Switch", new Waypoint[]{
                new Waypoint(72, 162, 0, 0.3),
                new Waypoint(72, 36, 0, 0.3),
                new Waypoint(156, 36, 0, 0.3),
        });

        addPath("Center Postion to Left Scale", new Waypoint[]{
                new Waypoint(72, 162, 0, 0.3),
                new Waypoint(72, 36, 0, 0.3),
                new Waypoint(324, 36, 0, 0.3),
        });

        addPath("Center Position to Right Switch", new Waypoint[]{
                new Waypoint(72, 162, 0, 0.3),
                new Waypoint(72, 288, 0, 0.3),
                new Waypoint(156, 288, 0, 0.3),
        });

        addPath("Center Position to Right Scale", new Waypoint[]{
                new Waypoint(72, 162, 0, 0.3),
                new Waypoint(72, 288, 0, 0.3),
                new Waypoint(324, 288, 0, 0.3),
        });
        //end of new paths

        initialized = true;
    }

    public void addPath(String name, Waypoint[] points) {
        paths.putIfAbsent(name, points);
    }

    public IWaypointProvider getProvider(String path) {
        if(!initialized) {
            throw new IllegalStateException("Waypoint Factory has not been initialized!");
        }

        final Waypoint[] pts = paths.get(path);

        if(pts == null) {
            throw new IllegalArgumentException("No such path " + path);
        }

        return new WaypointProvider(pts);
    }
}
