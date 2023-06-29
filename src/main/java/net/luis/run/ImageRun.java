package net.luis.run;

import net.luis.noise.Noises;
import net.luis.noise.color.NoiseColors;
import net.luis.utils.logging.LoggerConfiguration;
import net.luis.utils.logging.LoggingUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ImageRun {
	
	// TODO: add windyness
	
	public static final DateFormat FORMAT = new SimpleDateFormat("mm:ss:SSS");
	public static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) throws IOException {
		
		long start = System.currentTimeMillis();
		int size = Integer.parseInt(args[0]);
		LOGGER.info("Started making noise images");
		Thread temperature = new Thread(() -> {
			try {
				ImageIO.write(temperature(size), "png", new File(System.getProperty("user.home") + "\\Desktop\\temperature.png"));
				LOGGER.info("Made temperature noise image");
			} catch (IOException exception) {
				LOGGER.error("Could not make temperature noise image", exception);
			}
		});
		Thread humidity = new Thread(() -> {
			try {
				ImageIO.write(humidity(size), "png", new File(System.getProperty("user.home") + "\\Desktop\\humidity.png"));
				LOGGER.info("Made humidity noise image");
			} catch (IOException exception) {
				LOGGER.error("Could not make humidity noise image", exception);
			}
		});
		Thread continentalness = new Thread(() -> {
			try {
				ImageIO.write(continentalness(size), "png", new File(System.getProperty("user.home") + "\\Desktop\\continentalness.png"));
				LOGGER.info("Made continental noise image");
			} catch (IOException exception) {
				LOGGER.error("Could not make continental noise image", exception);
			}
		});
		Thread erosion = new Thread(() -> {
			try {
				ImageIO.write(erosion(size), "png", new File(System.getProperty("user.home") + "\\Desktop\\erosion.png"));
				LOGGER.info("Made erosion noise image");
			} catch (IOException exception) {
				LOGGER.error("Could not make erosion noise image", exception);
			}
		});
		temperature.start();
		humidity.start();
		continentalness.start();
		erosion.start();
	}
	
	protected static BufferedImage temperature(int size) {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();
		for (int x = 0; x <= size; x++) {
			for (int z = 0; z < size; z++) {
				graphics.setColor(NoiseColors.temperature(Noises.TEMPERATURE.getValue(x * 0.0625, z * 0.0625)).toAwt());
				graphics.drawLine(x, z, x, z);
			}
		}
		return image;
	}
	
	protected static BufferedImage humidity(int size) {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();
		for (int x = 0; x <= size; x++) {
			for (int z = 0; z < size; z++) {
				graphics.setColor(NoiseColors.humidity(Noises.HUMIDITY.getValue(x * 0.0625, z * 0.0625) * 2.5).toAwt());
				graphics.drawLine(x, z, x, z);
			}
		}
		return image;
	}
	
	protected static BufferedImage continentalness(int size) {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();
		for (int x = 0; x <= size; x++) {
			for (int z = 0; z < size; z++) {
				graphics.setColor(NoiseColors.continental(Noises.CONTINENTALNESS.getValue(x * 0.0625, z * 0.0625)).toAwt());
				graphics.drawLine(x, z, x, z);
			}
		}
		return image;
	}
	
	protected static BufferedImage erosion(int size) {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();
		for (int x = 0; x <= size; x++) {
			for (int z = 0; z < size; z++) {
				graphics.setColor(NoiseColors.erosion(Noises.EROSION.getValue(x * 0.0625, z * 0.0625)).toAwt());
				graphics.drawLine(x, z, x, z);
			}
		}
		return image;
	}
	
	static {
		LoggingUtils.initialize(LoggerConfiguration.DEFAULT);
	}
}
