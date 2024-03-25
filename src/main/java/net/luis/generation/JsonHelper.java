package net.luis.generation;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 *
 * @author Luis-St
 *
 */

public class JsonHelper {
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public static <T> @NotNull T decode(@NotNull File file, @NotNull Codec<T> codec) {
		try {
			JsonElement element = GSON.fromJson(new JsonReader(new FileReader(file)), JsonElement.class);
			return codec.decode(JsonOps.INSTANCE, element).result().orElseThrow().getFirst();
		} catch (Exception e) {
			throw new RuntimeException("Failed to decode file with codec", e);
		}
	}
	
	public static <T> void encode(@NotNull File file, @NotNull Codec<T> codec, @NotNull T value) {
		JsonElement element = codec.encodeStart(JsonOps.INSTANCE, value).getOrThrow(false, s -> {});
		try (Writer writer = new FileWriter(file)) {
			GSON.toJson(element, writer);
		} catch (Exception e) {
			throw new RuntimeException("Failed to write encoded value to file", e);
		}
	}
}
