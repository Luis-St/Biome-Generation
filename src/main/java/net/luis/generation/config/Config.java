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

public record Config(@NotNull File resourceFolder, @NotNull UiConfig ui, @NotNull DataConfig data, @NotNull ImageConfig image) {
	
	public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.STRING.xmap(File::new, File::getPath).fieldOf("resource_folder").forGetter(Config::resourceFolder),
		UiConfig.CODEC.fieldOf("ui").forGetter(Config::ui),
		DataConfig.CODEC.fieldOf("data").forGetter(Config::data),
		ImageConfig.CODEC.fieldOf("image").forGetter(Config::image)
	).apply(instance, Config::new));
}
