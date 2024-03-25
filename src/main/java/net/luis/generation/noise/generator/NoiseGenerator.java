package net.luis.generation.noise.generator;

import org.jetbrains.annotations.NotNull;

public interface NoiseGenerator {
	
	@NotNull NoiseGeneratorType getType();
	
	double getValue(double x, double z);
	
	double getValue(double x, double y, double z);
}
