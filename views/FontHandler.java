package views;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class FontHandler implements EventHandler<MouseEvent> {
    private ComboBox fontSizeComboBox;
    private AdventureGameView adventureView;

    /**
     * Constructor
     * @param view refers to the AventureGameView object
     */
    public FontHandler(AdventureGameView view, ComboBox<String> fontSizeCombo){
        fontSizeComboBox = fontSizeCombo;
        adventureView = view;

        // set default fontSize of the ComboBox to 16
        fontSizeComboBox.getSelectionModel().select("16");
    }

    /**
     * Handle a mouse event (i.e. a button click)!  This routine will need to:
     *
     * @param mouseEvent
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        String selectedFontSize = (String) fontSizeComboBox.getSelectionModel().getSelectedItem();
        int fontSize = Integer.parseInt(selectedFontSize);
        adventureView.setFontSize(fontSize);
    }
}
