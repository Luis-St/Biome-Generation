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

public record DataConfig(@NotNull File outputFolder) {
	
	public static final Codec<DataConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.STRING.xmap(File::new, File::getPath).fieldOf("output_folder").forGetter(DataConfig::outputFolder)
	).apply(instance, DataConfig::new));
}
