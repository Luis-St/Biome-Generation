package net.luis.noise.color;

import net.luis.biome.BiomeColor;

public class NoiseColors {
	
	public static BiomeColor temperature(double noise) {
		if (noise >= 2.04) {
			return new BiomeColor(40, 0, 0); // hot
		} else if (noise >= 1.92) {
			return new BiomeColor(80, 0, 0);
		} else if (noise >= 1.8) {
			return new BiomeColor(120, 0, 0);
		} else if (noise >= 1.68) {
			return new BiomeColor(160, 0, 0);
		} else if (noise >= 1.56) {
			return new BiomeColor(200, 0, 0);
		} else if (noise >= 1.44) {
			return new BiomeColor(255, 80, 0);
		} else if (noise >= 1.32) {
			return new BiomeColor(255, 100, 0);
		} else if (noise >= 1.2) {
			return new BiomeColor(255, 120, 0);
		} else if (noise >= 1.08) {
			return new BiomeColor(255, 140, 0); // warm
		} else if (noise >= 0.96) {
			return new BiomeColor(255, 160, 0);
		} else if (noise >= 0.84) {
			return new BiomeColor(255, 180, 0);
		} else if (noise >= 0.72) {
			return new BiomeColor(255, 200, 0);
		} else if (noise >= 0.6) {
			return new BiomeColor(255, 220, 0);
		} else if (noise >= 0.48) {
			return new BiomeColor(255, 240, 0);
		} else if (noise >= 0.36) {
			return new BiomeColor(235, 255, 0);
		} else if (noise >= 0.24) {
			return new BiomeColor(205, 255, 0);
		} else if (noise >= 0.12) {
			return new BiomeColor(175, 255, 0);
		} else if (noise >= 0.0) {
			return new BiomeColor(145, 255, 0); // temperate
		} else if (noise >= -0.12) {
			return new BiomeColor(0, 255, 132);
		} else if (noise >= -0.24) {
			return new BiomeColor(0, 255, 170);
		} else if (noise >= -0.36) {
			return new BiomeColor(0, 255, 198);
		} else if (noise >= -0.48) {
			return new BiomeColor(0, 255, 255);
		} else if (noise >= -0.6) {
			return new BiomeColor(0, 209, 255);
		} else if (noise >= -0.72) {
			return new BiomeColor(0, 178, 255);
		} else if (noise >= -0.84) {
			return new BiomeColor(0, 136, 255);
		} else if (noise >= -0.96) {
			return new BiomeColor(0, 116, 255);
		} else if (noise >= -1.08) {
			return new BiomeColor(0, 93, 255); // cold
		} else if (noise >= -1.2) {
			return new BiomeColor(0, 62, 194);
		} else if (noise >= -1.32) {
			return new BiomeColor(0, 0, 255);
		} else if (noise >= -1.44) {
			return new BiomeColor(0, 0, 155);
		} else if (noise >= -1.56) {
			return new BiomeColor(0, 0, 128);
		} else if (noise >= -1.68) {
			return new BiomeColor(0, 0, 109);
		} else if (noise >= -1.8) {
			return new BiomeColor(0, 0, 89);
		} else if (noise >= -1.92) {
			return new BiomeColor(0, 0, 62);
		} else {
			return new BiomeColor(0, 0, 31); // frozen
		}
	}
	
	public static BiomeColor humidity(double noise) {
		if (noise >= 2.0) {
			return new BiomeColor(255, 255, 0); // very intensiv rain
		} else if (noise >= 1.8) {
			return new BiomeColor(255, 200, 0);
		} else if (noise >= 1.6) {
			return new BiomeColor(255, 160, 0);
		} else if (noise >= 31.4) {
			return new BiomeColor(255, 120, 0);
		} else if (noise >= 1.2) {
			return new BiomeColor(255, 80, 0);
		} else if (noise >= 1.0) {
			return new BiomeColor(255, 40, 0); // intensiv rain
		} else if (noise >= 0.8) {
			return new BiomeColor(255, 0, 0);
		} else if (noise >= 0.6) {
			return new BiomeColor(255, 0, 40);
		} else if (noise >= 0.4) {
			return new BiomeColor(255, 0, 80); // moderate rain
		} else if (noise >= 0.2) {
			return new BiomeColor(255, 0, 120);
		} else if (noise >= 0.0) {
			return new BiomeColor(255, 0, 255);
		} else if (noise >= -0.2) {
			return new BiomeColor(160, 0, 255);
		} else if (noise >= -0.4) {
			return new BiomeColor(120, 0, 255); // light rain
		} else if (noise >= -0.6) {
			return new BiomeColor(80, 0, 255);
		} else if (noise >= -0.8) {
			return new BiomeColor(20, 0, 255);
		} else if (noise >= -1.0) {
			return new BiomeColor(0, 0, 255); // drizzling rain
		} else if (noise >= -1.2) {
			return new BiomeColor(0, 75, 255);
		} else if (noise >= -1.4) {
			return new BiomeColor(0, 115, 255);
		} else if (noise >= -1.8) {
			return new BiomeColor(0, 155, 255);
		} else if (noise >= -2.0) {
			return new BiomeColor(0, 195, 255);
		} else {
			return new BiomeColor(0, 255, 255); // no rain
		}
	}
	
	public static BiomeColor continental(double noise) {
		if (noise >= 1.9) {
			return new BiomeColor(0, 24, 0); // far inland
		} else if (noise >= 1.6) {
			return new BiomeColor(0, 39, 0);
		} else if (noise >= 1.3) {
			return new BiomeColor(0, 66, 0); // mid inland
		} else if (noise >= 1.0) {
			return new BiomeColor(0, 89, 0);
		} else if (noise >= 0.7) {
			return new BiomeColor(0, 159, 0); // inland
		} else if (noise >= 0.4) {
			return new BiomeColor(0, 198, 0);
		} else if (noise >= 0.012) {
			return new BiomeColor(0, 255, 0); // near inland
		} else if (noise >= -0.012) {
			return new BiomeColor(255, 207, 66); // coast
		} else if (noise >= -0.4) {
			return new BiomeColor(0, 255, 221); // shallow ocean
		} else if (noise >= -0.7) {
			return new BiomeColor(0, 178, 221);
		} else if (noise >= -1.0) {
			return new BiomeColor(0, 140, 221); // ocean
		} else if (noise >= -1.3) {
			return new BiomeColor(0, 82, 221);
		} else if (noise >= -1.6) {
			return new BiomeColor(0, 0, 221); // deep ocean
		} else if (noise >= -1.9) {
			return new BiomeColor(0, 0, 132);
		} else {
			return new BiomeColor(0, 0, 55); // ocean digging
		}
	}
	
	public static BiomeColor erosion(double noise) {
		if (noise >= 2.1) {
			return new BiomeColor(51, 20, 0); // mountain peak
		} else if (noise >= 1.95) {
			return new BiomeColor(76, 29, 0);
		} else if (noise >= 1.8) {
			return new BiomeColor(102, 39, 0);
		} else if (noise >= 1.65) {
			return new BiomeColor(127, 48, 0);
		} else if (noise >= 1.5) {
			return new BiomeColor(165, 63, 0); // mountain
		} else if (noise >= 1.35) {
			return new BiomeColor(255, 97, 0);
		} else if (noise >= 1.2) {
			return new BiomeColor(255, 124, 0);
		} else if (noise >= 1.05) {
			return new BiomeColor(255, 159, 0); // highland
		} else if (noise >= 0.9) {
			return new BiomeColor(255, 217, 0);
		} else if (noise >= 0.75) {
			return new BiomeColor(230, 255, 0);
		} else if (noise >= 0.6) {
			return new BiomeColor(190, 255, 0); // default level
		} else if (noise >= 0.45) {
			return new BiomeColor(130, 255, 0);
		} else if (noise >= 0.3) {
			return new BiomeColor(0, 255, 90);
		} else if (noise >= 0.15) {
			return new BiomeColor(0, 255, 150);
		} else if (noise >= 0.0) {
			return new BiomeColor(0, 255, 210); // sea level
		} else if (noise >= -0.15) {
			return new BiomeColor(0, 255, 255);
		} else if (noise >= -0.3) {
			return new BiomeColor(0, 209, 255);
		} else if (noise >= -0.45) {
			return new BiomeColor(0, 190, 255);
		} else if (noise >= -0.6) {
			return new BiomeColor(0, 170, 255); // shallow ocean floor
		} else if (noise >= -0.75) {
			return new BiomeColor(0, 150, 255);
		} else if (noise >= -0.9) {
			return new BiomeColor(0, 130, 255);
		} else if (noise >= -1.05) {
			return new BiomeColor(0, 110, 255); // ocean floor
		} else if (noise >= -1.2) {
			return new BiomeColor(0, 90, 255);
		} else if (noise >= -1.35) {
			return new BiomeColor(0, 0, 255);
		} else if (noise >= -1.5) {
			return new BiomeColor(0, 0, 120); // deep coean floor
		} else if (noise >= -1.65) {
			return new BiomeColor(0, 0, 90);
		} else if (noise >= -1.8) {
			return new BiomeColor(0, 0, 70);
		} else if (noise >= -1.95) {
			return new BiomeColor(0, 0, 50);
		} else {
			return new BiomeColor(0, 0, 30); // deepest ocean floor
		}
	}
}
