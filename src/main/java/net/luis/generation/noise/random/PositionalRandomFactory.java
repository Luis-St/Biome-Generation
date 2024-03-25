package net.luis.generation.noise.random;

import org.jetbrains.annotations.NotNull;

public interface PositionalRandomFactory {
	
	@NotNull RandomSource fromHashOf(@NotNull String string);
}