package net.luis.run;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import net.luis.biome.BiomePlacer;
import net.luis.biome.BiomeRectangle;
import net.luis.noise.Noises;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Luis-st
 *
 */

public class DebugRun extends Application {
	
	public static final Logger LOGGER = LogManager.getLogger();
	protected int x;
	protected int z;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void init() throws Exception {
		this.x = 1000;
		this.z = 1000;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Group group = new Group();
		for (int x = 0; x <= this.x; x++) {
			for (int z = 0; z <= this.z; z++) {
				Rectangle rectangle = new BiomeRectangle(BiomePlacer.INSTANCE.getBiome(Noises.CONTINENTALNESS.getValue(x, z), Noises.EROSION.getValue(x, z), Noises.TEMPERATURE.getValue(x, z), Noises.HUMIDITY.getValue(x, z) + 1.0), x, z);
				group.getChildren().add(rectangle);
			}
		}
		stage.setScene(new Scene(group, this.x, this.z));
		stage.setTitle("Biome NoiseGenerator");
		stage.show();
	}
}
