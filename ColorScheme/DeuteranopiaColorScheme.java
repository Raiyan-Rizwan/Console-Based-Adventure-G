package ColorScheme;

import javafx.scene.paint.Color;

/**
 * DeuteranopiaColorScheme implementation of the ColorScheme interface.
 * This class provides color values optimized for adventure game users with Deuteranopia color vision deficiency.
 */
public class DeuteranopiaColorScheme implements ColorScheme {

    /**
     * Get the button color suitable for Deuteranopia.
     *
     * @return String representing the CSS background color for buttons.
     */
    @Override
    public String getButtonColor() {
        return " -fx-background-color: #DAA520;"; // shade of golden or dark yellow for better visibility
    }

    /**
     * Get the background color suitable for Deuteranopia.
     *
     * @return String representing the hexadecimal background color for the overall background.
     */
    @Override
    public String getBackgroundColor() {
        return  "#E0E0E0"; //Light gray for better contrast
    }

    /**
     * Get the image color suitable for Deuteranopia.
     *
     * @return Color object representing the color used for images.
     */
    @Override
    public Color getImageColor() {
        return Color.web("#6699CC"); //shade of blue
    }

    /**
     * Get the text color suitable for Deuteranopia.
     *
     * @return String representing the CSS text color.
     */
    @Override
    public String getTextColor() {
        return "-fx-text-fill: #000000;"; //black
    }
}

