package net.luis.generation.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 *
 * @author Luis-St
 *
 */

public record ImageConfig(@NotNull File outputFolder, int size) {
	
	public static final Codec<ImageConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.STRING.xmap(File::new, File::getPath).fieldOf("output_folder").forGetter(ImageConfig::outputFolder),
		Codec.INT.fieldOf("size").forGetter(ImageConfig::size)
	).apply(instance, ImageConfig::new));
}
