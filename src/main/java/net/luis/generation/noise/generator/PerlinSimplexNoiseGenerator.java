package net.luis.generation.noise.generator;

import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import net.luis.generation.noise.random.*;
import net.luis.utils.annotation.Ignored;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class PerlinSimplexNoiseGenerator implements NoiseGenerator {
	
	private final SimplexNoiseGenerator[] noiseLevels;
	private final double highestFreqValueFactor;
	private final double highestFreqInputFactor;
	
	public PerlinSimplexNoiseGenerator(@NotNull RandomSource source, @NotNull List<Integer> octaves) {
		this(source, new IntRBTreeSet(Objects.requireNonNull(octaves, "Octaves must not be null")));
	}
	
	private PerlinSimplexNoiseGenerator(@NotNull RandomSource source, @NotNull IntSortedSet octaves) {
		Objects.requireNonNull(source, "Source must not be null");
		Objects.requireNonNull(octaves, "Octaves must not be null");
		if (octaves.isEmpty()) {
			throw new IllegalArgumentException("Need some octaves!");
		} else {
			int first = -octaves.firstInt();
			int last = octaves.lastInt();
			int octaveCount = first + last + 1;
			if (octaveCount < 1) {
				throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
			} else {
				SimplexNoiseGenerator simplexNoise = new SimplexNoiseGenerator(source);
				this.noiseLevels = new SimplexNoiseGenerator[octaveCount];
				if (last >= 0 && last < octaveCount && octaves.contains(0)) {
					this.noiseLevels[last] = simplexNoise;
				}
				for (int i = last + 1; i < octaveCount; ++i) {
					if (i >= 0 && octaves.contains(last - i)) {
						this.noiseLevels[i] = new SimplexNoiseGenerator(source);
					} else {
						source.consumeCount(262);
					}
				}
				if (last > 0) {
					long noiseSeed = (long) (simplexNoise.getValue(simplexNoise.xo, simplexNoise.yo, simplexNoise.zo) * 9.223372E18);
					RandomSource rng = new WorldgenRandom(noiseSeed);
					for (int i = last - 1; i >= 0; --i) {
						if (i < octaveCount && octaves.contains(last - i)) {
							this.noiseLevels[i] = new SimplexNoiseGenerator(rng);
						} else {
							rng.consumeCount(262);
						}
					}
				}
				this.highestFreqInputFactor = Math.pow(2.0, last);
				this.highestFreqValueFactor = 1.0 / (Math.pow(2.0, octaveCount) - 1.0);
			}
		}
	}
	
	@Override
	public double getValue(double x, double z) {
		return this.getValue(x, z, true);
	}
	
	@Override
	public double getValue(double x, @Ignored.Always double y, double z) {
		return this.getValue(x, z, true);
	}
	
	public double getValue(double x, double z, boolean simplex) {
		double result = 0.0;
		double inputFactor = this.highestFreqInputFactor;
		double valueFactor = this.highestFreqValueFactor;
		for (SimplexNoiseGenerator simplexNoise : this.noiseLevels) {
			if (simplexNoise != null) {
				result += simplexNoise.getValue(x * inputFactor + (simplex ? simplexNoise.xo : 0.0), z * inputFactor + (simplex ? simplexNoise.yo : 0.0)) * valueFactor;
			}
			inputFactor /= 2.0;
			valueFactor *= 2.0;
		}
		return result;
	}
}