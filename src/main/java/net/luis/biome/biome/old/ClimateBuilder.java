package net.luis.biome.biome.old;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Maps;

import net.luis.biome.biome.Biome;
import net.luis.biome.biome.Biomes;
import net.luis.biome.biome.old.Climate.Parameter;

@Deprecated
public class ClimateBuilder {
	
	public static final ClimateBuilder INSTANCE = new ClimateBuilder();
	
	protected final Map<Biome, Climate> biomeClimates;
	
	ClimateBuilder() {
		this.biomeClimates = Maps.newHashMap();
		this.register();
	}
	
	public Set<Entry<Biome, Climate>> getBiomeClimates() {
		return this.biomeClimates.entrySet();
	}
	
	/**
	 * temperature: 
	 *  - hot (2.0)
	 *  - warm (1.0)
	 *  - temperate (0.0)
	 *  - cold (-1.0)
	 *  - frozen (-2.0)
	 */
	
	/**
	 * humidity: 
	 *  - very intensiv rain (2.0)
	 *  - intensiv rain (1.2)
	 *  - moderate rain (0.4)
	 *  - light rain (-0.4)
	 *  - drizzling rain (-1.2)
	 *  - no rain (-2.0;
	 */
	
	/**
	 * continentalness: 
	 *  - far inland (2.0)
	 *  - mid inland (1.5)
	 *  - inland (1.0)
	 *  - near inland (0.5)
	 *  - coast (0.0)
	 *  - shallow ocean (-0.5)
	 *  - ocean (-1.0)
	 *  - deep ocean (-1.5)
	 *  - ocean digging (-2.0)
	 */
	
	/**
	 * erosion: 
	 *  - mountain peak (2.0)
	 *  - mountain (1.5)
	 *  - highland (1.0)
	 *  - default level (0.5)
	 *  - sea level (0.0)
	 *  - shallow ocean floor (-0.5)
	 *  - ocean floor (-1.0)
	 *  - deep coean floor (-1.5)
	 *  - deepest ocean floor (-2.0)
	 */
	
	protected final Parameter FULL_RANGE = Parameter.span(-2.0, 2.0);
	
	protected final Parameter FOREST_CONTINENTALNESS = Parameter.span(0.4, 1.5);
	protected final Parameter FOREST_EROSION = Parameter.span(0.75, 1.6);
	
	protected final Parameter JUNGLE_TEMPERATURE = Parameter.span(1.3, 2.0);
	protected final Parameter JUNGLE_HUMIDITY = Parameter.span(1.0, 2.0);
	protected final Parameter JUNGLE_EROSION = Parameter.span(0.2, 0.75);
	
	protected final Parameter SAVANNA_TEMPERATURE = Parameter.span(0.3, 0.9);
	protected final Parameter SAVANNA_HUMIDITY = Parameter.span(0.0, 0.9);
	protected final Parameter SAVANNA_CONTINENTALNESS = Parameter.span(0.2, 0.8);
	
	protected final Parameter BADLANDS_TEMPERATURE = Parameter.span(1.6, 2.0);
	protected final Parameter BADLANDS_HUMIDITY = Parameter.span(-2.0, -1.6);
	protected final Parameter BADLANDS_CONTINENTALNESS = Parameter.span(0.1, 1.2);
	
	protected final Parameter PEAK_CONTINENTALNESS = Parameter.span(1.5, 2.0);
	
	protected final Parameter BEACH_CONTINENTALNESS = Parameter.span(-0.1, 0.1);
	
	protected final Parameter OCEAN_CONTINENTALNESS = Parameter.span(-1.0, -0.1);
	protected final Parameter DEEP_OCEAN_CONTINENTALNESS = Parameter.span(-2.0, -1.0);
	protected final Parameter OCEAN_EROSION = Parameter.span(-1.0, 0.0);
	protected final Parameter DEEP_OCEAN_EROSION = Parameter.span(-2.0, -1.0);
	
	protected void register() {
		// plain biomes
		this.biomeClimates.put(Biomes.PLAINS, new Climate(Parameter.span(-0.7, 0.7), Parameter.span(-1.2, 0.0), Parameter.span(0.1, 0.9), Parameter.span(0.2, 1.0)));
		this.biomeClimates.put(Biomes.SUNFLOWER_PLAINS, new Climate(Parameter.span(0.1, 0.6), Parameter.span(-0.9, -0.0), Parameter.span(0.25, 0.75), Parameter.span(0.3, 0.65)));
		this.biomeClimates.put(Biomes.SNOWY_PLAINS, new Climate(Parameter.span(-1.25, -0.75), Parameter.span(-1.2, -0.4), Parameter.span(0.25, 0.75), Parameter.span(0.2, 0.75)));
		this.biomeClimates.put(Biomes.ICE_SPIKES, new Climate(Parameter.span(-1.5, -0.95), Parameter.span(-2.0, -1.2), Parameter.span(0.5, 1.0), Parameter.span(0.3, 0.8)));
		
		// desert biomes
		this.biomeClimates.put(Biomes.DESERT, new Climate(Parameter.span(1.5, 2.0), Parameter.span(-2.0, -1.8), Parameter.span(0.1, 0.6), Parameter.span(0.15, 0.4)));
		
		// swamp biomes
		this.biomeClimates.put(Biomes.SWAMP, new Climate(Parameter.span(1.4, 1.9), Parameter.span(1.0, 1.5), Parameter.span(1.2, 1.6), Parameter.span(0.1, 0.3)));
		
		// forest biomes
		this.biomeClimates.put(Biomes.FOREST, new Climate(Parameter.span(-0.3, 0.3), Parameter.span(-1.5, -0.8), this.FOREST_CONTINENTALNESS, this.FOREST_EROSION));
		this.biomeClimates.put(Biomes.FLOWER_FOREST, new Climate(Parameter.span(0.1, 0.6), Parameter.span(-0.9, 0.0), this.FOREST_CONTINENTALNESS, this.FOREST_EROSION));
		this.biomeClimates.put(Biomes.BIRCH_FOREST, new Climate(Parameter.span(-0.3, 0.3), Parameter.span(-1.5, -0.8), this.FOREST_CONTINENTALNESS, this.FOREST_EROSION));
		this.biomeClimates.put(Biomes.DARK_FOREST, new Climate(Parameter.span(0.3, 0.75), Parameter.span(0.5, 1.0), this.FOREST_CONTINENTALNESS, Parameter.span(0.5, 0.8)));
		this.biomeClimates.put(Biomes.OLD_GROWTH_BIRCH_FOREST, new Climate(Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0)));
		this.biomeClimates.put(Biomes.OLD_GROWTH_PINE_TAIGA, new Climate(Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0)));
		this.biomeClimates.put(Biomes.OLD_GROWTH_SPRUCE_TAIGA, new Climate(Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0)));
		
		// taiga biomes
		this.biomeClimates.put(Biomes.TAIGA, new Climate(Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0)));
		this.biomeClimates.put(Biomes.SNOWY_TAIGA, new Climate(Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0)));
		
		// savanna biomes
		this.biomeClimates.put(Biomes.SAVANNA, new Climate(this.SAVANNA_TEMPERATURE, this.SAVANNA_HUMIDITY, this.SAVANNA_CONTINENTALNESS, Parameter.span(0.2, 0.75)));
		this.biomeClimates.put(Biomes.SAVANNA_PLATEAU, new Climate(this.SAVANNA_TEMPERATURE, this.SAVANNA_HUMIDITY, this.SAVANNA_CONTINENTALNESS, Parameter.span(0.75, 1.4)));
		
		// windswept hills biomes
		this.biomeClimates.put(Biomes.WINDSWEPT_HILLS, new Climate(Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0)));
		this.biomeClimates.put(Biomes.WINDSWEPT_GRAVELLY_HILLS, new Climate(Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0)));
		this.biomeClimates.put(Biomes.WINDSWEPT_FOREST, new Climate(Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0)));
		
		// jungle biomes
		this.biomeClimates.put(Biomes.JUNGLE, new Climate(this.JUNGLE_TEMPERATURE, this.JUNGLE_HUMIDITY, Parameter.span(0.6, 1.0), this.JUNGLE_EROSION));
		this.biomeClimates.put(Biomes.SPARSE_JUNGLE, new Climate(this.JUNGLE_TEMPERATURE, this.JUNGLE_HUMIDITY, Parameter.span(0.3, 0.6), this.JUNGLE_EROSION));
		this.biomeClimates.put(Biomes.BAMBOO_JUNGLE, new Climate(this.JUNGLE_TEMPERATURE, this.JUNGLE_HUMIDITY, Parameter.span(1.0, 1.3), this.JUNGLE_EROSION));
		
		// badlands biomes
		this.biomeClimates.put(Biomes.BADLANDS, new Climate(this.BADLANDS_TEMPERATURE, this.BADLANDS_HUMIDITY, this.BADLANDS_CONTINENTALNESS, Parameter.span(0.2, 0.6)));
		this.biomeClimates.put(Biomes.ERODED_BADLANDS, new Climate(this.BADLANDS_TEMPERATURE, this.BADLANDS_HUMIDITY, this.BADLANDS_CONTINENTALNESS, Parameter.span(0.6, 1.0)));
		this.biomeClimates.put(Biomes.WOODED_BADLANDS, new Climate(this.BADLANDS_TEMPERATURE, this.BADLANDS_HUMIDITY, this.BADLANDS_CONTINENTALNESS, Parameter.span(1.0, 1.5)));
		
		// mountain biomes
		this.biomeClimates.put(Biomes.MEADOW, new Climate(Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0)));
		this.biomeClimates.put(Biomes.GROVE, new Climate(Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0)));
		
		// peak biomes
		this.biomeClimates.put(Biomes.SNOWY_SLOPES, new Climate(Parameter.span(-2.0, -0.5), Parameter.span(0.9, 1.5), this.PEAK_CONTINENTALNESS, Parameter.span(1.75, 2.0)));
		this.biomeClimates.put(Biomes.FROZEN_PEAKS, new Climate(Parameter.span(-2.0, -0.5), Parameter.span(0.1, 0.7), this.PEAK_CONTINENTALNESS, Parameter.span(1.5, 1.75)));
		this.biomeClimates.put(Biomes.JAGGED_PEAKS, new Climate(Parameter.span(-2.0, -0.5), Parameter.span(-1.5, -0.9), this.PEAK_CONTINENTALNESS, Parameter.span(1.75, 2.0)));
		this.biomeClimates.put(Biomes.STONY_PEAKS, new Climate(Parameter.span(-0.5, 2.0), Parameter.span(-2.0, -1.7), this.PEAK_CONTINENTALNESS, Parameter.span(1.75, 2.0)));
		
		// beach Biomes
		this.biomeClimates.put(Biomes.BEACH, new Climate(Parameter.span(-0.3, 2.0), this.FULL_RANGE, this.BEACH_CONTINENTALNESS, Parameter.span(-2.0, 0.75)));
		this.biomeClimates.put(Biomes.SNOWY_BEACH, new Climate(Parameter.span(-2.0, -0.3), this.FULL_RANGE, this.BEACH_CONTINENTALNESS, Parameter.span(-2.0, 0.75)));
		this.biomeClimates.put(Biomes.STONY_SHORE, new Climate(this.FULL_RANGE, this.FULL_RANGE, this.BEACH_CONTINENTALNESS, Parameter.span(0.75, 2.0)));
		
		// ocean biomes
		this.biomeClimates.put(Biomes.WARM_OCEAN, new Climate(Parameter.span(1.25, 2.0), this.FULL_RANGE, this.OCEAN_CONTINENTALNESS, this.OCEAN_EROSION));
		this.biomeClimates.put(Biomes.LUKEWARM_OCEAN, new Climate(Parameter.span(0.5, 1.25), this.FULL_RANGE, this.OCEAN_CONTINENTALNESS, this.OCEAN_EROSION));
		this.biomeClimates.put(Biomes.DEEP_LUKEWARM_OCEAN, new Climate(Parameter.span(0.5, 1.25), this.FULL_RANGE, this.DEEP_OCEAN_CONTINENTALNESS, this.DEEP_OCEAN_EROSION));
		this.biomeClimates.put(Biomes.OCEAN, new Climate(Parameter.span(-0.5, 0.5), this.FULL_RANGE, this.OCEAN_CONTINENTALNESS, this.OCEAN_EROSION));
		this.biomeClimates.put(Biomes.DEEP_OCEAN, new Climate(Parameter.span(-0.5, 0.5), this.FULL_RANGE, this.DEEP_OCEAN_CONTINENTALNESS, this.DEEP_OCEAN_EROSION));
		this.biomeClimates.put(Biomes.COLD_OCEAN, new Climate(Parameter.span(-1.25, -0.5), this.FULL_RANGE, this.OCEAN_CONTINENTALNESS, this.OCEAN_EROSION));
		this.biomeClimates.put(Biomes.DEEP_COLD_OCEAN, new Climate(Parameter.span(-1.25, -0.5), this.FULL_RANGE, this.DEEP_OCEAN_CONTINENTALNESS, this.DEEP_OCEAN_EROSION));
		this.biomeClimates.put(Biomes.FROZEN_OCEAN, new Climate(Parameter.span(-2.0, -1.25), this.FULL_RANGE, this.OCEAN_CONTINENTALNESS, this.OCEAN_EROSION));
		this.biomeClimates.put(Biomes.DEEP_FROZEN_OCEAN, new Climate(Parameter.span(-2.0, -1.25), this.FULL_RANGE, this.DEEP_OCEAN_CONTINENTALNESS, this.DEEP_OCEAN_EROSION));
		
		// island biomes
		this.biomeClimates.put(Biomes.MUSHROOM_FIELDS, new Climate(Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0), Parameter.span(0.0, 0.0)));
	}
}
