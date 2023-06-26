package net.luis.biome.noise.generator;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import net.luis.biome.noise.Noise;
import net.luis.biome.util.Mth;
import net.luis.biome.util.Pair;
import net.luis.biome.util.random.PositionalRandomFactory;
import net.luis.biome.util.random.RandomSource;

public class PerlinNoise implements Noise {
	
	private final ImprovedNoise[] noiseLevels;
	private final int firstOctave;
	private final DoubleList amplitudes;
	private final double lowestFreqValueFactor;
	private final double lowestFreqInputFactor;

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

	public static PerlinNoise create(RandomSource source, int firstOctave, DoubleList amplitudes) {
		return new PerlinNoise(source, Pair.of(firstOctave, amplitudes), true);
	}

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
				DoubleList doublelist = new DoubleArrayList(new double[k]);
				IntBidirectionalIterator intbidirectionaliterator = amplitudes.iterator();
				while (intbidirectionaliterator.hasNext()) {
					int l = intbidirectionaliterator.nextInt();
					doublelist.set(l + i, 1.0D);
				}
				return Pair.of(-i, doublelist);
			}
		}
	}

	@SuppressWarnings("deprecation")
	protected PerlinNoise(RandomSource source, Pair<Integer, DoubleList> pair, boolean improved) {
		this.firstOctave = pair.getFirst();
		this.amplitudes = pair.getSecond();
		int i = this.amplitudes.size();
		int j = -this.firstOctave;
		this.noiseLevels = new ImprovedNoise[i];
		if (improved) {
			PositionalRandomFactory positionalrandomfactory = source.forkPositional();
			for (int k = 0; k < i; ++k) {
				if (this.amplitudes.getDouble(k) != 0.0D) {
					int l = this.firstOctave + k;
					this.noiseLevels[k] = new ImprovedNoise(positionalrandomfactory.fromHashOf("octave_" + l));
				}
			}
		} else {
			ImprovedNoise improvednoise = new ImprovedNoise(source);
			if (j >= 0 && j < i) {
				double d0 = this.amplitudes.getDouble(j);
				if (d0 != 0.0D) {
					this.noiseLevels[j] = improvednoise;
				}
			}
			for (int i1 = j - 1; i1 >= 0; --i1) {
				if (i1 < i) {
					double d1 = this.amplitudes.getDouble(i1);
					if (d1 != 0.0D) {
						this.noiseLevels[i1] = new ImprovedNoise(source);
					} else {
						skipOctave(source);
					}
				} else {
					skipOctave(source);
				}
			}
			if (Arrays.stream(this.noiseLevels).filter(Objects::nonNull).count() != this.amplitudes.stream().filter((p_192897_) -> {
				return p_192897_ != 0.0D;
			}).count()) {
				throw new IllegalStateException("Failed to create correct number of noise levels for given non-zero amplitudes");
			}
			if (j < i - 1) {
				throw new IllegalArgumentException("Positive octaves are temporarily disabled");
			}
		}
		this.lowestFreqInputFactor = Math.pow(2.0D, (double) (-j));
		this.lowestFreqValueFactor = Math.pow(2.0D, (double) (i - 1)) / (Math.pow(2.0D, (double) i) - 1.0D);
	}

	private static void skipOctave(RandomSource source) {
		source.consumeCount(262);
	}

	@Override
	public double getValue(double x, double y, double z) {
		return this.getValue(x, y, z, 0.0D, 0.0D, false);
	}

	public double getValue(double x, double y, double z, double d, double f, boolean improved) {
		double d0 = 0.0D;
		double d1 = this.lowestFreqInputFactor;
		double d2 = this.lowestFreqValueFactor;
		for (int i = 0; i < this.noiseLevels.length; ++i) {
			ImprovedNoise improvednoise = this.noiseLevels[i];
			if (improvednoise != null) {
				double d3 = improvednoise.noise(wrap(x * d1), improved ? -improvednoise.yo : wrap(y * d1), wrap(z * d1), d * d1, f * d1);
				d0 += this.amplitudes.getDouble(i) * d3 * d2;
			}
			d1 *= 2.0D;
			d2 /= 2.0D;
		}
		return d0;
	}

	@Nullable
	public ImprovedNoise getOctaveNoise(int octave) {
		return this.noiseLevels[this.noiseLevels.length - 1 - octave];
	}

	public static double wrap(double d) {
		return d - (double) Mth.lfloor(d / 3.3554432E7D + 0.5D) * 3.3554432E7D;
	}

	protected int firstOctave() {
		return this.firstOctave;
	}

	protected DoubleList amplitudes() {
		return this.amplitudes;
	}

	@Override
	public double getValue(double x, double z) {
		return this.getValue(x, 0.0, z);
	}
}