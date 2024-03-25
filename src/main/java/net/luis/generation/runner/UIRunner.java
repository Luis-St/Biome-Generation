package net.luis.generation.runner;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.luis.fxutils.launcher.FxApplication;
import net.luis.fxutils.util.FxUtils;
import net.luis.generation.config.Config;
import net.luis.generation.config.UiConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class UIRunner implements FxApplication {
	
	private final UiConfig config;
	
	public UIRunner(@NotNull Config config) {
		this.config = Objects.requireNonNull(config, "Config must not be null").ui();
	}
	
	@Override
	public void start(@NotNull Stage stage) {}
}
