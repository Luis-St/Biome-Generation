package net.luis.generation.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 *
 * @author Luis-St
 *
 */

public record UiConfig(int width, int height) {
	
	public static final Codec<UiConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.INT.fieldOf("width").forGetter(UiConfig::width),
		Codec.INT.fieldOf("height").forGetter(UiConfig::height)
	).apply(instance, UiConfig::new));
}
