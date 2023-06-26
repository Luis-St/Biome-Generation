package net.luis.biome.util;

import java.util.function.Consumer;

public class Util {
	
	public static <T> T make(T value, Consumer<T> consumer) {
		consumer.accept(value);
		return value;
	}

}
