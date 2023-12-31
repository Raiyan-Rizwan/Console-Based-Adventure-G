package ColorScheme;

import javafx.scene.paint.Color;

/**
 * ProtanopiaColorScheme class implements the ColorScheme interface
 * Defines color schemes tailored for users with protanopia.
 */
public class ProtanopiaColorScheme implements ColorScheme {

    /**
     * Gets the button color for the Protanopia color scheme.
     *
     * @return String representing the CSS background color for buttons.
     */
    @Override
    public String getButtonColor() {
        return " -fx-background-color: #0066cc;"; // a shade of blue that pops
    }

    /**
     * Gets the background color for the Protanopia color scheme.
     *
     * @return String representing the hexadecimal background color for the overall background.
     */
    @Override
    public String getBackgroundColor() {
        return "#ffebcd;"; // light peach colour
    }

    /**
     * Gets the image color for the Protanopia color scheme.
     *
     * @return Color object representing the color used for images.
     */
    @Override
    public Color getImageColor() {
        return Color.web("#0099cc"); // warm blue colour
    }

    /**
     * Gets the text color for the Protanopia color scheme.
     *
     * @return String representing the CSS text color.
     */
    @Override
    public String getTextColor() {
        return "-fx-text-fill:#000000;"; //black
    }
}
