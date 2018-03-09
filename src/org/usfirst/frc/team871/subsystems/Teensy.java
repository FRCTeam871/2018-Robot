package org.usfirst.frc.team871.subsystems;

import java.util.ArrayList;
import java.util.List;import com.sun.prism.Texture.WrapMode;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.WriteBufferMode;

public class Teensy {

	private SerialPort port;
	private List<String> queue = new ArrayList<>();
	
	public Teensy() {
		this.port = new SerialPort(9600, Port.kMXP);
	}
	
	public void playSound(Sound sound) {
		write("audio play " + sound.getPath());
	}
	
	public void stopSound() {
		write("audio mute");
	}
	
	public void setVolume(double volume) {
		write("audio volume " + volume);
	}
	
	public void setPixelStripMode(int strip, PixelStripMode mode) {
		write("led " + strip + " " + mode.getIndex());
	}
	
	public void write(String str) {
		queue.add(str);
	}
	
	private long lastFlush = 0;
	public void update() {
		
		if(port.getBytesReceived() > 0) {
			System.out.println("Recieved: " + port.readString());
		}
		
		long now = System.currentTimeMillis();
		if(now - lastFlush >= 100) {
			String str = null;
			
			if(!queue.isEmpty()) {
				str = queue.get(0);
				queue.remove(0);
				
				System.out.println("Writing: " + str);
				port.writeString(str + "\n");
				port.flush();
				
				lastFlush = now;
			}
		}
	}
	
}
