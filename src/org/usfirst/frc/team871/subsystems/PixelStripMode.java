package org.usfirst.frc.team871.subsystems;

public enum PixelStripMode {

	DISABLED(0),
	FIRE_RED(1),
	FIRE_BLUE(2),
	BALLS(3);
	
	private int index = 0;
	private PixelStripMode(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
}
