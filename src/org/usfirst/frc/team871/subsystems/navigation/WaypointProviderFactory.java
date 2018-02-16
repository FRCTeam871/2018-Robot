package org.usfirst.frc.team871.subsystems.navigation;

import org.usfirst.frc.team871.subsystems.Grabber;
import org.usfirst.frc.team871.subsystems.lifter.SuperLift;
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
