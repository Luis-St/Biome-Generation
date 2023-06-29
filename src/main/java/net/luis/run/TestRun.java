package net.luis.run;

import net.luis.utils.logging.LoggerConfiguration;
import net.luis.utils.logging.LoggingUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestRun {
	
	static {
		LoggingUtils.initialize(LoggerConfiguration.DEFAULT);
	}
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	public static void main(String[] args) {
/*		Noise noise = PerlinNoise.create(new WorldgenRandom(10), IntStream.rangeClosed(-3, 0));
		double min = 0.0;
		double max = 0.0;
		for (int x = 0; x <= 100000; x++) {
			for (int z = 0; z <= 100000; z++) {
				double value = noise.getValue(x * 0.0625, z * 0.0625);
				min = Math.min(min, value);
				max = Math.max(max, value);
			}
		}
		LOGGER.info("PerlinNoise min: {}", min);
		LOGGER.info("PerlinNoise max: {}", max);
		LOGGER.info("");*/
/*		LOGGER.info("TEMPERATURE, min: {}, max: {}", Noises.TEMPERATURE.min(), Noises.TEMPERATURE.max());
		LOGGER.info("HUMIDITY, min: {}, max: {}", Noises.HUMIDITY.min(), Noises.HUMIDITY.max());
		LOGGER.info("CONTINENTALNESS, min: {}, max: {}", Noises.CONTINENTALNESS.min(), Noises.CONTINENTALNESS.max());
		LOGGER.info("EROSION, min: {}, max: {}", Noises.EROSION.min(), Noises.EROSION.max());*/
		
		int threads = 10;
		int size = 10;
		
		divideRectangle(size, size, threads);
		
		
	}
	
	public static void divideRectangle(int width, int height, int parts) {
		if (parts <= 0) {
			LOGGER.warn("Invalid parts count. Please provide a positive value.");
			return;
		}
		
		if (width <= 0 || height <= 0) {
			LOGGER.warn("Invalid rectangle dimensions. Please provide positive values for width and height.");
			return;
		}
		
		int rows = 1;
		int cols = parts;
		
		int minDifference = Math.abs(rows - cols);
		
		for (int i = 2; i <= Math.sqrt(parts); i++) {
			if (parts % i == 0) {
				int currentCols = parts / i;
				int difference = Math.abs(i - currentCols);
				
				if (difference < minDifference) {
					minDifference = difference;
					rows = i;
					cols = currentCols;
				}
			}
		}
		
		int partWidth = width / cols;
		int partHeight = height / rows;
		
		LOGGER.info("Rectangle dimensions: width={}, height={}", width, height);
		LOGGER.info("Parts count: {}", parts);
		System.out.println();
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int x = j * partWidth;
				int y = i * partHeight;
				
				LOGGER.info("Part {}", (i * cols + j + 1));
				LOGGER.info("Top: ({}, {})", x + 1, y + 1);
				LOGGER.info("Bottom: ({}, {})", partWidth + 1, partHeight + 1);
				System.out.println();
			}
		}
	}
}
