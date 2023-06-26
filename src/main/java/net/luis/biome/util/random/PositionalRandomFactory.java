package net.luis.biome.util.random;

public interface PositionalRandomFactory {
	
	RandomSource fromHashOf(String string);
	
	RandomSource at(int x, int y, int z);

}