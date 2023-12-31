package ColorScheme;

import javafx.scene.paint.Color;

/**
 * TritanopiaColorScheme class implements the ColorScheme interface
 * and defines color schemes tailored for adventure game users with tritanopia.
 */
public class TritanopiaColorScheme implements ColorScheme {

    /**
     * Gets the button color for the Tritanopia color scheme.
     *
     * @return String representing the CSS background color for buttons.
     */
    @Override
    public String getButtonColor() {
        return " -fx-background-color: #05F6FF;"; //neon light blue
    }

    /**
     * Gets the background color for the Tritanopia color scheme.
     *
     * @return String representing the hexadecimal background color for the overall background.
     */
    @Override
    public String getBackgroundColor() {
        return "#ADD8E6;"; //sky blue
    }

    /**
     * Gets the image color for the Tritanopia color scheme.
     *
     * @return Color object representing the color used for images.
     */
    @Override
    public Color getImageColor() {
        return Color.web("#FFA07A"); //salmon
    }

    /**
     * Gets the text color for the Tritanopia color scheme.
     *
     * @return String representing the CSS text color.
     */
    @Override
    public String getTextColor() {
        return "-fx-text-fill: #D40E90;"; //pink
    }
}
