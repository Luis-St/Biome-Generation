package net.luis.generation.noise.random;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class LegacyRandomSource implements BitRandomSource {
	
	private final AtomicLong seed = new AtomicLong();
	private final MarsagliaPolarGaussian gaussian = new MarsagliaPolarGaussian(this);
	
	public LegacyRandomSource(long seed) {
		this.setSeed(seed);
	}
	
	public @NotNull RandomSource fork() {
		return new LegacyRandomSource(this.nextLong());
	}
	
	public @NotNull PositionalRandomFactory forkPositional() {
		return new LegacyPositionalRandomFactory(this.nextLong());
	}
	
	public void setSeed(long seed) {
		if (this.seed.compareAndSet(this.seed.get(), (seed ^ 25214903917L) & 281474976710655L)) {
			this.gaussian.reset();
		} else {
			throw new RuntimeException();
		}
	}
	
	public int next(int value) {
		long i = this.seed.get();
		long j = i * 25214903917L + 11L & 281474976710655L;
		if (this.seed.compareAndSet(i, j)) {
			return (int) (j >> 48 - value);
		}
		throw new RuntimeException();
	}
	
	public static class LegacyPositionalRandomFactory implements PositionalRandomFactory {
		
		private final long seed;
		
		public LegacyPositionalRandomFactory(long seed) {
			this.seed = seed;
		}
		
		public @NotNull RandomSource fromHashOf(@NotNull String string) {
			Objects.requireNonNull(string, "Hash string must not be null");
			return new LegacyRandomSource((long) string.hashCode() ^ this.seed);
		}
		
		private long getSeed(int x, int y, int z) {
			long result = x * 3129871L ^ z * 116129781L ^ y;
			result = result * result * 42317861L + result * 11L;
			return result >> 16;
		}
	}
}