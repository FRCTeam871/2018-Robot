package org.usfirst.frc.team871.subsystems.navigation;

import java.util.EnumMap;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WaypointSelector {

	
	EnumMap<WaypointPosition, Boolean> offLimits;
	EnumMap<WaypointPosition, WaypointSide> positionSides;
	NetworkTable table;
	
	public WaypointSelector(NetworkTable table) {
		this.table = table;
		
		offLimits = new EnumMap<>(WaypointPosition.class);
		for(WaypointPosition wp : WaypointPosition.values()) {
			offLimits.put(wp, false);
			positionSides.put(wp, WaypointSide.INBOARD);
		}
	}

	public WaypointProvider choosePath() {
		//TODO write todo message
		return null;
	}
	
	public WaypointProvider findPath(WaypointPosition start, WaypointPosition end, WaypointSide side) {
		//TODO yes
		return null;
	}
	
	public void setupConfiguration() {
		String config = DriverStation.getInstance().getGameSpecificMessage(); // LRL RLR LLL RRR
		
		offLimits.put(WaypointPosition.SWITCH_L, config.charAt(0) == 'R');
		offLimits.put(WaypointPosition.SWITCH_R, config.charAt(0) == 'L');
		offLimits.put(WaypointPosition.SCALE_L, config.charAt(0) == 'R');
		offLimits.put(WaypointPosition.SCALE_R, config.charAt(0) == 'L');
		
	}
	
	public void determineOffLimits() {
		
		if(!offLimits.get(WaypointPosition.SWITCH_L)) {
			offLimits.put(WaypointPosition.SWITCH_L, table.getEntry("lSwitchOffLimits").getBoolean(false));
		}
		
		if(!offLimits.get(WaypointPosition.SWITCH_R)) {
			offLimits.put(WaypointPosition.SWITCH_R, table.getEntry("rSwitchOffLimits").getBoolean(false));
		}
		
		if(!offLimits.get(WaypointPosition.SCALE_L)) {
			offLimits.put(WaypointPosition.SCALE_L, table.getEntry("lScaleOffLimits").getBoolean(false));
		}
		
		if(!offLimits.get(WaypointPosition.SCALE_R)) {
			offLimits.put(WaypointPosition.SCALE_R, table.getEntry("rScaleOffLimits").getBoolean(false));
		}
		
	}
	
}
