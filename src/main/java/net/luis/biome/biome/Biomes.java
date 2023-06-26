package net.luis.biome.biome;

import java.util.List;

import com.google.common.collect.Lists;

public class Biomes {
	
	
	public static final List<Biome> BIOMES = Lists.newArrayList(); // 87
	
	// fallback biomes
	public static final Biome VOID = register(new Biome("void", new BiomeColor(0, 0, 0)));
	
	// plain biome
	public static final Biome PLAINS = register(new Biome("plains", new BiomeColor(0, 124, 0)));
	public static final Biome SUNFLOWER_PLAINS = register(new Biome("sunflower_plains", new BiomeColor(255, 0, 255)));
	public static final Biome SNOWY_PLAINS = register(new Biome("snowy_plains", new BiomeColor(229, 225, 219)));
	public static final Biome ICE_SPIKES = register(new Biome("ice_spikes", new BiomeColor(191, 209, 255)));
	public static final Biome STONY_PLAINS = register(new Biome("stony_plains", new BiomeColor(165, 228, 165)));
	
	// desert biomes
	public static final Biome DESERT = register(new Biome("desert", new BiomeColor(255, 255, 0)));
	public static final Biome DESERT_OASIS = register(new Biome("desert_oasis", new BiomeColor(210, 255, 0)));
	
	// swamp biomes
	public static final Biome SWAMP = register(new Biome("swamp", new BiomeColor(89, 101, 55)));
	public static final Biome SPARSE_SWAMP = register(new Biome("sparse_swamp", new BiomeColor(89, 85, 55)));
	public static final Biome MANGROVE_SWAMP = register(new Biome("mangrove_swamp", new BiomeColor(128, 101, 55)));
	
	// forest biomes
	public static final Biome FOREST = register(new Biome("forest", new BiomeColor(28, 132, 0)));
	public static final Biome SPARSE_FOREST = register(new Biome("sparse_forest", new BiomeColor(93, 130, 83)));
	public static final Biome FLOWER_FOREST = register(new Biome("flower_forest", new BiomeColor(130, 0, 74)));
	public static final Biome SPARSE_FLOWER_FOREST = register(new Biome("sparse_flower_forest", new BiomeColor(127, 81, 98)));
	public static final Biome BIRCH_FOREST = register(new Biome("birch_forest", new BiomeColor(14, 70, 0)));
	public static final Biome SPARSE_BIRCH_FOREST = register(new Biome("sparse_birch_forest", new BiomeColor(49, 68, 44)));
	public static final Biome DARK_FOREST = register(new Biome("dark_forest", new BiomeColor(14, 56, 0)));
	public static final Biome SPARSE_DARK_FOREST = register(new Biome("sparse_dark_forest", new BiomeColor(39, 53, 34)));
	public static final Biome OLD_GROWTH_BIRCH_FOREST = register(new Biome("old_growth_birch_forest", new BiomeColor(45, 39, 0)));
	public static final Biome SPARSE_OLD_GROWTH_BIRCH_FOREST = register(new Biome("sparse_old_growth_birch_forest", new BiomeColor(43, 41, 27)));
	public static final Biome OLD_GROWTH_PINE_TAIGA = register(new Biome("old_growth_pine_taiga", new BiomeColor(4, 39, 0)));
	public static final Biome SPARSE_OLD_GROWTH_PINE_TAIGA = register(new Biome("sparse_old_growth_pine_taiga", new BiomeColor(26, 38, 24)));
	public static final Biome OLD_GROWTH_SPRUCE_TAIGA = register(new Biome("old_growth_spruce_taiga", new BiomeColor(45, 25, 0)));
	public static final Biome SPARSE_OLD_GROWTH_SPRUCE_TAIGA = register(new Biome("sparse_old_growth_spruce_taiga", new BiomeColor(43, 36, 27)));
	
	// taiga biomes
	public static final Biome TAIGA = register(new Biome("taiga", new BiomeColor(4, 39, 28)));
	public static final Biome SNOWY_TAIGA = register(new Biome("snowy_taiga", new BiomeColor(21, 193, 136)));
	public static final Biome SPARSE_TAIGA = register(new Biome("sparse_taiga", new BiomeColor(255, 0, 255)));
	public static final Biome SPARSE_SNOWY_TAIGA = register(new Biome("sparse_snowy_taiga", new BiomeColor(255, 0, 124)));
	
	// savanna biomes
	public static final Biome SAVANNA = register(new Biome("savanna", new BiomeColor(255, 78, 0)));
	public static final Biome SAVANNA_PLATEAU = register(new Biome("savanna_plateau", new BiomeColor(255, 151, 0)));
	public static final Biome SPARSE_SAVANNA = register(new Biome("sparse_savanna", new BiomeColor(255, 163, 163)));
	public static final Biome WOODED_SAVANNA_PLATEAU = register(new Biome("wooded_savanna_plateau", new BiomeColor(128, 66, 0)));	
	public static final Biome OLD_GROWTH_SAVANNA = register(new Biome("old_growth_savanna", new BiomeColor(255, 101, 105)));
	
	// windswept hills biomes
	public static final Biome WINDSWEPT_HILLS = register(new Biome("windswept_hills", new BiomeColor(105, 105, 105)));
	public static final Biome WINDSWEPT_GRAVELLY_HILLS = register(new Biome("windswept_gravelly_hills", new BiomeColor(65, 65, 65)));
	public static final Biome WINDSWEPT_FOREST = register(new Biome("windswept_forest", new BiomeColor(45, 0, 37)));
	
	// jungle biomes
	public static final Biome JUNGLE = register(new Biome("jungle", new BiomeColor(0, 255, 0)));
	public static final Biome SPARSE_JUNGLE = register(new Biome("sparse_jungle", new BiomeColor(35, 209, 24)));
	public static final Biome BAMBOO_JUNGLE = register(new Biome("bamboo_jungle", new BiomeColor(120, 174, 0)));
	public static final Biome TROPICAL_JUNGLE = register(new Biome("tropical_jungle", new BiomeColor(170, 205, 24)));
	public static final Biome SPARSE_TROPICAL_JUNGLE = register(new Biome("sparse_tropical_jungle", new BiomeColor(140, 151, 0)));
	
	// badlands biomes
	public static final Biome BADLANDS = register(new Biome("badlands", new BiomeColor(163, 128, 106)));
	public static final Biome ERODED_BADLANDS = register(new Biome("eroded_badlands", new BiomeColor(163, 128, 255)));
	public static final Biome WOODED_BADLANDS = register(new Biome("wooded_badlands", new BiomeColor(105, 70, 106)));
	
	// mountain biomes
	public static final Biome MEADOW = register(new Biome("meadow", new BiomeColor(255, 105, 255)));
	public static final Biome GROVE = register(new Biome("grove", new BiomeColor(255, 182, 255)));
	
	// peak biomes
	public static final Biome SNOWY_SLOPES = register(new Biome("snowy_slopes", new BiomeColor(147, 255, 255)));
	public static final Biome FROZEN_PEAKS = register(new Biome("frozen_peaks", new BiomeColor(221, 255, 255)));
	public static final Biome JAGGED_PEAKS = register(new Biome("jagged_peaks", new BiomeColor(220, 220, 220)));
	public static final Biome STONY_PEAKS = register(new Biome("stony_peaks", new BiomeColor(192, 192, 192)));
	public static final Biome WINDY_PEAKS = register(new Biome("windy_peaks", new BiomeColor(255, 255, 255)));
	public static final Biome VOLCANO_PEAKS = register(new Biome("volcano_peaks", new BiomeColor(78, 0, 0)));
	
	// river biomes
	public static final Biome RIVER = register(new Biome("river", new BiomeColor(167, 178, 255)));
	public static final Biome FROZEN_RIVER = register(new Biome("frozen_river", new BiomeColor(214, 219, 255)));
	public static final Biome TROPICAL_RIVER = register(new Biome("tropical_river", new BiomeColor(167, 178, 143)));
	
	// beach Biomes
	public static final Biome BEACH = register(new Biome("beach", new BiomeColor(255, 248, 167)));
	public static final Biome SNOWY_BEACH = register(new Biome("snowy_beach", new BiomeColor(255, 251, 214)));
	public static final Biome STONY_SHORE = register(new Biome("stony_shore", new BiomeColor(255, 221, 198)));
	public static final Biome TROPICAL_BEACH = register(new Biome("tropical_beach", new BiomeColor(255, 166, 94)));
	
	// ocean biomes
	public static final Biome TROPICAL_OCEAN_DIGGING = register(new Biome("tropical_ocean_digging", new BiomeColor(0, 102, 102)));
	public static final Biome DEEP_TROPICAL_OCEAN = register(new Biome("deep_tropical_ocean", new BiomeColor(0, 153, 153)));
	public static final Biome TROPICAL_OCEAN = register(new Biome("tropical_ocean", new BiomeColor(0, 204, 204)));
	public static final Biome TROPICAL_SHALLOW_OCEAN = register(new Biome("tropical_shallow_ocean", new BiomeColor(0, 255, 255)));
	
	public static final Biome WARM_OCEAN_DIGGING = register(new Biome("warm_ocean_digging", new BiomeColor(0, 81, 102)));
	public static final Biome DEEP_WARM_OCEAN = register(new Biome("deep_warm_ocean", new BiomeColor(0, 122, 153)));
	public static final Biome WARM_OCEAN = register(new Biome("warm_ocean", new BiomeColor(0, 163, 204)));
	public static final Biome WARM_SHALLOW_OCEAN = register(new Biome("warm_shallow_ocean", new BiomeColor(0, 200, 255)));
	
	public static final Biome LUKEWARM_OCEAN_DIGGING = register(new Biome("lukewarm_ocean_digging", new BiomeColor(0, 61, 102)));
	public static final Biome DEEP_LUKEWARM_OCEAN = register(new Biome("deep_lukewarm_ocean", new BiomeColor(0, 91, 153)));
	public static final Biome LUKEWARM_OCEAN = register(new Biome("lukewarm_ocean", new BiomeColor(0, 122, 204)));
	public static final Biome LUKEWARM_SHALLOW_OCEAN = register(new Biome("lukewarm_shallow_ocean", new BiomeColor(0, 150, 255)));
	
	public static final Biome OCEAN_DIGGING = register(new Biome("ocean_digging", new BiomeColor(0, 20, 51)));
	public static final Biome DEEP_OCEAN = register(new Biome("deep_ocean", new BiomeColor(0, 400, 102)));
	public static final Biome OCEAN = register(new Biome("ocean", new BiomeColor(0, 61, 153)));
	public static final Biome SHALLOW_OCEAN = register(new Biome("shallow_ocean", new BiomeColor(0, 100, 255)));
	
	public static final Biome COLD_OCEAN_DIGGING = register(new Biome("cold_ocean_digging", new BiomeColor(0, 10, 51)));
	public static final Biome DEEP_COLD_OCEAN = register(new Biome("deep_cold_ocean", new BiomeColor(0, 20, 102)));
	public static final Biome COLD_OCEAN = register(new Biome("cold_ocean", new BiomeColor(0, 30, 153)));
	public static final Biome COLD_SHALLOW_OCEAN = register(new Biome("cold_shallow_ocean", new BiomeColor(0, 50, 255)));
	
	public static final Biome FROZEN_OCEAN_DIGGING = register(new Biome("frozen_ocean_digging", new BiomeColor(0, 0, 51)));
	public static final Biome DEEP_FROZEN_OCEAN = register(new Biome("deep_frozenocean", new BiomeColor(0, 1, 102)));
	public static final Biome FROZEN_OCEAN = register(new Biome("frozen_ocean", new BiomeColor(0, 2, 153)));
	public static final Biome FROZEN_SHALLOW_OCEAN = register(new Biome("frozen_shallow_ocean", new BiomeColor(0, 0, 255)));
	
	public static final Biome KELP_FOREST = register(new Biome("kelp_forest", new BiomeColor(78, 106, 135)));
	public static final Biome CORAL_FOREST = register(new Biome("coral_forest", new BiomeColor(147, 201, 255)));
	
	// island biomes
	public static final Biome MUSHROOM_FIELDS = register(new Biome("mushroom_fields", new BiomeColor(87, 0, 127)));
	
	public static Biome register(Biome biome) {
		BIOMES.add(biome);
		return biome;
	}
}
