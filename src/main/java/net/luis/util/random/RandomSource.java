package net.luis.util.random;

public interface RandomSource {
	
	RandomSource fork();
	
	PositionalRandomFactory forkPositional();
	
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