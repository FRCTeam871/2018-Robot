package org.usfirst.frc.team871.subsystems.navigation;

import java.util.*;

/**
 * provides waypoints in a list one by one with no modification to the waypoints for navigation
 *  @author Team871-TPfaffe
 */
public class WaypointProvider implements IWaypointProvider {
    private Iterator<Waypoint> it;
    private final List<Waypoint> waypointArrayList;
    private String name;

    public WaypointProvider(String name, List<Waypoint> waypoints) {
        this.waypointArrayList = waypoints;
        it = waypointArrayList.iterator();
        this.name = name;
    }
    
    public WaypointProvider(List<Waypoint> waypoints) {
    	this("No Name", waypoints);
    }
    
    public WaypointProvider(Waypoint... points) {
        this("No Name", Arrays.asList(points));
    }
    
    public WaypointProvider(String name, Waypoint... points) {
        this(name, Arrays.asList(points));
    }

    @Override
    public Waypoint getNextWaypoint() {
        final Waypoint wp = it.next();

        System.out.println("WaypointProvider: Next waypoint -> " + wp.toString());
        return wp;
    }

    @Override
    public boolean hasNext() {
    	final boolean hasNext = it.hasNext();
        System.out.println("WaypointProvider: Has next? " + hasNext);
        return hasNext;
    }

    @Override
    public void reset() {
        System.out.println("WaypointProvider: Resetting.");
        it = waypointArrayList.iterator();
    }

    @Override
    public IWaypointProvider reverse() {
        System.out.println("WaypointProvider: Creating reversed provider.");

        final List<Waypoint> reversed = new ArrayList<>(waypointArrayList);
        Collections.reverse(reversed);
        return new WaypointProvider(reversed);
    }
    
    public String getName() {
    	return name;
    }
}
