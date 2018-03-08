package org.usfirst.frc.team871.subsystems.navigation;

public class WaypointArrayPositionWrapper {

	private final Waypoint[] waypoints;
	private final WaypointPosition startPosition;
	private final WaypointPosition endPosition;
	private final WaypointSide side;
	private String name;
	
	public WaypointArrayPositionWrapper(Waypoint[] waypoints, WaypointPosition startPosition, WaypointPosition endPosition, WaypointSide side) {
		this.waypoints = waypoints;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.side = side;
	}

	public Waypoint[] getWaypoints() {
		return waypoints;
	}

	public WaypointPosition getStartPosition() {
		return startPosition;
	}

	public WaypointPosition getEndPosition() {
		return endPosition;
	}

	public WaypointSide getSide() {
		return side;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
