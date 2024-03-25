package net.luis.run;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import net.luis.fxutils.helper.EventHandlers;
import net.luis.ui.Fonts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class FxRun extends Application {
	
	public static final Logger LOGGER = LogManager.getLogger();
	private int width;
	private int height;
	/*	protected double scale;*/
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void init() {
		this.width = 1000;
		this.height = 1000;
	}
	
	@Override
	public void start(@NotNull Stage stage) {
		double gridSize = 100.0;
		stage.setScene(this.makeUI(gridSize));
		stage.setTitle("Biome NoiseGenerator");
		stage.show();
	}
	
	private @NotNull Scene makeUI(double gridSize) {
		int x = (int) Math.ceil(this.width / gridSize);
		int y = (int) Math.ceil(this.height / gridSize);
		StackPane pane = new StackPane();
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().add(this.makeGrid(x, y, gridSize));
		pane.getChildren().add(this.makeUserInteractions());
		return new Scene(pane, this.width, this.height);
	}
	
	private @NotNull Pane makeUserInteractions() {
		BorderPane pane = new BorderPane();
		Button btn = new Button("\uf0c9");
		btn.setFont(Font.font(Fonts.FA_SOLID.getFamily(), 25));
		btn.setBackground(null);
		btn.setOnAction(EventHandlers.create(() -> {
			System.out.println("Hello World!");
		}));
		pane.setTop(btn);
		return pane;
	}
	
	private @NotNull GridPane makeGrid(int x, int y, double size) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		for (int i = 0; i < x; i++) {
			for (int j = 1; j < y * 2; j++) {
				Rectangle rectangle;
				if (j % 2 == 0) {
					rectangle = new Rectangle(size, 2);
					rectangle.setFill(Color.BLACK);
				} else {
					rectangle = new Rectangle(size, size);
					rectangle.setFill(Color.GREENYELLOW);
				}
				grid.add(rectangle, i, j);
			}
			if (i == 0) {
				continue;
			}
			for (int j = 1; j < y * 2 + 1; j += 2) {
				Rectangle rectangle = new Rectangle(2, size);
				rectangle.setFill(Color.BLACK);
				grid.add(rectangle, i, j);
			}
		}
		return grid;
	}
	
	/*	@Override
	public void init() throws Exception {
		this.x = 1000;
		this.z = 1000;
		this.scale = 0.0625;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Group group = new Group();
		for (int x = 0; x <= this.x; x++) {
			for (int z = 0; z <= this.z; z++) {
				Rectangle rectangle = new BiomeRectangle(BiomePlacer.INSTANCE.getBiome(this.continental(x, z), this.erosion(x, z), this.temperature(x, z), this.humidity(x, z) + 1.0), x, z);
				group.getChildren().add(rectangle);
			}
		}
		stage.setScene(new Scene(group, this.x, this.z));
		stage.setTitle("Biome NoiseGenerator");
		stage.show();
	}*/

/*	protected Climate.Target target(int x, int z) {
		return Climate.Target.of(Noises.TEMPERATURE.getValue(x * this.scale, z * this.scale), Noises.HUMIDITY.getValue(x * 0.0625, z * 0.0625) * 2.5, Noises.CONTINENTALNESS.getValue(x * this.scale, z * this.scale) * 2.25,
			Noises.EROSION.getValue(x * this.scale, z * this.scale));
	}
	
	protected double temperature(double x, double z) {
		return this.shiftInBounds(Noises.TEMPERATURE, Noises.TEMPERATURE.getValue(x * this.scale, z * this.scale));
	}
	
	protected BiomeColor temperatureColor(double x, double z) {
		return NoiseColors.temperature(Noises.TEMPERATURE.getValue(x * this.scale, z * this.scale));
	}
	
	protected double humidity(double x, double z) {
		return this.shiftInBounds(Noises.HUMIDITY, Noises.HUMIDITY.getValue(x * 0.0625, z * 0.0625) * 2.5);
	}
	
	protected BiomeColor humidityColor(double x, double z) {
		return NoiseColors.humidity(Noises.HUMIDITY.getValue(x * 0.0625, z * 0.0625) * 2.5);
	}
	
	protected double continental(double x, double z) {
		return this.shiftInBounds(Noises.CONTINENTALNESS, Noises.CONTINENTALNESS.getValue(x * this.scale, z * this.scale) * 2.25);
	}
	
	protected BiomeColor continentalColor(double x, double z) {
		return NoiseColors.continental(Noises.CONTINENTALNESS.getValue(x * this.scale, z * this.scale) * 2.25);
	}
	
	protected double erosion(double x, double z) {
		return this.shiftInBounds(Noises.EROSION, Noises.EROSION.getValue(x * this.scale, z * this.scale));
	}
	
	protected BiomeColor erosionColor(double x, double z) {
		return NoiseColors.erosion(Noises.EROSION.getValue(x * this.scale, z * this.scale));
	}
	
	private double shiftInBounds(NormalNoiseGenerator noise, double value) {
		return (((value + noise.max()) / (noise.max() + noise.max())) * 2.0) - 1.0;
	}*/

//	protected Color river(double x, double z) {
//		NoiseGenerator riverNoise = NormalNoiseGenerator.create(new LegacyRandomSource(System.currentTimeMillis()), -2, 1.0, 1.0, 2.0, 2.0);
//		long noise = Math.round(riverNoise.getValue(x * this.scale, z * this.scale) * 10); // TEST: higher multiplier
//		if (noise == 1) {
//			return new Color(0.0, 1.0, 0.0, 1.0);
//		}
//		return noise == -1 ? new Color(0.0, 0.0, 1.0, 1.0) : new Color(1.0, 1.0, 0.0, 1.0);
//	}
	
	static {
		Fonts.initialize();
	}
}
