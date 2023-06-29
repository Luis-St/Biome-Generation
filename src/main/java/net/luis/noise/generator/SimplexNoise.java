package net.luis.noise.generator;

import net.luis.noise.Noise;
import net.luis.util.random.RandomSource;

public class SimplexNoise implements Noise {
	
	private static final double SQRT_3 = Math.sqrt(3.0);
	private static final double F2 = 0.5 * (SQRT_3 - 1.0);
	private static final double G2 = (3.0 - SQRT_3) / 6.0;
	protected static final int[][] GRADIENT = new int[][] {
		{1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1},
		{0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}, {1, 1, 0}, {0, -1, 1}, {-1, 1, 0}, {0, -1, -1}
	};
	private final int[] points = new int[512];
	public final double xo;
	public final double yo;
	public final double zo;
	
	public SimplexNoise(RandomSource rng) {
		this.xo = rng.nextDouble() * 256.0;
		this.yo = rng.nextDouble() * 256.0;
		this.zo = rng.nextDouble() * 256.0;
		for (int i = 0; i < 256; this.points[i] = i++) {}
		for (int i = 0; i < 256; ++i) {
			int offset = rng.nextInt(256 - i);
			int point = this.points[i];
			this.points[i] = this.points[offset + i];
			this.points[offset + i] = point;
		}
	}
	
	protected static double dot(int[] values, double x, double y, double z) {
		return (double) values[0] * x + (double) values[1] * y + (double) values[2] * z;
	}
	
	@Override
	public double getValue(double x, double z) {
		double d0 = (x + z) * F2;
		int i = (int) Math.floor(x + d0);
		int j = (int) Math.floor(z + d0);
		double d1 = (double) (i + j) * G2;
		double d2 = (double) i - d1;
		double d3 = (double) j - d1;
		double d4 = x - d2;
		double d5 = z - d3;
		int k;
		int l;
		if (d4 > d5) {
			k = 1;
			l = 0;
		} else {
			k = 0;
			l = 1;
		}
		double d6 = d4 - (double) k + G2;
		double d7 = d5 - (double) l + G2;
		double d8 = d4 - 1.0 + 2.0 * G2;
		double d9 = d5 - 1.0 + 2.0 * G2;
		int i1 = i & 255;
		int j1 = j & 255;
		int k1 = this.point(i1 + this.point(j1)) % 12;
		int l1 = this.point(i1 + k + this.point(j1 + l)) % 12;
		int i2 = this.point(i1 + 1 + this.point(j1 + 1)) % 12;
		double d10 = this.getCornerNoise3D(k1, d4, d5, 0.0, 0.5);
		double d11 = this.getCornerNoise3D(l1, d6, d7, 0.0, 0.5);
		double d12 = this.getCornerNoise3D(i2, d8, d9, 0.0, 0.5);
		return 70.0 * (d10 + d11 + d12);
	}
	
	@Override
	public double getValue(double x, double y, double z) {
		double d1 = (x + y + z) * 0.3333333333333333D;
		int i = (int) Math.floor(x + d1);
		int j = (int) Math.floor(y + d1);
		int k = (int) Math.floor(z + d1);
		double d3 = (double) (i + j + k) * 0.16666666666666666D;
		double d4 = (double) i - d3;
		double d5 = (double) j - d3;
		double d6 = (double) k - d3;
		double d7 = x - d4;
		double d8 = y - d5;
		double d9 = z - d6;
		int l;
		int i1;
		int j1;
		int k1;
		int l1;
		int i2;
		if (d7 >= d8) {
			if (d8 >= d9) {
				l = 1;
				i1 = 0;
				j1 = 0;
				k1 = 1;
				l1 = 1;
				i2 = 0;
			} else if (d7 >= d9) {
				l = 1;
				i1 = 0;
				j1 = 0;
				k1 = 1;
				l1 = 0;
				i2 = 1;
			} else {
				l = 0;
				i1 = 0;
				j1 = 1;
				k1 = 1;
				l1 = 0;
				i2 = 1;
			}
		} else if (d8 < d9) {
			l = 0;
			i1 = 0;
			j1 = 1;
			k1 = 0;
			l1 = 1;
			i2 = 1;
		} else if (d7 < d9) {
			l = 0;
			i1 = 1;
			j1 = 0;
			k1 = 0;
			l1 = 1;
			i2 = 1;
		} else {
			l = 0;
			i1 = 1;
			j1 = 0;
			k1 = 1;
			l1 = 1;
			i2 = 0;
		}
		double d10 = d7 - (double) l + 0.16666666666666666;
		double d11 = d8 - (double) i1 + 0.16666666666666666;
		double d12 = d9 - (double) j1 + 0.16666666666666666;
		double d13 = d7 - (double) k1 + 0.3333333333333333;
		double d14 = d8 - (double) l1 + 0.3333333333333333;
		double d15 = d9 - (double) i2 + 0.3333333333333333;
		double d16 = d7 - 1.0 + 0.5;
		double d17 = d8 - 1.0 + 0.5;
		double d18 = d9 - 1.0 + 0.5;
		int j2 = i & 255;
		int k2 = j & 255;
		int l2 = k & 255;
		int i3 = this.point(j2 + this.point(k2 + this.point(l2))) % 12;
		int j3 = this.point(j2 + l + this.point(k2 + i1 + this.point(l2 + j1))) % 12;
		int k3 = this.point(j2 + k1 + this.point(k2 + l1 + this.point(l2 + i2))) % 12;
		int l3 = this.point(j2 + 1 + this.point(k2 + 1 + this.point(l2 + 1))) % 12;
		double d19 = this.getCornerNoise3D(i3, d7, d8, d9, 0.6);
		double d20 = this.getCornerNoise3D(j3, d10, d11, d12, 0.6);
		double d21 = this.getCornerNoise3D(k3, d13, d14, d15, 0.6);
		double d22 = this.getCornerNoise3D(l3, d16, d17, d18, 0.6);
		return 32.0D * (d19 + d20 + d21 + d22);
	}
	
	//region Helper methods
	private int point(int offset) {
		return this.points[offset & 255];
	}
	
	private double getCornerNoise3D(int gradient, double x, double y, double z, double offset) {
		double scalar = offset - x * x - y * y - z * z;
		double result;
		if (scalar < 0.0) {
			result = 0.0;
		} else {
			scalar *= scalar;
			result = scalar * scalar * dot(GRADIENT[gradient], x, y, z);
		}
		return result;
	}
	//endregion
}