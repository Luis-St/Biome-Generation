package net.luis.generation.noise.generator;

import it.unimi.dsi.fastutil.doubles.*;
import net.luis.generation.noise.random.RandomSource;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NormalNoiseGenerator implements NoiseGenerator {
	
	private final double valueFactor;
	private final PerlinNoiseGenerator first;
	private final PerlinNoiseGenerator second;
	
	public NormalNoiseGenerator(@NotNull RandomSource source, int firstOctave, double @NotNull ... amplitudes) {
		this(source, firstOctave, new DoubleArrayList(amplitudes));
	}
	
	public NormalNoiseGenerator(@NotNull RandomSource source, int firstOctave, @NotNull DoubleList amplitudes) {
		Objects.requireNonNull(source, "Random source must not be null");
		Objects.requireNonNull(amplitudes, "Amplitudes must not be null");
		this.first = PerlinNoiseGenerator.create(source, firstOctave, amplitudes);
		this.second = PerlinNoiseGenerator.create(source, firstOctave, amplitudes);
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
	
	private double expectedDeviation(int value) {
		return 0.1 * (2.0 / value + 1.0);
	}
}