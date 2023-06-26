package net.luis.biome.noise.generator;

import net.luis.biome.noise.Noise;
import net.luis.biome.util.Mth;
import net.luis.biome.util.random.RandomSource;

public final class ImprovedNoise implements Noise {

	private final byte[] p;
	public final double xo;
	public final double yo;
	public final double zo;

	public ImprovedNoise(RandomSource source) {
		this.xo = source.nextDouble() * 256.0D;
		this.yo = source.nextDouble() * 256.0D;
		this.zo = source.nextDouble() * 256.0D;
		this.p = new byte[256];
		for (int i = 0; i < 256; ++i) {
			this.p[i] = (byte) i;
		}
		for (int k = 0; k < 256; ++k) {
			int j = source.nextInt(256 - k);
			byte b0 = this.p[k];
			this.p[k] = this.p[k + j];
			this.p[k + j] = b0;
		}
	}

	public double noise(double x, double y, double z) {
		return this.noise(x, y, z, 0.0D, 0.0D);
	}

	public double noise(double x, double y, double z, double d, double f) {
		double d0 = x + this.xo;
		double d1 = y + this.yo;
		double d2 = z + this.zo;
		int i = Mth.floor(d0);
		int j = Mth.floor(d1);
		int k = Mth.floor(d2);
		double d3 = d0 - (double) i;
		double d4 = d1 - (double) j;
		double d5 = d2 - (double) k;
		double d6;
		if (d != 0.0D) {
			double d7;
			if (f >= 0.0D && f < d4) {
				d7 = f;
			} else {
				d7 = d4;
			}
			d6 = (double) Mth.floor(d7 / d + (double) 1.0E-7F) * d;
		} else {
			d6 = 0.0D;
		}
		return this.sampleAndLerp(i, j, k, d3, d4 - d6, d5, d4);
	}

	public double noiseWithDerivative(double x, double y, double z, double[] ds) {
		double d0 = x + this.xo;
		double d1 = y + this.yo;
		double d2 = z + this.zo;
		int i = Mth.floor(d0);
		int j = Mth.floor(d1);
		int k = Mth.floor(d2);
		double d3 = d0 - (double) i;
		double d4 = d1 - (double) j;
		double d5 = d2 - (double) k;
		return this.sampleWithDerivative(i, j, k, d3, d4, d5, ds);
	}

	private static double gradDot(int i, double x, double y, double z) {
		return SimplexNoise.dot(SimplexNoise.GRADIENT[i & 15], x, y, z);
	}

	private int p(int p_75334_) {
		return this.p[p_75334_ & 255] & 255;
	}

	private double sampleAndLerp(int x, int y, int z, double d, double f, double h, double i) {
		int i0 = this.p(x);
		int j = this.p(x + 1);
		int k = this.p(i0 + y);
		int l = this.p(i0 + y + 1);
		int i1 = this.p(j + y);
		int j1 = this.p(j + y + 1);
		double d0 = gradDot(this.p(k + z), d, f, h);
		double d1 = gradDot(this.p(i1 + z), d - 1.0D, f, h);
		double d2 = gradDot(this.p(l + z), d, f - 1.0D, h);
		double d3 = gradDot(this.p(j1 + z), d - 1.0D, f - 1.0D, h);
		double d4 = gradDot(this.p(k + z + 1), d, f, h - 1.0D);
		double d5 = gradDot(this.p(i1 + z + 1), d - 1.0D, f, h - 1.0D);
		double d6 = gradDot(this.p(l + z + 1), d, f - 1.0D, h - 1.0D);
		double d7 = gradDot(this.p(j1 + z + 1), d - 1.0D, f - 1.0D, h - 1.0D);
		double d8 = Mth.smoothstep(d);
		double d9 = Mth.smoothstep(i);
		double d10 = Mth.smoothstep(h);
		return Mth.lerp3(d8, d9, d10, d0, d1, d2, d3, d4, d5, d6, d7);
	}

	private double sampleWithDerivative(int x, int y, int z, double d, double f, double h, double[] ds) {
		int i = this.p(x);
		int j = this.p(x + 1);
		int k = this.p(i + y);
		int l = this.p(i + y + 1);
		int i1 = this.p(j + y);
		int j1 = this.p(j + y + 1);
		int k1 = this.p(k + z);
		int l1 = this.p(i1 + z);
		int i2 = this.p(l + z);
		int j2 = this.p(j1 + z);
		int k2 = this.p(k + z + 1);
		int l2 = this.p(i1 + z + 1);
		int i3 = this.p(l + z + 1);
		int j3 = this.p(j1 + z + 1);
		int[] aint = SimplexNoise.GRADIENT[k1 & 15];
		int[] aint1 = SimplexNoise.GRADIENT[l1 & 15];
		int[] aint2 = SimplexNoise.GRADIENT[i2 & 15];
		int[] aint3 = SimplexNoise.GRADIENT[j2 & 15];
		int[] aint4 = SimplexNoise.GRADIENT[k2 & 15];
		int[] aint5 = SimplexNoise.GRADIENT[l2 & 15];
		int[] aint6 = SimplexNoise.GRADIENT[i3 & 15];
		int[] aint7 = SimplexNoise.GRADIENT[j3 & 15];
		double d0 = SimplexNoise.dot(aint, d, f, h);
		double d1 = SimplexNoise.dot(aint1, d - 1.0D, f, h);
		double d2 = SimplexNoise.dot(aint2, d, f - 1.0D, h);
		double d3 = SimplexNoise.dot(aint3, d - 1.0D, f - 1.0D, h);
		double d4 = SimplexNoise.dot(aint4, d, f, h - 1.0D);
		double d5 = SimplexNoise.dot(aint5, d - 1.0D, f, h - 1.0D);
		double d6 = SimplexNoise.dot(aint6, d, f - 1.0D, h - 1.0D);
		double d7 = SimplexNoise.dot(aint7, d - 1.0D, f - 1.0D, h - 1.0D);
		double d8 = Mth.smoothstep(d);
		double d9 = Mth.smoothstep(f);
		double d10 = Mth.smoothstep(h);
		double d11 = Mth.lerp3(d8, d9, d10, (double) aint[0], (double) aint1[0], (double) aint2[0], (double) aint3[0], (double) aint4[0], (double) aint5[0], (double) aint6[0], (double) aint7[0]);
		double d12 = Mth.lerp3(d8, d9, d10, (double) aint[1], (double) aint1[1], (double) aint2[1], (double) aint3[1], (double) aint4[1], (double) aint5[1], (double) aint6[1], (double) aint7[1]);
		double d13 = Mth.lerp3(d8, d9, d10, (double) aint[2], (double) aint1[2], (double) aint2[2], (double) aint3[2], (double) aint4[2], (double) aint5[2], (double) aint6[2], (double) aint7[2]);
		double d14 = Mth.lerp2(d9, d10, d1 - d0, d3 - d2, d5 - d4, d7 - d6);
		double d15 = Mth.lerp2(d10, d8, d2 - d0, d6 - d4, d3 - d1, d7 - d5);
		double d16 = Mth.lerp2(d8, d9, d4 - d0, d5 - d1, d6 - d2, d7 - d3);
		double d17 = Mth.smoothstepDerivative(d);
		double d18 = Mth.smoothstepDerivative(f);
		double d19 = Mth.smoothstepDerivative(h);
		double d20 = d11 + d17 * d14;
		double d21 = d12 + d18 * d15;
		double d22 = d13 + d19 * d16;
		ds[0] += d20;
		ds[1] += d21;
		ds[2] += d22;
		return Mth.lerp3(d8, d9, d10, d0, d1, d2, d3, d4, d5, d6, d7);
	}

	@Override
	public double getValue(double x, double z) {
		return this.noise(x, 0.0, z);
	}

	@Override
	public double getValue(double x, double y, double z) {
		return this.noise(x, y, z);
	}
}