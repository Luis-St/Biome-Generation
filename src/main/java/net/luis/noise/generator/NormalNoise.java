package net.luis.noise.generator;

import it.unimi.dsi.fastutil.doubles.*;
import net.luis.noise.Noise;
import net.luis.util.random.RandomSource;

import java.util.List;

public class NormalNoise implements Noise {
	
	private final double valueFactor;
	private final PerlinNoise first;
	private final PerlinNoise second;
	
	private NormalNoise(RandomSource source, int firstOctave, DoubleList amplitudes, boolean legacy) {
		if (legacy) {
			this.first = PerlinNoise.create(source, firstOctave, amplitudes);
			this.second = PerlinNoise.create(source, firstOctave, amplitudes);
		} else {
			this.first = PerlinNoise.createLegacyForLegacyNormalNoise(source, firstOctave, amplitudes);
			this.second = PerlinNoise.createLegacyForLegacyNormalNoise(source, firstOctave, amplitudes);
		}
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		DoubleListIterator iterator = amplitudes.iterator();
		while (iterator.hasNext()) {
			int i = iterator.nextIndex();
			double value = iterator.nextDouble();
			if (value != 0.0) {
				min = Math.min(min, i);
				max = Math.max(max, i);
			}
		}
		this.valueFactor = 0.16666666666666666 / this.expectedDeviation(max - min);
	}
	
	//region Factory methods
	public static NormalNoise createLegacyNetherBiome(RandomSource source, NormalNoise.NoiseParameters parameters) {
		return new NormalNoise(source, parameters.firstOctave(), parameters.amplitudes(), false);
	}
	
	public static NormalNoise create(RandomSource source, int firstOctave, double... amplitudes) {
		return new NormalNoise(source, firstOctave, new DoubleArrayList(amplitudes), true);
	}
	
	public static NormalNoise create(RandomSource source, NormalNoise.NoiseParameters parameters) {
		return new NormalNoise(source, parameters.firstOctave(), parameters.amplitudes(), true);
	}
	//endregion
	
	public static NormalNoise create(RandomSource source, int firstOctave, DoubleList amplitudes) {
		return new NormalNoise(source, firstOctave, amplitudes, true);
	}
	
	@Override
	public double getValue(double x, double z) {
		return this.getValue(x, 0.0, z);
	}
	
	@Override
	public double getValue(double x, double y, double z) {
		double xOffset = x * 1.0181268882175227;
		double yOffset = y * 1.0181268882175227;
		double zOffset = z * 1.0181268882175227;
		return (this.first.getValue(x, y, z) + this.second.getValue(xOffset, yOffset, zOffset)) * this.valueFactor;
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
	
	//region Helper methods
	private double expectedDeviation(int value) {
		return 0.1 * (1.0 + 1.0 / (double) (value + 1));
	}
	//endregion
	
	public static class NoiseParameters {
		
		private final int firstOctave;
		private final DoubleList amplitudes;
		
		public NoiseParameters(int firstOctave, List<Double> amplitudes) {
			this.firstOctave = firstOctave;
			this.amplitudes = new DoubleArrayList(amplitudes);
		}
		
		public int firstOctave() {
			return this.firstOctave;
		}
		
		public DoubleList amplitudes() {
			return this.amplitudes;
		}
	}
}