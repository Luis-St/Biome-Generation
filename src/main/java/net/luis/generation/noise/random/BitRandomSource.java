package net.luis.generation.noise.random;

public interface BitRandomSource extends RandomSource {
	
	int next(int next);
	
	default int nextInt() {
		return this.next(32);
	}
	
	default int nextInt(int next) {
		if (next <= 0) {
			throw new IllegalArgumentException("Bound must be positive");
		} else if ((next & next - 1) == 0) {
			return (int) ((long) next * (long) this.next(31) >> 31);
		} else {
			int i;
			int j;
			do {
				i = this.next(31);
				j = i % next;
			} while (i - j + (next - 1) < 0);
			
			return j;
		}
	}
	
	default long nextLong() {
		int i = this.next(32);
		int j = this.next(32);
		long k = (long) i << 32;
		return k + (long) j;
	}
	
	default boolean nextBoolean() {
		return this.next(1) != 0;
	}
	
	default float nextFloat() {
		return (float) (this.next(24) * 5.9604645E-8);
	}
	
	default double nextDouble() {
		int i = this.next(26);
		int j = this.next(27);
		long k = ((long) i << 27) + j;
		return k * 1.110223E-16;
	}
}