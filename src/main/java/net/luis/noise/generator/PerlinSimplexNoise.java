package net.luis.noise.generator;

import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import net.luis.noise.Noise;
import net.luis.util.random.*;
import net.luis.utils.annotation.Ignored;

import java.util.List;

public class PerlinSimplexNoise implements Noise {
	
	private final SimplexNoise[] noiseLevels;
	private final double highestFreqValueFactor;
	private final double highestFreqInputFactor;
	
	public PerlinSimplexNoise(RandomSource source, List<Integer> octaves) {
		this(source, new IntRBTreeSet(octaves));
	}
	
	private PerlinSimplexNoise(RandomSource source, IntSortedSet octaves) {
		if (octaves.isEmpty()) {
			throw new IllegalArgumentException("Need some octaves!");
		} else {
			int first = -octaves.firstInt();
			int last = octaves.lastInt();
			int octaveCount = first + last + 1;
			if (octaveCount < 1) {
				throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
			} else {
				SimplexNoise simplexnoise = new SimplexNoise(source);
				this.noiseLevels = new SimplexNoise[octaveCount];
				if (last >= 0 && last < octaveCount && octaves.contains(0)) {
					this.noiseLevels[last] = simplexnoise;
				}
				for (int i = last + 1; i < octaveCount; ++i) {
					if (i >= 0 && octaves.contains(last - i)) {
						this.noiseLevels[i] = new SimplexNoise(source);
					} else {
						source.consumeCount(262);
					}
				}
				if (last > 0) {
					long noiseSeed = (long) (simplexnoise.getValue(simplexnoise.xo, simplexnoise.yo, simplexnoise.zo) * 9.223372E18);
					RandomSource rng = new WorldgenRandom(new LegacyRandomSource(noiseSeed));
					for (int i = last - 1; i >= 0; --i) {
						if (i < octaveCount && octaves.contains(last - i)) {
							this.noiseLevels[i] = new SimplexNoise(rng);
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
		for (SimplexNoise simplexNoise : this.noiseLevels) {
			if (simplexNoise != null) {
				result += simplexNoise.getValue(x * inputFactor + (simplex ? simplexNoise.xo : 0.0), z * inputFactor + (simplex ? simplexNoise.yo : 0.0)) * valueFactor;
			}
			inputFactor /= 2.0;
			valueFactor *= 2.0;
		}
		return result;
	}
}