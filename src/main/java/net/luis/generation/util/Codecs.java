package net.luis.generation.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 * @author Luis-St
 *
 */

public class Codecs {
	
	public static <E extends Enum<E>> @NotNull Codec<E> forEnum(@NotNull Supplier<E[]> supplier) {
		Objects.requireNonNull(supplier, "Value supplier must not be null");
		Map<String, E> map = Arrays.stream(supplier.get()).collect(Collectors.toMap(Enum::name, Function.identity()));
		return Codec.STRING.flatXmap(str -> {
			E value = map.get(str.toLowerCase());
			if (value == null) {
				return DataResult.error(() -> "Unknown enum value: " + str.toLowerCase());
			}
			return DataResult.success(value);
		}, value -> DataResult.success(value.name().toLowerCase()));
	}
}
