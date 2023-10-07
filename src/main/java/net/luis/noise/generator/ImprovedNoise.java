package net.luis.noise.generator;

import net.luis.noise.Noise;
import net.luis.util.random.RandomSource;

public final class ImprovedNoise implements Noise {
	
	private final byte[] points;
	public final double xo;
	public final double yo;
	public final double zo;
	
	public ImprovedNoise(RandomSource rng) {
		this.xo = rng.nextDouble() * 256.0;
		this.yo = rng.nextDouble() * 256.0;
		this.zo = rng.nextDouble() * 256.0;
		this.points = new byte[256];
		for (int i = 0; i < 256; ++i) {
			this.points[i] = (byte) i;
		}
		for (int i = 0; i < 256; ++i) {
			int offset = rng.nextInt(256 - i);
			byte point = this.points[i];
			this.points[i] = this.points[i + offset];
			this.points[i + offset] = point;
		}
	}
	
	@Override
	public double getValue(double x, double z) {
		return this.noise(x, 0.0, z, 0.0, 0.0);
	}
	
	@Override
	public double getValue(double x, double y, double z) {
		return this.noise(x, y, z, 0.0, 0.0);
	}
	
	public double noise(double x, double y, double z, double d, double f) {
		double d0 = x + this.xo;
		double d1 = y + this.yo;
		double d2 = z + this.zo;
		int i = (int) Math.floor(d0);
		int j = (int) Math.floor(d1);
		int k = (int) Math.floor(d2);
		double d3 = d0 - (double) i;
		double d4 = d1 - (double) j;
		double d5 = d2 - (double) k;
		double d6;
		if (d == 0.0) {
			d6 = 0.0;
		} else {
			double d7;
			if (f >= 0.0 && f < d4) {
				d7 = f;
			} else {
				d7 = d4;
			}
			d6 = Math.floor(d7 / d + 1.0E-7) * d;
		}
		return this.sampleAndLerp(i, j, k, d3, d4 - d6, d5, d4);
	}
	
	private double sampleAndLerp(int x, int y, int z, double d, double f, double h, double i) {
		int i0 = this.point(x);
		int j = this.point(x + 1);
		int k = this.point(i0 + y);
		int l = this.point(i0 + y + 1);
		int i1 = this.point(j + y);
		int j1 = this.point(j + y + 1);
		double d0 = this.gradDot(this.point(k + z), d, f, h);
		double d1 = this.gradDot(this.point(i1 + z), d - 1.0D, f, h);
		double d2 = this.gradDot(this.point(l + z), d, f - 1.0D, h);
		double d3 = this.gradDot(this.point(j1 + z), d - 1.0D, f - 1.0D, h);
		double d4 = this.gradDot(this.point(k + z + 1), d, f, h - 1.0D);
		double d5 = this.gradDot(this.point(i1 + z + 1), d - 1.0D, f, h - 1.0D);
		double d6 = this.gradDot(this.point(l + z + 1), d, f - 1.0D, h - 1.0D);
		double d7 = this.gradDot(this.point(j1 + z + 1), d - 1.0D, f - 1.0D, h - 1.0D);
		double d8 = this.smoothStep(d);
		double d9 = this.smoothStep(i);
		double d10 = this.smoothStep(h);
		return this.lerp3(d8, d9, d10, d0, d1, d2, d3, d4, d5, d6, d7);
	}
	
	//region Helper methods
	private double gradDot(int i, double x, double y, double z) {
		return SimplexNoise.dot(SimplexNoise.GRADIENT[i & 15], x, y, z);
	}
	
	private int point(int value) {
		return this.points[value & 255] & 255;
	}
	
	private double smoothStep(double value) {
		return value * value * value * (value * (value * 6.0 - 15.0) + 10.0);
	}
	
	private double smoothStepDerivative(double value) {
		return 30.0 * value * value * (value - 1.0) * (value - 1.0);
	}
	
	private double lerp(double value, double min, double max) {
		return min + value * (max - min);
	}
	
	private double lerp2(double inner, double outer, double firstMin, double firstMax, double secondMin, double secondMax) {
		return this.lerp(outer, this.lerp(inner, firstMin, firstMax), this.lerp(inner, secondMin, secondMax));
	}
	
	private double lerp3(double firstInner, double secondInner, double outerValue, double ffMin, double ffMax, double fsMin, double fsMax, double sfMin, double sfMax, double ssMin, double ssMax) {
		return this.lerp(outerValue, this.lerp2(firstInner, secondInner, ffMin, ffMax, fsMin, fsMax), lerp2(firstInner, secondInner, sfMin, sfMax, ssMin, ssMax));
	}
	//endregion
}