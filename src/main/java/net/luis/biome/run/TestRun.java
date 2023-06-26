package net.luis.biome.run;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.luis.biome.noise.Noises;

public class TestRun {

	public static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) {
//		Noise noise = PerlinNoise.create(new WorldgenRandom(10), IntStream.rangeClosed(-3, 0));
//		double min = 0.0;
//		double max = 0.0;
//		for (int x = 0; x <= 100000; x++) {
//			for (int z = 0; z <= 100000; z++) {
//				double value = noise.getValue(x * 0.0625, z * 0.0625);
//				min = Math.min(min, value);
//				max = Math.max(max, value);
//			}
//		}
//		LOGGER.info("PerlinNoise min: {}", min);
//		LOGGER.info("PerlinNoise max: {}", max);
//		LOGGER.info("");
		LOGGER.info("TEMPERATURE, min: {}, max: {}", Noises.TEMPERATURE.min(), Noises.TEMPERATURE.max());
		LOGGER.info("HUMIDITY, min: {}, max: {}", Noises.HUMIDITY.min(), Noises.HUMIDITY.max());
		LOGGER.info("CONTINENTALNESS, min: {}, max: {}", Noises.CONTINENTALNESS.min(), Noises.CONTINENTALNESS.max());
		LOGGER.info("EROSION, min: {}, max: {}", Noises.EROSION.min(), Noises.EROSION.max());	
	}
}
