package net.luis.ui;

import javafx.scene.text.Font;
import net.luis.utils.annotation.AutoInitialize;

/**
 *
 * @author Luis-St
 *
 */

@AutoInitialize
public class Fonts {
	
	public static final Font FA_BRANDS = Font.loadFont(Fonts.class.getResourceAsStream("/font/font_awesome_brands.otf"), 12);
	public static final Font FA_REGULAR = Font.loadFont(Fonts.class.getResourceAsStream("/font/font_awesome_regular.otf"), 12);
	public static final Font FA_SOLID = Font.loadFont(Fonts.class.getResourceAsStream("/font/font_awesome_solid.otf"), 12);
	
	public static void initialize() {
	
	}
}
