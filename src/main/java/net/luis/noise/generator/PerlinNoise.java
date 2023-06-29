package net.luis.noise.generator;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.*;
import net.luis.noise.Noise;
import net.luis.util.random.PositionalRandomFactory;
import net.luis.util.random.RandomSource;
import net.luis.utils.util.Pair;

import java.util.*;
import java.util.stream.IntStream;

public class PerlinNoise implements Noise {
	
	private final ImprovedNoise[] noiseLevels;
	private final int firstOctave;
	private final DoubleList amplitudes;
	private final double lowestFreqValueFactor;
	private final double lowestFreqInputFactor;
	
	private PerlinNoise(RandomSource source, Pair<Integer, DoubleList> pair, boolean improved) {
		this.firstOctave = pair.getFirst();
		this.amplitudes = pair.getSecond();
		int size = this.amplitudes.size();
		int octave = -this.firstOctave;
		this.noiseLevels = new ImprovedNoise[size];
		if (improved) {
			PositionalRandomFactory factory = source.forkPositional();
			for (int i = 0; i < size; ++i) {
				if (this.amplitudes.getDouble(i) != 0.0) {
					int value = this.firstOctave + i;
					this.noiseLevels[i] = new ImprovedNoise(factory.fromHashOf("octave_" + value));
				}
			}
		} else {
			ImprovedNoise improvedNoise = new ImprovedNoise(source);
			if (octave >= 0 && octave < size) {
				double amplitude = this.amplitudes.getDouble(octave);
				if (amplitude != 0.0) {
					this.noiseLevels[octave] = improvedNoise;
				}
			}
			for (int i = octave - 1; i >= 0; --i) {
				if (i < size) {
					double d1 = this.amplitudes.getDouble(i);
					if (d1 != 0.0D) {
						this.noiseLevels[i] = new ImprovedNoise(source);
					} else {
						this.skipOctave(source);
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
	public static PerlinNoise createLegacyForBlendedNoise(RandomSource source, IntStream amplitudes) {
		return new PerlinNoise(source, makeAmplitudes(new IntRBTreeSet(amplitudes.boxed().collect(ImmutableList.toImmutableList()))), false);
	}
	
	public static PerlinNoise createLegacyForLegacyNormalNoise(RandomSource source, int firstOctave, DoubleList amplitudes) {
		return new PerlinNoise(source, Pair.of(firstOctave, amplitudes), false);
	}
	
	public static PerlinNoise create(RandomSource source, IntStream amplitudes) {
		return create(source, amplitudes.boxed().collect(ImmutableList.toImmutableList()));
	}
	
	public static PerlinNoise create(RandomSource source, List<Integer> amplitudes) {
		return new PerlinNoise(source, makeAmplitudes(new IntRBTreeSet(amplitudes)), true);
	}
	
	public static PerlinNoise create(RandomSource source, int firstOctave, double amplitude, double... amplitudes) {
		DoubleArrayList doublearraylist = new DoubleArrayList(amplitudes);
		doublearraylist.add(0, amplitude);
		return new PerlinNoise(source, Pair.of(firstOctave, doublearraylist), true);
	}
	//endregion
	
	public static PerlinNoise create(RandomSource source, int firstOctave, DoubleList amplitudes) {
		return new PerlinNoise(source, Pair.of(firstOctave, amplitudes), true);
	}
	//endregion
	
	//region Static helper methods
	private static Pair<Integer, DoubleList> makeAmplitudes(IntSortedSet amplitudes) {
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
	
	@Override
	public double getValue(double x, double y, double z) {
		return this.getValue(x, y, z, 0.0D, 0.0D, false);
	}
	
	public double getValue(double x, double y, double z, double d, double f, boolean improved) {
		double result = 0.0D;
		double inputFactor = this.lowestFreqInputFactor;
		double valueFactor = this.lowestFreqValueFactor;
		for (int i = 0; i < this.noiseLevels.length; ++i) {
			ImprovedNoise improvedNoise = this.noiseLevels[i];
			if (improvedNoise != null) {
				double noise = improvedNoise.noise(this.wrap(x * inputFactor), improved ? -improvedNoise.yo : this.wrap(y * inputFactor), this.wrap(z * inputFactor), d * inputFactor, f * inputFactor);
				result += this.amplitudes.getDouble(i) * noise * valueFactor;
			}
			inputFactor *= 2.0;
			valueFactor /= 2.0;
		}
		return result;
	}
	
	public ImprovedNoise getOctaveNoise(int octave) {
		return this.noiseLevels[this.noiseLevels.length - 1 - octave];
	}
	
	@Override
	public double getValue(double x, double z) {
		return this.getValue(x, 0.0, z);
	}
	
	//region Helper methods
	int firstOctave() {
		return this.firstOctave;
	}
	
	DoubleList amplitudes() {
		return this.amplitudes;
	}
	
	private void skipOctave(RandomSource source) {
		source.consumeCount(262);
	}
	
	private double wrap(double value) {
		return value - Math.floor(value / 3.3554432E7 + 0.5) * 3.3554432E7;
	}
	//endregion
}