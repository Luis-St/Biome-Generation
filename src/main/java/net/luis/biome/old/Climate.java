package net.luis.biome.old;

import net.luis.biome.Biome;
import net.luis.biome.Biomes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Deprecated
public record Climate(Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion) {
	
	protected static final Logger LOGGER = LogManager.getLogger();
	
	public boolean isPoint() {
		return this.temperature.isPoint() && this.humidity.isPoint() && this.continentalness.isPoint() && this.erosion.isPoint();
	}
	
	public Climate.Parameter value(Climate.Type type) {
		if (type == Climate.Type.TEMPERATURE) {
			return this.temperature;
		} else if (type == Climate.Type.HUMIDITY) {
			return this.humidity;
		} else if (type == Climate.Type.CONTINENTALNESS) {
			return this.continentalness;
		} else if (type == Climate.Type.EROSION) {
			return this.erosion;
		}
		throw new IllegalArgumentException(type.name);
	}
	
	public boolean match(Climate.Type type, Climate.Target target) {
		return this.value(type).match(type.value(target));
	}

//	public boolean match(Climate.Target target) {
//		return this.temperature.match(target.temperature()) && this.humidity.match(target.humidity()) && this.continentalness.match(target.continentalness()) && this.erosion.match(target.erosion());
//	}
//	
//	public boolean match(Climate climate) {
//		return this.temperature.match(climate.temperature()) && this.humidity.match(climate.humidity()) && this.continentalness.match(climate.continentalness()) && this.erosion.match(climate.erosion());
//	}
	
	public Climate average() {
		return new Climate(temperature.average(), humidity.average(), continentalness.average(), erosion.average());
	}
	
	public static enum Type {
		
		TEMPERATURE("temperature") {
			@Override
			public double value(Target target) {
				return target.temperature();
			}
		},
		HUMIDITY("humidity") {
			@Override
			public double value(Target target) {
				return target.humidity();
			}
		},
		CONTINENTALNESS("continentalness") {
			@Override
			public double value(Target target) {
				return target.continentalness();
			}
		},
		EROSION("erosion") {
			@Override
			public double value(Target target) {
				return target.erosion();
			}
		};
		
		protected final String name;
		
		private Type(String name) {
			this.name = name;
		}
		
		public abstract double value(Climate.Target target);
	}
	
	public static record Target(double temperature, double humidity, double continentalness, double erosion) {
		
		public static Climate.Target of(double temperature, double humidity, double continentalness, double erosion) {
			return new Climate.Target(temperature, humidity, continentalness, erosion);
		}
		
	}
	
	public static record Parameter(double min, double max) {
		
		public static final Parameter INFINITE = new Parameter(Double.MIN_VALUE, Double.MAX_VALUE);
		
		public static Climate.Parameter span(double min, double max) {
			if (min > max) {
				throw new IllegalArgumentException("The min value " + min + " can not be larger than the max value " + max);
			}
			return new Climate.Parameter(min, max);
		}
		
		public static Climate.Parameter point(double value) {
			return span(value, value);
		}
		
		public boolean isPoint() {
			return this.min == this.max;
		}
		
		public boolean match(double value) {
			return this.max >= value && value >= this.min;
		}

//		public boolean match(Climate.Parameter parameter) {
//			if (this.isPoint() && parameter.isPoint()) {
//				return this.max == parameter.max && this.min == parameter.min;
//			}
//			return (this.max >= parameter.max && parameter.max >= this.min) || (this.max >= parameter.min && parameter.min >= this.min);
//		}
		
		public Climate.Parameter average() {
			return this.isPoint() ? this : Climate.Parameter.point((this.max - this.min) / 2);
		}
		
	}
	
	public static class ParameterList {
		
		protected final boolean debug;
		
		public ParameterList() {
			this(false);
		}
		
		public ParameterList(boolean debug) {
			this.debug = debug;
		}
		
		public Biome findBiome(Climate.Target target, Biome fallback) {
			Biome biome = this.findBiome(target, this.possibleBiomes(Type.TEMPERATURE, target), this.possibleBiomes(Type.HUMIDITY, target), this.possibleBiomes(Type.CONTINENTALNESS, target), this.possibleBiomes(Type.EROSION, target));
			return biome != null ? biome : fallback;
		}
		
		protected Biome findBiome(Climate.Target target, List<Biome> tBiomes, List<Biome> hBiomes, List<Biome> cBiomes, List<Biome> eBiomes) {
			if (this.debug) {
				LOGGER.debug("Biomes:");
				LOGGER.debug(" - Temperature: {}", tBiomes.stream().map(Biome::name).toList());
				LOGGER.debug(" - Humidity: {}", hBiomes.stream().map(Biome::name).toList());
				LOGGER.debug(" - Continentalness: {}", cBiomes.stream().map(Biome::name).toList());
				LOGGER.debug(" - Erosion: {}", eBiomes.stream().map(Biome::name).toList());
			}
			for (Biome tBiome : tBiomes) {
				for (Biome hBiome : hBiomes) {
					for (Biome cBiome : cBiomes) {
						for (Biome eBiome : eBiomes) {
							if (tBiome == hBiome && tBiome == cBiome && tBiome == eBiome) {
								return tBiome;
							}
						}
					}
				}
			}
			if (this.debug) {
				LOGGER.debug("No Biome was found, use {} Biome instead", Biomes.VOID.name());
			}
			return null;
		}
		
		protected List<Biome> possibleBiomes(Climate.Type type, Climate.Target target) {
			return ClimateBuilder.INSTANCE.getBiomeClimates().stream().filter((biome) -> {
				return biome.getValue().match(type, target);
			}).map(Map.Entry::getKey).collect(Collectors.toList());
		}
		
	}
}
