package org.usfirst.frc.team871.subsystems.navigation;

import java.util.List;

public interface IWaypointProvider {

	public Waypoint getNextWaypoint();
	
	public boolean hasNext();
	
	public List<Waypoint> getAvailableWaypoints();
}
