package net.luis.run;

import com.google.common.collect.Lists;
import net.luis.utils.logging.*;
import net.luis.utils.util.Pair;
import org.apache.logging.log4j.*;

import java.util.List;
import java.util.Objects;

public class TestRun {
	
	static {
		LoggingUtils.initialize(LoggerConfiguration.DEFAULT.disableLogging(LoggingType.FILE)
			//.addDefaultLogger(Level.DEBUG, LoggingType.CONSOLE)
		);
	}
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	public static void main(String[] args) {
		/*Noise noise = PerlinNoise.create(new WorldgenRandom(10), IntStream.rangeClosed(-3, 0));
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
		/*LOGGER.info("TEMPERATURE, min: {}, max: {}", Noises.TEMPERATURE.min(), Noises.TEMPERATURE.max());
		LOGGER.info("HUMIDITY, min: {}, max: {}", Noises.HUMIDITY.min(), Noises.HUMIDITY.max());
		LOGGER.info("CONTINENTALNESS, min: {}, max: {}", Noises.CONTINENTALNESS.min(), Noises.CONTINENTALNESS.max());
		LOGGER.info("EROSION, min: {}, max: {}", Noises.EROSION.min(), Noises.EROSION.max());*/
		LoggingUtils.enable(Level.DEBUG, LoggingType.FILE);
		int threads = 4;
		int size = 20;
		
		List<Rectangle> rectangles = divideRectangle(size, size, threads);
		for (Rectangle rectangle : rectangles) {
			System.out.println();
			LOGGER.info("({}|{}) - ({}|{})", rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y);
			LOGGER.info("({}|{}) - ({}|{})", rectangle.x, rectangle.y + rectangle.height, rectangle.x + rectangle.width, rectangle.y + rectangle.height);
		}
	}
	
	public static List<Rectangle> divideRectangle(int width, int height, int parts) {
		Pair<Integer, Integer> pair = tryFindParts(width, height, parts);
		if (pair == null) {
			LOGGER.error("Could not divide rectangle ({} x {}) into {} parts", width, height, parts);
			return Lists.newArrayList();
		}
		int horizontalSize = height / pair.getFirst();
		int verticalSize = width / pair.getSecond();
		
		List<Rectangle> rectangles = Lists.newArrayList();
		for (int part = 0; part < parts; part++) {
			rectangles.add(new Rectangle(
				(part % pair.getSecond()) * verticalSize,
				(part / pair.getSecond()) * horizontalSize,
				verticalSize,
				horizontalSize
			));
		}
		return rectangles;
	}
	
	private static Pair<Integer, Integer> tryFindParts(int width, int height, int parts) {
		Pair<Integer, Integer> result = tryDivideMultiple(width, height, parts);
		if (result != null) {
			return result;
		}
		if (width % parts == 0) {
			return Pair.of(parts, 1);
		}
		return height % parts == 0 ? Pair.of(1, parts) : null;
	}
	
	private static Pair<Integer, Integer> tryDivideMultiple(int width, int height, int parts) {
		int horizontalParts = parts % 2 == 0 ? parts / 2 : parts / 2 + 1;
		int verticalParts = parts == 1 ? 1 : parts / 2;
		LOGGER.debug("HParts: {}, VParts: {}", horizontalParts, verticalParts);
		if (horizontalParts + verticalParts != parts) {
			LOGGER.debug("Could not divide rectangle ({} x {}) into {} horizontal and {} vertical parts", width, height, horizontalParts, verticalParts);
			return null;
		}
		int area = width * height;
		int horizontalSize = height / horizontalParts;
		int verticalSize = width / verticalParts;
		if ((horizontalSize * verticalSize) * parts != area) {
			LOGGER.debug("Could not divide rectangle ({} x {}) into {} parts ({})", width, height, parts, horizontalSize * verticalSize);
			return null;
		}
		return Pair.of(horizontalParts, verticalParts);
	}
	
	public static record Rectangle(int x, int y, int width, int height) {}
}
