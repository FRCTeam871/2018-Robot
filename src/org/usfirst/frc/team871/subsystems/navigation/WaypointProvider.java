package org.usfirst.frc.team871.subsystems.navigation;

import java.util.ArrayList;
import java.util.List;

public class WaypointProvider extends ArrayList<Waypoint> implements IWaypointProvider{

	private int index;
	
	public WaypointProvider() {
		index = 0;
		
	}
	@Override
	public Waypoint getNextWaypoint() {
		return super.get(this.index++);
	}

	@Override
	public boolean hasNext() {
		if(super.size() == index) {
			return false;	
		}
		
		return true;
		
	}

	@Override
	public List<Waypoint> getAvailableWaypoints() {
		return this;
	}

	public int getIndex() {
		return index;
	}
}
