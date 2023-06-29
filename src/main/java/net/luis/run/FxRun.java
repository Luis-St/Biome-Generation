package net.luis.run;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import net.luis.biome.*;
import net.luis.noise.Noises;
import net.luis.noise.color.NoiseColors;
import net.luis.noise.generator.NormalNoise;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FxRun extends Application {
	
	public static final Logger LOGGER = LogManager.getLogger();
	protected int x;
	protected int z;
	protected double scale;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
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
		stage.setTitle("Biome Noise");
		stage.show();
	}

//	protected Climate.Target target(int x, int z) {
//		return Climate.Target.of(Noises.TEMPERATURE.getValue(x * this.scale, z * this.scale), Noises.HUMIDITY.getValue(x * 0.0625, z * 0.0625) * 2.5, Noises.CONTINENTALNESS.getValue(x * this.scale, z * this.scale) * 2.25,
//			Noises.EROSION.getValue(x * this.scale, z * this.scale));
//	}
	
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
	
	private double shiftInBounds(NormalNoise noise, double value) {
		return (((value + noise.max()) / (noise.max() + noise.max())) * 2.0) - 1.0;
	}

//	protected Color river(double x, double z) {
//		Noise riverNoise = NormalNoise.create(new LegacyRandomSource(System.currentTimeMillis()), -2, 1.0, 1.0, 2.0, 2.0);
//		long noise = Math.round(riverNoise.getValue(x * this.scale, z * this.scale) * 10); // TEST: higher multiplier
//		if (noise == 1) {
//			return new Color(0.0, 1.0, 0.0, 1.0);
//		}
//		return noise == -1 ? new Color(0.0, 0.0, 1.0, 1.0) : new Color(1.0, 1.0, 0.0, 1.0);
//	}
}
