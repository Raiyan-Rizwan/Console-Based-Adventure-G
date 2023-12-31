package views;

import AdventureModel.AdventureGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class AdventureGameStartView {
    Stage nameEntryStage = new Stage();

    // UI elements for name entry
    Label nameLabel = new Label("Enter Your Name:");
    TextField nameTextField = new TextField();
    Button submitButton = new Button("Submit");
    // Layout for name entry
    VBox nameEntryLayout = new VBox(10);
    // create scene
    Scene nameEntryScene;
    private AdventureGameView adventureGameView;

    // constructor
    public AdventureGameStartView(AdventureGame model){
        nameEntryStage.setTitle("Enter Your Name");

        // styling
        nameEntryLayout.setStyle("-fx-background-color: #1E1E1E;");
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18;");
        nameTextField.setStyle("-fx-font-size: 14;");
        submitButton.setStyle("-fx-font-size: 16;");

        // Padding
        nameEntryLayout.setPadding(new Insets(20));
        nameEntryLayout.setAlignment(Pos.CENTER);

        // altering the entry layout:
        nameEntryLayout.getChildren().addAll(nameLabel, nameTextField, submitButton);
        nameEntryLayout.setAlignment(Pos.CENTER);
        // alter the scene
        nameEntryScene = new Scene(nameEntryLayout, 600, 300);
        // set scene
        nameEntryStage.setScene(nameEntryScene);
        // submit button handler
        submitButton.setOnAction(e -> {
            String playerName = nameTextField.getText();
            if(!playerName.isEmpty()){
                model.getPlayer().setPlayerName(playerName);
                nameEntryStage.close();
            }
        });
        nameEntryStage.show();

    }
}
