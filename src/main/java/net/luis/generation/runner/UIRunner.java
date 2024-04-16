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
	public void start(@NotNull Stage stage) {
		MenuButton operation = new MenuButton("Select Operation");
		
		Menu mathematical = new Menu("Mathematical");
		MenuItem addition = new MenuItem("Addition");
		MenuItem subtraction = new MenuItem("Subtraction");
		MenuItem multiplication = new MenuItem("Multiplication");
		MenuItem division = new MenuItem("Division");
		mathematical.getItems().addAll(addition, subtraction, multiplication, division);
		
		Menu interpolation = new Menu("Interpolation");
		
		Menu positive = new Menu("Positive");
		MenuItem positiveLinear = new MenuItem("Linear");
		MenuItem positiveQuadratic = new MenuItem("Quadratic");
		MenuItem positiveCubic = new MenuItem("Cubic");
		positive.getItems().addAll(positiveLinear, positiveQuadratic, positiveCubic);
		
		Menu negative = new Menu("Negative");
		MenuItem negativeLinear = new MenuItem("Linear");
		MenuItem negativeQuadratic = new MenuItem("Quadratic");
		MenuItem negativeCubic = new MenuItem("Cubic");
		negative.getItems().addAll(negativeLinear, negativeQuadratic, negativeCubic);
		
		interpolation.getItems().addAll(positive, negative);
		
		operation.getItems().addAll(mathematical, interpolation);
		
		
		
		HBox box = FxUtils.makeDefaultHBox(operation);
		
		stage.setScene(new Scene(box, this.config.width(), this.config.height()));
		stage.show();
	}
}
