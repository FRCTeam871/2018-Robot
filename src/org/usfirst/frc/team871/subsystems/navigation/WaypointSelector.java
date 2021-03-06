package org.usfirst.frc.team871.subsystems.navigation;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DriverStation;

public class WaypointSelector {

	private EnumMap<WaypointPosition, Boolean> offLimits;
	private EnumMap<WaypointPosition, WaypointSide> positionSides;
	private List<WaypointArrayPositionWrapper> paths;
	private WaypointPosition startingPosition;
	private WaypointPosition endingPosition;
	private FieldSetup setup;
	
	public WaypointSelector(List<WaypointArrayPositionWrapper> paths, FieldSetup setup) {
		this.paths = paths;
		this.setup = setup;
		
		offLimits = new EnumMap<>(WaypointPosition.class);
		positionSides = new EnumMap<>(WaypointPosition.class);
		for(WaypointPosition wp : WaypointPosition.values()) {
			offLimits.put(wp, false);
			positionSides.put(wp, WaypointSide.INBOARD);
		}
	}

	public WaypointPosition chooseEndpoint() {
		if(startingPosition == WaypointPosition.START_L) {
			if(!isOfflimits(WaypointPosition.SCALE_L)) {
				endingPosition = WaypointPosition.SCALE_L;
			}else if(!isOfflimits(WaypointPosition.SWITCH_L)) {
				endingPosition = WaypointPosition.SWITCH_L;
			}else {
				if(!isOfflimits(WaypointPosition.SCALE_R)) {
					endingPosition = WaypointPosition.SCALE_R;
				}else if(!isOfflimits(WaypointPosition.SWITCH_R)) {
					endingPosition = WaypointPosition.SWITCH_R;
				}else {
					endingPosition = WaypointPosition.AUTOLINE_L;
				}
			}
		}else if(startingPosition == WaypointPosition.START_M) {
			if(!isOfflimits(WaypointPosition.SWITCH_L)) {
				endingPosition = WaypointPosition.SWITCH_L;
			} else if(!isOfflimits(WaypointPosition.SWITCH_R)) {
				endingPosition = WaypointPosition.SWITCH_R;
			} else if(!isOfflimits(WaypointPosition.SCALE_L)) {
				endingPosition = WaypointPosition.SCALE_L;
			} else if(!isOfflimits(WaypointPosition.SCALE_R)) {
				endingPosition = WaypointPosition.SCALE_R;
			} else {
				endingPosition = WaypointPosition.AUTOLINE_M;
			}
		}else if (startingPosition == WaypointPosition.START_R){
			if(!isOfflimits(WaypointPosition.SCALE_R)) {
				endingPosition = WaypointPosition.SCALE_R;
			}else if(!isOfflimits(WaypointPosition.SWITCH_R)) {
				endingPosition = WaypointPosition.SWITCH_R;
			}else {
				if(!isOfflimits(WaypointPosition.SCALE_L)) {
					endingPosition = WaypointPosition.SCALE_L;
				}else if(!isOfflimits(WaypointPosition.SWITCH_L)) {
					endingPosition = WaypointPosition.SWITCH_L;
				}else {
					endingPosition = WaypointPosition.AUTOLINE_R;
				}
			}
		
		}
		
		System.out.println("chooseEndpoint " + startingPosition + " " + endingPosition);
		
		return endingPosition;
	}
	
	public WaypointProvider getPath() {
		return findPath(startingPosition, endingPosition, positionSides.get(endingPosition));
	}
	
	public WaypointProvider findPath(WaypointPosition start, WaypointPosition end, WaypointSide side) {
    	
		Optional<WaypointArrayPositionWrapper> path = paths.stream().filter((t) -> {
			return t.getStartPosition() == start && t.getEndPosition() == end && (t.getSide() == null || t.getSide() == side);
		}).findFirst();
		
		if(path.isPresent()) {
			WaypointArrayPositionWrapper way = path.get();
			System.out.println("findPath(" + start + ", " + end + ", " + side + ") returning " + way.getName());
			return new WaypointProvider(way.getName(), way.getWaypoints());
		}
		
		System.out.println("findPath(" + start + ", " + end + ", " + side + ") returning null");
		return null;
	}
	
	
	public boolean isOfflimits(WaypointPosition wp) {
		return offLimits.get(wp);
	}
	
	public WaypointSide getSide(WaypointPosition wp) {
		return positionSides.get(wp);
	}
	
	public void setupConfiguration() {
		
		String config = setup.getFieldConfiguration();
		
		System.out.println(config);
		
		offLimits.put(WaypointPosition.SWITCH_L, config.charAt(0) == 'R');
		offLimits.put(WaypointPosition.SWITCH_R, config.charAt(0) == 'L');
		offLimits.put(WaypointPosition.SCALE_L, config.charAt(1) == 'R');
		offLimits.put(WaypointPosition.SCALE_R, config.charAt(1) == 'L');
		
		for(WaypointPosition pos : offLimits.keySet()) {
			System.out.println(pos + ": " + offLimits.get(pos));
		}
		
		int startingPositionNumber = setup.getStartPosition();
		System.out.println("start = " + startingPositionNumber);
		
		if(startingPositionNumber == 0) {
			startingPosition = WaypointPosition.START_L;
		}else if(startingPositionNumber == 1) {
			startingPosition = WaypointPosition.START_M;
		}else if(startingPositionNumber == 2){
			startingPosition = WaypointPosition.START_R;
		}
		
	}
	
	public void determineOffLimits() {
		
		if(!offLimits.get(WaypointPosition.SWITCH_L)) {
			offLimits.put(WaypointPosition.SWITCH_L, setup.getSwitchLOffLimits());
		}
		
		if(!offLimits.get(WaypointPosition.SWITCH_R)) {
			offLimits.put(WaypointPosition.SWITCH_R, setup.getSwitchROffLimits());
		}
		
		if(!offLimits.get(WaypointPosition.SCALE_L)) {
			offLimits.put(WaypointPosition.SCALE_L, setup.getScaleLOffLimits());
		}
		
		if(!offLimits.get(WaypointPosition.SCALE_R)) {
			offLimits.put(WaypointPosition.SCALE_R, setup.getScaleROffLimits());
		}
		
	}
	
	public void determinePreferance() {
		positionSides.put(WaypointPosition.SWITCH_R, setup.getSwitchRInboard() ? WaypointSide.INBOARD : WaypointSide.OUTBOARD);
		positionSides.put(WaypointPosition.SWITCH_L, setup.getSwitchLInboard() ? WaypointSide.INBOARD : WaypointSide.OUTBOARD);
	}
	
	public void setup() {
		setupConfiguration();
		determineOffLimits();
		determinePreferance();
	}

}
