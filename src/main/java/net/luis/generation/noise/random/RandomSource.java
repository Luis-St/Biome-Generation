package net.luis.generation.noise.random;

import org.jetbrains.annotations.NotNull;

public interface RandomSource {
	
	@NotNull RandomSource fork();
	
	@NotNull PositionalRandomFactory forkPositional();
	
	void setSeed(long seed);
	
	int nextInt();
	
	int nextInt(int next);
	
	long nextLong();
	
	double nextDouble();
	
	default void consumeCount(int count) {
		for (int i = 0; i < count; ++i) {
			this.nextInt();
		}
	}
}