package net.luis.generation.runner;

import net.luis.generation.config.Config;
import net.luis.generation.config.DataConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class DataGenerationRunner implements Runnable {
	
	private static final Logger LOGGER = LogManager.getLogger(DataGenerationRunner.class);
	
	private final DataConfig config;
	
	public DataGenerationRunner(@NotNull Config config) {
		this.config = Objects.requireNonNull(config, "Config must not be null").data();
	}
	
	@Override
	public void run() {}
}
