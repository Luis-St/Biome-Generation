package net.luis.generation.noise.random;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MarsagliaPolarGaussian {
	
	public final RandomSource randomSource;
	private double nextNextGaussian;
	private boolean haveNextNextGaussian;
	
	public MarsagliaPolarGaussian(@NotNull RandomSource rng) {
		this.randomSource = Objects.requireNonNull(rng, "Random source must not be null");
	}
	
	public void reset() {
		this.haveNextNextGaussian = false;
	}
	
	public double nextGaussian() {
		if (this.haveNextNextGaussian) {
			this.haveNextNextGaussian = false;
			return this.nextNextGaussian;
		} else {
			while (true) {
				double d0 = 2.0 * this.randomSource.nextDouble() - 1.0;
				double d1 = 2.0 * this.randomSource.nextDouble() - 1.0;
				double d2 = d0 * d0 + d0 * d0;
				if (d2 < 1.0) {
					if (d2 != 0.0) {
						double d3 = Math.sqrt(-2.0 * Math.log(d2) / d2);
						this.nextNextGaussian = d1 * d3;
						this.haveNextNextGaussian = true;
						return d0 * d3;
					}
				}
			}
		}
	}
}