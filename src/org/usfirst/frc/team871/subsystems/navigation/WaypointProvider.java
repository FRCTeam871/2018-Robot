package org.usfirst.frc.team871.subsystems.navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * provides waypoints in a list one by one with no modification to the waypoints for navigation
 *  @author Team871-TPfaffe
 */
public class WaypointProvider implements IWaypointProvider {

    private int index;

    private ArrayList<Waypoint> waypointArrayList;

    public WaypointProvider(Waypoint... points) {
        index = 0;
        waypointArrayList = new ArrayList<Waypoint>();

        for(Waypoint point: points){
            waypointArrayList.add(point); //add all points from constructor argument
        }

    }

    @Override
    public Waypoint getNextWaypoint() {
    	System.out.println("getNextWaypoint()");
        return this.waypointArrayList.get(this.index++);
    }

    @Override
    public boolean hasNext() {
    	System.out.println("hasNext() == " + (this.waypointArrayList.size() != index));
        return this.waypointArrayList.size() != index;

    }

    @Override
    public List<Waypoint> getAvailableWaypoints() {
        ArrayList<Waypoint> copyList = this.waypointArrayList;
        return copyList;
    }

    public int getIndex() {
        return this.index;
    }
}
