package net.luis.generation.runner;

import net.luis.generation.config.Config;
import net.luis.generation.config.ImageConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class ImageRunner implements Runnable {
	
	private static final Logger LOGGER = LogManager.getLogger(ImageRunner.class);
	
	private final ImageConfig config;
	
	public ImageRunner(@NotNull Config config) {
		this.config = Objects.requireNonNull(config, "Config must not be null").image();
	}
	
	@Override
	public void run() {}
}
