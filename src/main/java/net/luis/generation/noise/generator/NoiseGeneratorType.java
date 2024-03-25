package net.luis.generation.noise.generator;

import com.mojang.serialization.Codec;
import net.luis.generation.util.Codecs;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public enum NoiseGeneratorType {
	
	IMPROVED("improved"),
	NORMAL("normal"),
	PERLIN("perlin"),
	SIMPLEX("simplex"),
	PERLIN_SIMPLEX("perlin_simplex");
	
	public static final Codec<NoiseGeneratorType> CODEC = Codecs.forEnum(NoiseGeneratorType::values);
	
	private final String name;
	
	NoiseGeneratorType(@NotNull String name) {
		this.name = Objects.requireNonNull(name, "Name must not be null");
	}
	
	public @NotNull String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
