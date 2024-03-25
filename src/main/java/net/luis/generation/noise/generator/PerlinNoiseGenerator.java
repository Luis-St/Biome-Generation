package net.luis.generation.noise.generator;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.*;
import net.luis.generation.noise.random.PositionalRandomFactory;
import net.luis.generation.noise.random.RandomSource;
import net.luis.utils.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.IntStream;

public class PerlinNoiseGenerator implements NoiseGenerator {
	
	private final ImprovedNoiseGenerator[] noiseLevels;
	private final int firstOctave;
	private final DoubleList amplitudes;
	private final double lowestFreqValueFactor;
	private final double lowestFreqInputFactor;
	
	private PerlinNoiseGenerator(@NotNull RandomSource source, @NotNull Pair<Integer, DoubleList> pair, boolean improved) {
		Objects.requireNonNull(source, "Random source must not be null");
		Objects.requireNonNull(pair, "Pair must not be null");
		this.firstOctave = pair.getFirst();
		this.amplitudes = pair.getSecond();
		int size = this.amplitudes.size();
		int octave = -this.firstOctave;
		this.noiseLevels = new ImprovedNoiseGenerator[size];
		if (improved) {
			PositionalRandomFactory factory = source.forkPositional();
			for (int i = 0; i < size; ++i) {
				if (this.amplitudes.getDouble(i) != 0.0) {
					int value = this.firstOctave + i;
					this.noiseLevels[i] = new ImprovedNoiseGenerator(factory.fromHashOf("octave_" + value));
				}
			}
		} else {
			ImprovedNoiseGenerator improvedNoise = new ImprovedNoiseGenerator(source);
			if (octave >= 0 && octave < size) {
				double amplitude = this.amplitudes.getDouble(octave);
				if (amplitude != 0.0) {
					this.noiseLevels[octave] = improvedNoise;
				}
			}
			for (int i = octave - 1; i >= 0; --i) {
				if (i < size) {
					double d1 = this.amplitudes.getDouble(i);
					if (d1 == 0.0) {
						this.skipOctave(source);
					} else {
						this.noiseLevels[i] = new ImprovedNoiseGenerator(source);
					}
				} else {
					this.skipOctave(source);
				}
			}
			if (Arrays.stream(this.noiseLevels).filter(Objects::nonNull).count() != this.amplitudes.doubleStream().filter((amplitude) -> amplitude != 0.0).count()) {
				throw new IllegalStateException("Failed to create correct number of noise levels for given non-zero amplitudes");
			}
			if (octave < size - 1) {
				throw new IllegalArgumentException("Positive octaves are temporarily disabled");
			}
		}
		this.lowestFreqInputFactor = Math.pow(2.0, -octave);
		this.lowestFreqValueFactor = Math.pow(2.0, size - 1) / (Math.pow(2.0, size) - 1.0D);
	}
	
	//region Factory methods
	public static PerlinNoiseGenerator createLegacyForBlendedNoise(RandomSource source, IntStream amplitudes) {
		return new PerlinNoiseGenerator(source, makeAmplitudes(new IntRBTreeSet(amplitudes.boxed().collect(ImmutableList.toImmutableList()))), false);
	}
	
	public static PerlinNoiseGenerator createLegacyForLegacyNormalNoise(RandomSource source, int firstOctave, DoubleList amplitudes) {
		return new PerlinNoiseGenerator(source, Pair.of(firstOctave, amplitudes), false);
	}
	
	public static PerlinNoiseGenerator create(RandomSource source, IntStream amplitudes) {
		return create(source, amplitudes.boxed().collect(ImmutableList.toImmutableList()));
	}
	
	public static PerlinNoiseGenerator create(RandomSource source, List<Integer> amplitudes) {
		return new PerlinNoiseGenerator(source, makeAmplitudes(new IntRBTreeSet(amplitudes)), true);
	}
	
	public static PerlinNoiseGenerator create(RandomSource source, int firstOctave, double amplitude, double... amplitudes) {
		DoubleArrayList doublearraylist = new DoubleArrayList(amplitudes);
		doublearraylist.add(0, amplitude);
		return new PerlinNoiseGenerator(source, Pair.of(firstOctave, doublearraylist), true);
	}
	
	public static @NotNull PerlinNoiseGenerator create(@NotNull RandomSource source, int firstOctave, @NotNull DoubleList amplitudes) {
		return new PerlinNoiseGenerator(source, Pair.of(firstOctave, amplitudes), true);
	}
	//endregion
	
	//region Static helper methods
	private static @NotNull Pair<Integer, DoubleList> makeAmplitudes(@NotNull IntSortedSet amplitudes) {
		Objects.requireNonNull(amplitudes, "Amplitudes must not be null");
		if (amplitudes.isEmpty()) {
			throw new IllegalArgumentException("Need some octaves!");
		} else {
			int i = -amplitudes.firstInt();
			int j = amplitudes.lastInt();
			int k = i + j + 1;
			if (k < 1) {
				throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
			} else {
				DoubleList list = new DoubleArrayList(new double[k]);
				IntBidirectionalIterator iterator = amplitudes.iterator();
				while (iterator.hasNext()) {
					int l = iterator.nextInt();
					list.set(l + i, 1.0D);
				}
				return Pair.of(-i, list);
			}
		}
	}
	//endregion
	
	@Override
	public @NotNull NoiseGeneratorType getType() {
		return NoiseGeneratorType.PERLIN;
	}
	
	@Override
	public double getValue(double x, double y, double z) {
		return this.getValue(x, y, z, 0.0D, 0.0D, false);
	}
	
	public double getValue(double x, double y, double z, double d, double f, boolean improved) {
		double result = 0.0;
		double inputFactor = this.lowestFreqInputFactor;
		double valueFactor = this.lowestFreqValueFactor;
		for (int i = 0; i < this.noiseLevels.length; ++i) {
			ImprovedNoiseGenerator improvedNoise = this.noiseLevels[i];
			if (improvedNoise != null) {
				double noise = improvedNoise.noise(this.wrap(x * inputFactor), improved ? -improvedNoise.yo : this.wrap(y * inputFactor), this.wrap(z * inputFactor), d * inputFactor, f * inputFactor);
				result += this.amplitudes.getDouble(i) * noise * valueFactor;
			}
			inputFactor *= 2.0;
			valueFactor /= 2.0;
		}
		return result;
	}
	
	public @NotNull ImprovedNoiseGenerator getOctaveNoise(int octave) {
		return this.noiseLevels[this.noiseLevels.length - 1 - octave];
	}
	
	@Override
	public double getValue(double x, double z) {
		return this.getValue(x, 0.0, z);
	}
	
	//region Helper methods
	private void skipOctave(@NotNull RandomSource source) {
		Objects.requireNonNull(source, "Random source must not be null").consumeCount(262);
	}
	
	private double wrap(double value) {
		return value - Math.floor(value / 3.3554432E7 + 0.5) * 3.3554432E7;
	}
	//endregion
}