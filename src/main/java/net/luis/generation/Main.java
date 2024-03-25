package net.luis.generation;

import javafx.application.Application;
import joptsimple.*;
import net.luis.fxutils.launcher.FxApplication;
import net.luis.generation.config.Config;
import net.luis.generation.runner.*;
import net.luis.utils.io.FileUtils;
import net.luis.utils.logging.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.*;

import java.io.File;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 *
 * @author Luis-St
 *
 */

public class Main {
	
	private static final Logger LOGGER;
	
	public static void main(String[] args) throws Exception {
		LOGGER.info("Starting application");
		OptionParser parser = new OptionParser();
		parser.allowsUnrecognizedOptions();
		
		OptionSpec<Void> help = parser.accepts("help", "Shows the help information");
		OptionSpec<Void> debug = parser.accepts("debug", "Starts the application in debug mode");
		OptionSpec<Void> dev = parser.accepts("dev", "Starts the application in development mode");
		
		OptionSpec<Void> ui = parser.accepts("ui", "Starts the user interface");
		OptionSpec<Void> data = parser.accepts("data", "Generates data");
		OptionSpec<Void> image = parser.accepts("image", "Generates an image");
		
		OptionSpec<File> config = parser.accepts("config", "Sets the config file").requiredUnless(dev, help).withOptionalArg().defaultsTo("./config.json").ofType(File.class);
		
		OptionSet options = parser.parse(args);
		if (options.has(help)) {
			parser.printHelpOn(System.out);
			return;
		}
		if (options.has(debug) || options.has(dev)) {
			LoggingUtils.enable(LoggingType.FILE, Level.DEBUG);
		}
		if (Stream.of(ui, image, data).filter(options::has).count() > 1) {
			LOGGER.error("Only one runner option can be set");
			return;
		}
		
		File configFile = null;
		if (options.has(dev)) {
			configFile = new File("./src/main/resources/config.json");
		} else {
			configFile = options.valueOf(config);
			if (!configFile.exists()) {
				LOGGER.error("Config file does not exist");
				return;
			}
		}
		LOGGER.info("Loading config file from {}", configFile.toPath().normalize().toAbsolutePath().toString());
		Config conf = JsonHelper.decode(configFile, Config.CODEC);
		if (options.has(ui)) {
			LOGGER.info("Starting user interface");
			FxApplication.launch(new UIRunner(conf), args);
		} else if (options.has(image)) {
			LOGGER.info("Generating image");
			new ImageRunner(conf).run();
		} else if (options.has(data)) {
			LOGGER.info("Generating data");
			new DataGenerationRunner(conf).run();
		} else {
			LOGGER.error("No runner option set");
		}
	}
	
	static {
		LoggingUtils.initialize(
			new LoggerConfiguration("net.luis.generation").addDefaultLogger(LoggingType.FILE, Level.DEBUG)
		);
		LoggingUtils.disable(LoggingType.FILE, Level.DEBUG);
		LOGGER = LogManager.getLogger();
	}
}
