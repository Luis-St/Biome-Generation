package net.luis.biome.noise;

public interface Noise {
	
	double getValue(double x, double z);
	
	double getValue(double x, double y, double z);
}
