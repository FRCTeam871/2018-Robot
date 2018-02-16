package org.usfirst.frc.team871.subsystems.navigation;

public interface IWaypointProvider {

    Waypoint getNextWaypoint();

    boolean hasNext();

    void reset();

    IWaypointProvider reverse();
}
