package net.luis.biome.noise.generator;

import java.util.List;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import net.luis.biome.noise.Noise;
import net.luis.biome.util.random.RandomSource;

public class NormalNoise implements Noise {

	private final double valueFactor;
	private final PerlinNoise first;
	private final PerlinNoise second;

	public static NormalNoise createLegacyNetherBiome(RandomSource source, NormalNoise.NoiseParameters parameters) {
		return new NormalNoise(source, parameters.firstOctave(), parameters.amplitudes(), false);
	}

	public static NormalNoise create(RandomSource source, int firstOctave, double... amplitudes) {
		return new NormalNoise(source, firstOctave, new DoubleArrayList(amplitudes), true);
	}

	public static NormalNoise create(RandomSource source, NormalNoise.NoiseParameters parameters) {
		return new NormalNoise(source, parameters.firstOctave(), parameters.amplitudes(), true);
	}

	public static NormalNoise create(RandomSource source, int firstOctave, DoubleList amplitudes) {
		return new NormalNoise(source, firstOctave, amplitudes, true);
	}

	private NormalNoise(RandomSource source, int firstOctave, DoubleList amplitudes, boolean legacy) {
		if (legacy) {
			this.first = PerlinNoise.create(source, firstOctave, amplitudes);
			this.second = PerlinNoise.create(source, firstOctave, amplitudes);
		} else {
			this.first = PerlinNoise.createLegacyForLegacyNormalNoise(source, firstOctave, amplitudes);
			this.second = PerlinNoise.createLegacyForLegacyNormalNoise(source, firstOctave, amplitudes);
		}
		int i = Integer.MAX_VALUE;
		int j = Integer.MIN_VALUE;
		DoubleListIterator doublelistiterator = amplitudes.iterator();
		while (doublelistiterator.hasNext()) {
			int k = doublelistiterator.nextIndex();
			double d0 = doublelistiterator.nextDouble();
			if (d0 != 0.0D) {
				i = Math.min(i, k);
				j = Math.max(j, k);
			}
		}
		this.valueFactor = 0.16666666666666666D / expectedDeviation(j - i);
	}

	private static double expectedDeviation(int i) {
		return 0.1D * (1.0D + 1.0D / (double) (i + 1));
	}

	@Override
	public double getValue(double x, double z) {
		return this.getValue(x, 0.0, z);
	}
	
	@Override
	public double getValue(double x, double y, double z) {
		double d0 = x * 1.0181268882175227D;
		double d1 = y * 1.0181268882175227D;
		double d2 = z * 1.0181268882175227D;
		return (this.first.getValue(x, y, z) + this.second.getValue(d0, d1, d2)) * this.valueFactor;
	}
	
	public double min() {
		return (-1.0 + -1.0) * this.valueFactor;
	}
	
	public double max() {
		return (1.0 + 1.0) * this.valueFactor;
	}

	public NormalNoise.NoiseParameters parameters() {
		return new NormalNoise.NoiseParameters(this.first.firstOctave(), this.first.amplitudes());
	}

	public static class NoiseParameters {
		
		private final int firstOctave;
		private final DoubleList amplitudes;

		public NoiseParameters(int firstOctave, List<Double> amplitudes) {
			this.firstOctave = firstOctave;
			this.amplitudes = new DoubleArrayList(amplitudes);
		}

		public NoiseParameters(int firstOctave, double amplitude, double... amplitudes) {
			this.firstOctave = firstOctave;
			this.amplitudes = new DoubleArrayList(amplitudes);
			this.amplitudes.add(0, amplitude);
		}

		public int firstOctave() {
			return this.firstOctave;
		}

		public DoubleList amplitudes() {
			return this.amplitudes;
		}
	}
}