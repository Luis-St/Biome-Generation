package net.luis.biome.noise;

import net.luis.biome.noise.generator.NormalNoise;
import net.luis.biome.util.random.WorldgenRandom;

public class Noises {
	
//	public static final NormalNoise TEMPERATURE = NormalNoise.create(new WorldgenRandom(0), -6, 1.5, 0.0, 1.0, 0.0, 0.0, 0.0); // zoom: -11
//	public static final NormalNoise HUMIDITY = NormalNoise.create(new WorldgenRandom(1), -5, 1.5, 0.0, 1.0, 0.0, 0.0, 0.0); // zoom: -6
//	public static final NormalNoise CONTINENTALNESS = NormalNoise.create(new WorldgenRandom(2), -4, 1.5, 0.0, 1.0, 0.0, 0.0, 0.0); // zoom: -7
//	public static final NormalNoise EROSION = NormalNoise.create(new WorldgenRandom(3), -5, 1.5, 0.0, 1.0, 0.0, 0.0, 0.0); // zome: -7
	
	public static final NormalNoise TEMPERATURE = NormalNoise.create(new WorldgenRandom(0), -6, 1.5, 0.0, 1.0, 0.0, 0.0, 0.0); // zoom: -11
	public static final NormalNoise HUMIDITY = NormalNoise.create(new WorldgenRandom(1), -5, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0); // zoom: -6
	public static final NormalNoise CONTINENTALNESS = NormalNoise.create(new WorldgenRandom(2), -4, 1.0, 1.0, 2.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0); // zoom: -7
	public static final NormalNoise EROSION = NormalNoise.create(new WorldgenRandom(3), -5, 2.0, 2.0, 3.0, 4.0, 4.0, 5.0, 6.0, 6.0); // zome: -7
}
