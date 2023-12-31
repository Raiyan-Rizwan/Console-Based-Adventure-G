package ColorScheme;
import javafx.scene.paint.Color;

/**
 * An interface defining color schemes for the Adventure Game GUI for users with low vision.
 */
public interface ColorScheme {

    /**
     * Get the color code for buttons in the GUI.
     *
     * @return String representing the CSS background color for buttons.
     */
    String getButtonColor();

    /**
     * Get the background color code for the GUI.
     *
     * @return String representing the hexadecimal background color for the overall background.
     */
    String getBackgroundColor();

    /**
     * Get the text color code for the GUI.
     *
     * @return String representing the CSS text color.
     */
    String getTextColor();

    /**
     * Get the color for images in the GUI.
     *
     * @return Color object representing the color used for images.
     */
    Color getImageColor();

}
