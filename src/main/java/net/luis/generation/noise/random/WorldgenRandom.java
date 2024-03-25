package net.luis.generation.noise.random;

import com.mojang.serialization.Codec;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class WorldgenRandom extends Random implements RandomSource {
	
	private final RandomSource rng;
	private int count;
	
	public WorldgenRandom(long seed) {
		this(new LegacyRandomSource(seed));
	}
	
	public WorldgenRandom(@NotNull RandomSource rng) {
		super(0L);
		this.rng = Objects.requireNonNull(rng, "Random source must not be null");
	}
	
	public int getCount() {
		return this.count;
	}
	
	public @NotNull RandomSource fork() {
		return this.rng.fork();
	}
	
	public @NotNull PositionalRandomFactory forkPositional() {
		return this.rng.forkPositional();
	}
	
	public int next(int next) {
		++this.count;
		if (this.rng instanceof LegacyRandomSource rng) {
			return rng.next(next);
		} else {
			return (int) (this.rng.nextLong() >>> 64 - next);
		}
	}
	
	public synchronized void setSeed(long seed) {
		if (this.rng != null) {
			this.rng.setSeed(seed);
		}
	}
}