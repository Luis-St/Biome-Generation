package net.luis.biome;

import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class BiomeRectangle extends Rectangle {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	public BiomeRectangle(BiomeColor color, int x, int z) {
		this(Optional.empty(), color, x, z);
	}
	
	public BiomeRectangle(Biome biome, int x, int z) {
		this(Optional.of(biome), biome.color(), x, z);
	}
	
	private BiomeRectangle(Optional<Biome> optional, BiomeColor color, int x, int z) {
		super(x, z, 1, 1);
		this.setFill(color.toFx());
		this.setOnMouseClicked((event) -> {
			optional.ifPresent((biome) -> {
				LOGGER.info("Biome at ({}|{}) is {}", x, z, biome.name());
			});
		});
	}
}
