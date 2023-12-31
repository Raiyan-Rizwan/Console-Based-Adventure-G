package ColorScheme;

import javafx.scene.paint.Color;

/**
 * DefaultColorScheme implementation of the ColorScheme interface.
 * This class provides default green, black and white color values for the Adventure Game GUI.
 */
public class DefaultColorScheme implements ColorScheme {

    /**
     * Get the default green color code for buttons.
     *
     * @return String representing the CSS background color for buttons.
     */
    @Override
    public String getButtonColor() {
        return "-fx-background-color: #17871b;"; //green
    }

    /**
     * Get the default black background color code for the GUI.
     *
     * @return The default overall black background color code in hexadecimal.
     */
    @Override
    public String getBackgroundColor() {
        return "#000000;";
    }

    /**
     * Get the default white text color code for the GUI.
     *
     * @return String representing the CSS text color.
     */
    @Override
    public String getTextColor() {
        return "-fx-text-fill: #FFFFFF;" ;
    }

    /**
     * Get the default color for images in the GUI.
     *
     * @return Color object representing the color used for images.
     */
    @Override
    public Color getImageColor() {
        return Color.web("#17871b");
    }
}
