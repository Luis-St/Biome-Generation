package net.luis.biome.biome;

/**
 *
 * @author Luis-st
 *
 */

public record BiomeColor(int red, int green, int blue) {
	
	public java.awt.Color toAwt() {
		return new java.awt.Color(this.red, this.green, this.blue);
	}
	
	public javafx.scene.paint.Color toFx() {
		return new javafx.scene.paint.Color((double) this.red / 255.0, (double) this.green / 255.0, (double) this.blue / 255.0, 1.0);
	}
}
