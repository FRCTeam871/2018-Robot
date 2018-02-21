package org.usfirst.frc.team871.subsystems;

public enum Sound {
	LEEROY_JENKINS("leeroy.wav"),
	TEST("test.wav"),
	AHA("aha.wav"),
	CRAZY("crazy.wav"),
	DK1("dk1.wav"),
	GALAGA_1("galaga1.wav"),
	GALAGA_2("galaga2.wav"),
	JUNGLE("jungle.wav"),
	MARIO_1("mario1.wav"),
	START("start.wav"),
	TETRIS("tetris.wav");

	private String path;
	Sound(String path){
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
}
