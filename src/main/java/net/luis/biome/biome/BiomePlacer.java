package net.luis.biome.biome;

import static net.luis.biome.biome.Biomes.*;

/**
 *
 * @author Luis-st
 *
 */

public enum BiomePlacer {
	
	INSTANCE;
	
	private final double beachCon = 0.03;
	private final double beachTem = 0.0;
	private final double troBeachTem = 0.5;
	private final double beachEro = 0.5;
	private final double beachHum = 1.0;
	
	public Biome getBiome(double continental, double erosion, double temperature, double humidity) {
		if (continental >= this.beachCon) {
			return PLAINS;
		} else if (this.beachCon >= continental && continental >= -this.beachCon) {
			if (erosion >= this.beachEro) {
				return STONY_SHORE;
			}
			if (temperature >= this.beachTem) {
				if (temperature >= this.troBeachTem && humidity >= this.beachHum) {
					return TROPICAL_BEACH;
				}
				return BEACH;
			} else if (this.beachTem >= temperature) {
				return SNOWY_BEACH;
			}
			return BEACH;
		} else if (-this.beachCon >= continental) {
			return OCEAN;
		}
		return VOID;
	}
}
