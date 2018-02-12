package org.usfirst.frc.team871.subsystems.navigation;

import java.util.List;

public interface IWaypointProvider {

    Waypoint getNextWaypoint();

    boolean hasNext();

    List<Waypoint> getAvailableWaypoints();

    int getIndex();
}
