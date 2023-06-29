package net.luis.util.random;

public interface PositionalRandomFactory {
	
	RandomSource fromHashOf(String string);
}