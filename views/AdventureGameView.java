package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.Passage;
import AdventureModel.PassageTable;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import AdventureModel.RoomSound;
import Trolls.CompanionTroll;
import Trolls.SnakeGameTroll;
import Trolls.Troll;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event.EventHandler; //you will need this too!
import javafx.scene.AccessibleRole;

import java.io.*;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javax.swing.*;
import java.io.File;

import Trolls.TicTacToeTroll;

import ColorScheme.*;
import ColorScheme.ColorScheme;


import Trolls.TicTacToeTroll;

import java.util.*;
import javafx.scene.input.MouseEvent;
//Add mouse event library.

import java.util.Objects;



/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize your model.
 * You are asked to demo your visualization via a Zoom
 * recording. Place a link to your recording below.
 *
 * ZOOM LINK: https://utoronto-my.sharepoint.com/:v:/g/personal/daniel_sequeira_mail_utoronto_ca/EV_v3-mnho9Hvwde01NAY8YBjaSs2sujEfTxY9wd9niBPA?e=QCT42j
 * PASSWORD: sk1llissu3
 */
public class AdventureGameView {

    public AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton, colorContrastButton; //Buttons for saving, loading, help, and color contrast.
    Boolean helpToggle = false; //is help on display?
    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    VBox roomPane; //VBox to hold room-related elements
    ImageView roomImageView; //to hold current room images
    TextField inputTextField; //for user input

    VBox textEntry; //to organize text-related elements.
    Menu colorSchemeMenu; //Menu for selecting different color schemes.
    ScrollPane scO; //for displaying objects in the room
    ScrollPane scI; //for displaying objects in the inventory
    Label objLabel; //for indicating objects in the room
    Label invLabel; //for indicating objects in the inventory.
    Label commandLabel; //for displaying user commands
    ImageView objectIV; //to hold the image of a game object
    public ColorScheme currentColorScheme; //current color scheme applied to the UI
    Label displayTextLabel;
    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing
    // Attributes for Stopwatch feature
    Label timeLabel;  // to hold timer
    private SimpleIntegerProperty hours = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty minutes = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty seconds = new SimpleIntegerProperty(0);
    private boolean gameFinished = false;
    // set up text field for showing final time:
    public Label finalTimeTextLabel;
    // comboBox for changing font size and attribute for holding the current font size.
    public ComboBox<String> fontSizeComboBox = new ComboBox<>();
    private int currentFontSize = 16;
    // Trolls:
    private Troll[] trolls = {new CompanionTroll(roomDescLabel), new TicTacToeTroll(roomDescLabel), new SnakeGameTroll(roomDescLabel, this)};
    private Troll currentTroll = null;
    /**
     * Maps trolls onto boolean values corresponding to whether
     * they have been met in the game or not
     */
    private Map<Troll, Boolean> trollMap = new HashMap<>();

    public RoomSound roomSound = RoomSound.getInstance();


    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) {
        this.model = model;
        this.stage = stage;
        this.currentColorScheme = new DefaultColorScheme();
        for (Troll troll:trolls){
            trollMap.put(troll,false);
        }
        intiUI();
        roomSound.setAdventureName(model.getDirectoryName());
    }

    /**
     * Change the color scheme of the GUI.
     *
     * @param newColorScheme ColorScheme instance representing the new color scheme.
     */
    public void changeColorScheme(ColorScheme newColorScheme) {
        this.currentColorScheme = newColorScheme;
        applyColorScheme(currentColorScheme);
    }

    /**
     * Initialize the UI
     */
    public void intiUI() {

        // setting up the stage
        this.stage.setTitle("seque123's Adventure Game"); //Replace <YOUR UTORID> with your UtorID

        //Inventory + Room items
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));


        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints( 550 );
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow( Priority.SOMETIMES );
        row3.setVgrow( Priority.SOMETIMES );

        gridPane.getColumnConstraints().addAll( column1 , column2 , column1 );
        gridPane.getRowConstraints().addAll( row1 , row2 , row1 );

        // Buttons
        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 100, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();


        //Add sound cue to save button.


        //Add sound effect to save button.

        EventHandler<MouseEvent> soundIf1 = addSoundToButton("saveButton.mp3");

        saveButton.setOnMouseEntered(soundIf1);

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 100, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        //Add sound cue to load button.


        //Add sound effect to load button.

        EventHandler<MouseEvent> soundIf2 = addSoundToButton("loadButton.mp3");

        loadButton.setOnMouseEntered(soundIf2);

        helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        customizeButton(helpButton, 200, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();

        //Add sound cue to help button.

        colorContrastButton = new Button("Color Contrast");
        colorContrastButton.setId("ColorContrast");
        customizeButton(colorContrastButton, 200, 50);
        makeButtonAccessible(colorContrastButton, "Color Contrast Button", "This button changes the color contrast.", "Click it to change the color contrast for better visibility.");

        //menu items for different color schemes
        MenuItem deuteranopiaItem = new MenuItem("Deuteranopia");
        MenuItem protanopiaItem = new MenuItem("Protanopia");
        MenuItem tritanopiaItem = new MenuItem("Tritanopia");
        MenuItem defaultItem = new MenuItem("DefaultColorScheme");

        //event handlers to set color schemes
        deuteranopiaItem.setOnAction(e -> changeColorScheme(new DeuteranopiaColorScheme()));
        protanopiaItem.setOnAction(e -> changeColorScheme(new ProtanopiaColorScheme()));
        tritanopiaItem.setOnAction(e -> changeColorScheme(new TritanopiaColorScheme()));
        defaultItem.setOnAction(e -> changeColorScheme(new DefaultColorScheme()));

        //a menu for color schemes
        colorSchemeMenu = new Menu("Select Color Scheme");
        colorSchemeMenu.getItems().addAll(deuteranopiaItem, protanopiaItem, tritanopiaItem, defaultItem);

        //menu bar and adding the color scheme menu
        MenuBar colorContrastMenuBar = new MenuBar(colorSchemeMenu);
        colorContrastMenuBar.setStyle("-fx-background-color: transparent;");

        //adding the menu bar to the color contrast button
        colorContrastButton.setGraphic(colorContrastMenuBar);
        //Add sound effect to help button.
        EventHandler<MouseEvent> soundIf3 = addSoundToButton("helpButton.mp3");

        helpButton.setOnMouseEntered(soundIf3);

        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(saveButton, helpButton, loadButton, colorContrastButton,fontSizeComboBox );
        topButtons.setSpacing(10);
        topButtons.setAlignment(Pos.CENTER);

        inputTextField = new TextField();
//        inputTextField.setFont(new Font("Arial", currentFontSize)); // changed to currentFontSize
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        addTextHandlingEvent(); //attach an event to this input field

        //labels for inventory and room items
        objLabel =  new Label("Objects in Room");
        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle( currentColorScheme.getTextColor());
        objLabel.setFont(new Font("Arial", 16));

        invLabel =  new Label("Your Inventory");
        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle( currentColorScheme.getTextColor());
        invLabel.setFont(new Font("Arial", 16));
//        objLabel.setFont(new Font("Arial", currentFontSize)); // changed to <currentFontSize>

        invLabel =  new Label("Your Inventory");
        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle("-fx-text-fill: white;");
//        invLabel.setFont(new Font("Arial", currentFontSize)); // changed to <currentFontSize>

        //add all the widgets to the GridPane
        gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
        gridPane.add( topButtons, 1, 0, 1, 1 );  // Add buttons
        gridPane.add( invLabel, 2, 0, 1, 1 );  // Add label

        commandLabel = new Label("What would you like to do?");
        commandLabel.setStyle(currentColorScheme.getTextColor());
        commandLabel.setFont(new Font("Arial", 16));
//        commandLabel.setFont(new Font("Arial", 16)); will change dynamically


        updateScene(""); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms

        // adding the text area and submit button to a VBox
        textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add( textEntry, 0, 2, 3, 1 );

        // Render everything
        var scene = new Scene( gridPane ,  1000, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();

        // Create the Label for the final time
        finalTimeTextLabel = new Label();
        finalTimeTextLabel.setFont(new Font("Arial", 30));
        finalTimeTextLabel.setStyle("-fx-text-fill: black;");
        finalTimeTextLabel.prefWidth(647);
        finalTimeTextLabel.prefHeight(647);
        finalTimeTextLabel.setTextOverrun(OverrunStyle.CLIP);
        finalTimeTextLabel.setWrapText(true);
        finalTimeTextLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        finalTimeTextLabel.setBorder(new Border(new BorderStroke(Color.SNOW, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        finalTimeTextLabel.setAlignment(Pos.CENTER);

        // creating a ComboBox for editing font size
        fontSizeComboBox.getItems().addAll("16", "18", "20", "22", "24");
        fontSizeComboBox.setValue("16");  //set default font size
        fontSizeComboBox.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("Font Size: " + item);
                }
            }
        });

        fontSizeComboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("Font Size: " + item);
                }
            }
        });

        // Set up the event handler
        fontSizeComboBox.setOnAction(e -> {String selectedValue = fontSizeComboBox.getValue();
            if (selectedValue != null) {
                // Extract the font size from the selected value and set the font size
                int fontSize = Integer.parseInt(selectedValue);
                setFontSize(fontSize);
            }
        });
//        fontSizeComboBox.setOnAction(e -> setFontSize(Integer.parseInt(fontSizeComboBox.getValue())));
    }

    //Method to play sound file when hovering over node.

    /**
     * Apply the specified color scheme to the GUI elements.
     *
     * @param colorScheme ColorScheme instance representing the color scheme to apply.
     */
    private void applyColorScheme(ColorScheme colorScheme) {
        // Apply the color scheme to buttons
        saveButton.setStyle(colorScheme.getButtonColor() + colorScheme.getTextColor());
        loadButton.setStyle(colorScheme.getButtonColor() + colorScheme.getTextColor());
        helpButton.setStyle(colorScheme.getButtonColor() + colorScheme.getTextColor());
        colorContrastButton.setStyle(colorScheme.getButtonColor() + colorScheme.getTextColor());
        colorSchemeMenu.setStyle(colorScheme.getButtonColor() + colorScheme.getTextColor());

        // scroll pane for objects and inventory color change from initial
        scO.setStyle("-fx-background:" + colorScheme.getBackgroundColor() + ";" + "-fx-background-color:transparent;");
        scI.setStyle("-fx-background:" + colorScheme.getBackgroundColor() + ";" + "-fx-background-color:transparent;");

        // pane color change
        gridPane.setStyle("-fx-background-color:" + colorScheme.getBackgroundColor());
        roomPane.setStyle("-fx-background-color:" + colorScheme.getBackgroundColor());



        // text color change
        roomDescLabel.setStyle(colorScheme.getTextColor());
        objLabel.setStyle(currentColorScheme.getTextColor());
        invLabel.setStyle(currentColorScheme.getTextColor());
        commandLabel.setStyle(currentColorScheme.getTextColor());
        textEntry.setStyle("-fx-background-color:" + colorScheme.getBackgroundColor());
        roomImageView.setStyle(colorScheme.getBackgroundColor());

        // room image color change,
        // taking coordinates and dimensions of the room image to put a color input region (rectangle) on top of it,
        ColorInput colorInput = new ColorInput(
                0, 0, 400, 300,
                colorScheme.getImageColor()
        );
        Blend blend = new Blend(BlendMode.MULTIPLY);
        blend.setTopInput(colorInput);

        // Apply the Blend to the ImageView
        roomImageView.setEffect(blend);

        // objects  in room color change, when drop in inventory should go back to default color
        colorInput = new ColorInput(
                -4, -4, 100, 80,
                colorScheme.getImageColor()
        );
        blend = new Blend(BlendMode.MULTIPLY);
        blend.setTopInput(colorInput);
        objectIV.setEffect(blend);
    }

    //Method to play sound file if cursor enters node.
    //@param soundString file of sound to be played

    public EventHandler<MouseEvent> addSoundToButton(String soundString){
        EventHandler<MouseEvent> readSound = new EventHandler<MouseEvent>(){
            public void handle(MouseEvent MouseEntered){
                String directName = model.getDirectoryName();
                String soundURL = "./"+ directName + "/sounds/" + soundString;

                Media sound = new Media(new File(soundURL).toURI().toString());
                MediaPlayer player = new MediaPlayer(sound);
                player.play();
            }
        };
        return (readSound);
    }


    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", currentFontSize)); // changed to <currentFontSize>
        inputButton.setStyle("-fx-background-color: #17871b;" + currentColorScheme.getTextColor()+';');
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute
     *
     * Your event handler should respond when users
     * hits the ENTER or TAB KEY. If the user hits
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped
     * string to submitEvent for processing.
     *
     * If the user hits the TAB key, move the focus
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        EventHandler<KeyEvent> forEnterOrTab = new EventHandler() {
            @Override
            public void handle(Event event) {
                if (event instanceof KeyEvent){
                    String keyType = event.getEventType().getName();
                    KeyCode codeForKey = ((KeyEvent) event).getCode();
                    if (keyType.equals("KEY_PRESSED")){
                        if (codeForKey == KeyCode.ENTER){
                            String inputTextStripped = inputTextField.getText().strip();
                            submitEvent(inputTextStripped);
                        } else if (codeForKey == KeyCode.TAB) {
                            gridPane.requestFocus();
                        }
                    }
                }
            }
        };
        inputTextField.setOnKeyPressed(forEnterOrTab);

    }


    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    private void submitEvent(String text) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        }

        Troll currTroll = getCurrentTroll();

        if (currTroll != null) {
            if (text.isEmpty()) {
                currTroll.nextStage();
                return;
            }
            else if (currTroll.expectingAnswer()){
                System.out.println("now");
                if (currTroll.actionResponse(text)) {
                    PauseTransition pause = new PauseTransition(Duration.seconds(5));
                    pause.setOnFinished(event -> {
                        AdventureObject takenObject = model.player.getCurrentRoom().getObject(currTroll.getName());
                        model.player.getCurrentRoom().removeGameObject(takenObject);

                        model.player.addToInventory(takenObject);
                        updateItems();
                        currTroll.exit(this);
                    });
                    pause.play();
                    return;
                }
                else {
                    PauseTransition pause = new PauseTransition(Duration.seconds(5));
                    pause.setOnFinished(event -> {
                        currTroll.exit(this);
                    });
                    pause.play();
                    return;
                }
            }
        }

        else if (text.equalsIgnoreCase("BYE")){
            currTroll.exit(this);
            return;
        }
        
        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            if (!gameFinished) {
                updateScene("");
                updateItems();

                // Stop the stopwatch (method is around line 623)
                timeline.stop();
                gameFinished = true;

                // before displaying the finalTimeTextField, we clear out the current gridpane
                gridPane.getChildren().clear();

                int finalHours = hours.get();
                int finalminutes = minutes.get();
                int finalseconds = seconds.get();

                String finalTime = String.format("%02d:%02d:%02d", finalHours, finalminutes, finalseconds);

                // Load Stats from stats.txt (Time taken)
                String loadedStats = readTimeFromFile();
                finalTimeTextLabel.setText(loadedStats + "\n" + "Total Time taken for " + model.player.getPlayerName() + ": " + finalTime);

                // save stats to file (Do After Loading Stats and setText function for finalTimeTextLabel)
                saveTimetoFile("Player Name: " + model.player.getPlayerName() + ". Total Time Taken: " + finalTime);

                // create a scroll pane to hold the label showing the final stats
                ScrollPane statsPane = new ScrollPane();
                statsPane.setContent(finalTimeTextLabel);
                statsPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                statsPane.setBackground(new Background(new BackgroundFill(
                        Color.valueOf("#000000"),
                        new CornerRadii(0),
                        new Insets(0)
                )));
                gridPane.add(statsPane, 1, 1, 1, 1);
            }
            PauseTransition pause = new PauseTransition(Duration.seconds(60));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();

        } else if (output.equals("FORCED")) {
            //write code here to handle "FORCED" events!
            //Your code will need to display the image in the
            //current room and pause, then transition to
            //the forced room.
            updateScene("");
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> submitEvent("FORCED"));
            pause.play();
        }
    }


    /**
     * showCommands
     * __________________________
     *
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the
     * current room.
     */
    private void showCommands() {
        PassageTable currMotionTable = model.player.getCurrentRoom().getMotionTable();
        String movesText = "You can move in these directions:\n";
        for (Passage path: currMotionTable.passageTable){
            if (movesText == "You can move in these directions:\n"){
                movesText += path.getDirection().toUpperCase();
            }else{
                movesText += "," + path.getDirection().toUpperCase();
            }

        }
        formatText(movesText);
    }


    /**
     * updateScene
     * __________________________
     *
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     *
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {
//        getRoomImage(); //get the image of the current room
        ColorInput colorInput = new ColorInput(
                0, 0, 400, 300,
                currentColorScheme.getImageColor()
        );
        Blend blend = new Blend(BlendMode.MULTIPLY);
        blend.setTopInput(colorInput);

//        getRoomImage(); //get the image of the current room
//        formatText(textToDisplay); //format the text to display
//        roomDescLabel.setPrefWidth(500);
//        roomDescLabel.setPrefHeight(500);
//        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
//        roomDescLabel.setWrapText(true);
//        roomPane = new VBox(roomImageView,roomDescLabel);
//        roomPane.setPadding(new Insets(10));
//        roomPane.setAlignment(Pos.TOP_CENTER);
//        gridPane.add(roomPane, 1, 1);
//        stage.sizeToScene();

        //Is a Troll move being made?
        if(model.trollMove){
            roomDescLabel.setText("The path is blocked? Worry not. Allow me to use my Troll strength to get us through.");
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> {
                model.trollMove = false;
                updateScene("");
            });
            pause.play();
        }

        else {

            getRoomImage(); //get the image of the current room
            formatText(textToDisplay); //format the text to display
            roomDescLabel.setPrefWidth(500);
            roomDescLabel.setPrefHeight(500);
            roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
            roomDescLabel.setWrapText(true);
            VBox roomPane = new VBox(roomImageView, roomDescLabel);
            roomPane.setPadding(new Insets(10));
            roomPane.setAlignment(Pos.TOP_CENTER);
            roomPane.setStyle("-fx-background-color: #000000;");

            gridPane.add(roomPane, 1, 1);
            stage.sizeToScene();


            //finally, articulate the description
            if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
        }
        roomSound.updateSounds(model.player.getCurrentRoom());
    }

    /**
     * formatText
     * __________________________
     *
     * Format text for display.
     *
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle(currentColorScheme.getTextColor());
        roomDescLabel.setFont(new Font("Arial", currentFontSize)); // changed to <currentFontSize>
        roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * Sets the font size for the roomDescLabel and other specified nodes
     * @param fontSize
     */
    public void setFontSize(int fontSize){
        currentFontSize = fontSize;
        //formatText(""); This would only change the font size of the roomDescLabel. We need to change everything else
        // we will call <setFontofObject> to change the font size of specific objects in our GUI.
        formatText("");
        timeLabel.setFont(new Font("Arial", fontSize));
        inputTextField.setFont(new Font("Arial", fontSize));
        if(fontSize >= 20) {
            invLabel.setFont(new Font("Arial", 20));
            objLabel.setFont(new Font("Arial", 20));
        } else {
            invLabel.setFont(new Font("Arial", currentFontSize));
            objLabel.setFont(new Font("Arial", currentFontSize));
        }
        displayTextLabel.setFont(new Font("Arial", fontSize));
        commandLabel.setFont(new Font("Arial", fontSize));
    }

    /**
     * getRoomImage
     * __________________________
     *
     * Get the image for the current room and place
     * it in the roomImageView
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = "Games/TinyGame" + "/room-images/" + roomNumber + ".png";

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);
        ColorInput colorInput = new ColorInput(
                0, 0, 400, 300,
                currentColorScheme.getImageColor()
        );
        Blend blend = new Blend(BlendMode.MULTIPLY);
        blend.setTopInput(colorInput);

        // Apply the Blend to the ImageView
        roomImageView.setEffect(blend);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updateItems
     * __________________________
     *
     * This method is partially completed, but you are asked to finish it off.
     *
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     *
     * Images of each object are in the assets
     * folders of the given adventure game.
     */
    public void updateItems() {

        //write some code here to add images of objects in a given room to the objectsInRoom Vbox
        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        //please use setAccessibleText to add "alt" descriptions to your images!
        //the path to the image of any is as follows:
        //this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";


        scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setStyle("-fx-background:" + currentColorScheme.getBackgroundColor() + ";" + "-fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

        scI = new ScrollPane(objectsInInventory);
        scI.setFitToWidth(true);
        scI.setStyle("-fx-background:" + currentColorScheme.getBackgroundColor() + ";" + "-fx-background-color:transparent;");
        gridPane.add(scI,2,1);

        //Make sure both Vboxes are empty
        objectsInRoom.getChildren().clear();
        objectsInInventory.getChildren().clear();

        for (AdventureObject advObj: model.player.getCurrentRoom().objectsInRoom){
            for (Troll troll: Arrays.asList(trolls)){
                if((advObj.getName().equals(troll.getName())) && !trollMap.get(troll)) {
                    trollMap.put(troll, true);
                    stopArticulation();
                    setCurrentTroll(troll);
                    getCurrentTroll().nextStage();
                }
            }
        }

        //VBOX METHOD Load Objects in Room
        ArrayList<AdventureObject> roomObjects = model.player.getCurrentRoom().objectsInRoom;
        for (AdventureObject obj: roomObjects){
            String objectName = obj.getName();
            Image objectImage = new Image("Games/TinyGame" + "/objectImages/" + objectName + ".jpg");

            //ImageView objectIV = new ImageView(objectImage);
            objectIV = new ImageView(objectImage);
            //preserve ration of image
            float objectImageRatio = (float)(objectImage.getHeight() / objectImage.getWidth());
            float newObjectImageHeight = 100*objectImageRatio;
            objectIV.preserveRatioProperty();
            objectIV.setFitWidth(100);
            objectIV.setFitHeight(newObjectImageHeight);

            ColorInput colorInput = new ColorInput(
                    -4, -4, 100, 80,
                    currentColorScheme.getImageColor()
            );
            Blend blend = new Blend(BlendMode.MULTIPLY);
            blend.setTopInput(colorInput);
            objectIV.setEffect(blend);

            Label objectLabel = new Label(objectName);
            Button objectButton = new Button("", objectIV);
            VBox objectVB = new VBox(objectLabel, objectButton);
            objectVB.setAccessibleText("Button to take " + objectName);
            //check if the object already has a Vbox made


            boolean notHere = true;
            objectVB.setId(objectName);
            for (Node child: objectsInRoom.getChildren()){
                if (child instanceof VBox){
                    if(child.getId() == objectName){
                        notHere= false;
                    }
                }
            }
            if (notHere){
                objectsInRoom.getChildren().add(objectVB);
                //check if it is still in the other ScrollPane

                for (Node child: objectsInInventory.getChildren()){
                    if (child instanceof VBox){
                        if(child.getId() == objectName){
                            objectsInInventory.getChildren().remove(child);
                        }
                    }
                }


            }





            //EventHandler for when Button is pressed (Object is Taken from Room)
//
            EventHandler<ActionEvent> TakenEvent = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    
                    AdventureObject takenObject = model.player.getCurrentRoom().getObject(objectName);
                    model.player.getCurrentRoom().removeGameObject(takenObject);

                    model.player.addToInventory(takenObject);
                    updateItems();
                }
            };

            objectButton.setOnAction(TakenEvent);




        }


        //Load Objects in Inventory
        ArrayList<AdventureObject> inventoryObjects = model.player.inventory;
        for (AdventureObject obj: inventoryObjects){
            String objectName = obj.getName();
            Image objectImage = new Image("Games/TinyGame" + "/objectImages/" + objectName + ".jpg");
            ImageView objectIV = new ImageView(objectImage);
            //preserve ration of image
            float objectImageRatio = (float)(objectImage.getHeight() / objectImage.getWidth());
            float newObjectImageHeight = 100*objectImageRatio;
            objectIV.preserveRatioProperty();
            objectIV.setFitWidth(100);
            objectIV.setFitHeight(newObjectImageHeight);
            objectIV.setAccessibleText(objectName);
            Label objectLabel = new Label(objectName);
            Button objectButton = new Button("",objectIV);

            objectButton.setTextAlignment(TextAlignment.CENTER);
            objectButton.setAccessibleText(objectName);
            VBox objectVB = new VBox(objectLabel, objectButton);
            objectVB.setAccessibleText("Button to drop " + objectName);
            boolean notHere = true;
            objectVB.setId(objectName);
            for (Node child: objectsInInventory.getChildren()){
                if (child instanceof VBox){
                    if(child.getId() == objectName){
                        notHere= false;
                    }
                }
            }
            if (notHere){
                objectsInInventory.getChildren().add(objectVB);
                //check if it is still in the other ScrollPane

                for (Node child: objectsInRoom.getChildren()){
                    if (child instanceof VBox){
                        if(child.getId() == objectName){
                            objectsInRoom.getChildren().remove(child);
                        }
                    }
                }

            }


            //EventHandler for when Button is pressed (Object Dropped from Inventory)

            EventHandler<ActionEvent> buttonDroppedEvent = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    model.player.dropObject(objectName);
                    model.player.getCurrentRoom().addGameObject(obj);

                    updateItems();



                }
            };

            objectButton.setOnAction(buttonDroppedEvent);

        }

        StopWatch();


    }

    // Render Stopwatch
    public void StopWatch() {

        // the label which will hold the stopwatch
        timeLabel = new Label();
        timeLabel.textProperty().bind(hours.asString("%02d").concat(":").concat(minutes.asString("%02d")).concat(":").concat(seconds.asString("%02d")));
//        timeLabel.setFont(new Font("Arial", currentFontSize)); // replaced with <currentFontSize>
        timeLabel.setStyle("-fx-text-fill: white;");
        timeLabel.prefHeight(200);
        timeLabel.prefWidth(200);
        timeLabel.setAlignment(Pos.CENTER);

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        gridPane.add(timeLabel, 0, 1);
    }

    /*
    runs the timer by setting up a call to updateTimer each second (This set up needs to be outside the Stopwatch
    method to prevent the setup from being called again and consequently increasing the duration of seconds):
     */
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    updateTimer();
                }
            })
    );

    // Updates the Time
    private void updateTimer() {
        seconds.set(seconds.get() + 1);
        if (seconds.get() == 60) {
            seconds.set(0);
            minutes.set(minutes.get() + 1);
            if (minutes.get() == 60) {
                minutes.set(0);
                hours.set(hours.get() + 1);
                if (hours.get() == 60) {
                    hours.set(0);
                }
            }
        }
    }


    // Edited to include for the FileWriter
    private void saveTimetoFile(String time) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Games/Saved/scores.txt", true))){
            writer.println(time);  // edited to include the player's name as well.
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String readTimeFromFile(){
        StringBuilder stats = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("Games/Saved/scores.txt"))){
            String line;
            while((line = reader.readLine()) != null) {
                stats.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats.toString();
    }

//    public void addStartStage() {
//
//        System.out.println("check point 2");
//        for (AdventureObject advObj: model.player.getCurrentRoom().objectsInRoom){
//            System.out.println("blabla");
//            for (Troll troll: Arrays.asList(trolls)){
//                System.out.println(advObj.getName());
//                System.out.println(advObj.getName());
//                if(advObj.getName().equals(troll.getName())) {
//                    stopArticulation();
//                    setCurrentTroll(troll);
//                    getCurrentTroll().nextStage();
//                }
//            }
//        }
//
//
//    }

    /*
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        if(helpToggle){

            //Looking through the children of gridPane and looking for
            // whichever are visible in cell (1,1)
            for (Node child: gridPane.getChildren()){
                if ((gridPane.getRowIndex(child) == 1) && (gridPane.getColumnIndex(child)) == 1){
                    if (child.isVisible()){
                        child.setVisible(false);
                    }
                }
            }
            //re-enabling the Room image and the Room Desc
            updateScene("");
            roomDescLabel.setVisible(true);
            helpToggle = !helpToggle;

        } else {
            //disabling the Room image and the Room Desc
            roomImageView.setVisible(false);
            roomDescLabel.setVisible(false);
            //Creating new label to house the help text
            String displayText = model.getInstructions();
            displayTextLabel = new Label();
            displayTextLabel.setText(displayText);
            //formatting text

            displayTextLabel.setStyle(currentColorScheme.getTextColor() +
                    "-fx-background-color: " + currentColorScheme.getBackgroundColor() + ";");
            displayTextLabel.setFont(new Font("Arial", 16));


//            displayTextLabel.setFont(new Font("Arial", currentFontSize)); //replaced with <currentFontSize>
            displayTextLabel.setWrapText(true);
            //formatting background
            displayTextLabel.setBackground(new Background(new BackgroundFill(
                    Color.valueOf("#000000"),
                    new CornerRadii(0),
                    new Insets(0)
            )));
            displayTextLabel.setMaxWidth(645);
            //Creating ScrollPane to enable scrolling
            // functionality to see the full text
            ScrollPane helpPane = new ScrollPane();
            helpPane.setContent(displayTextLabel);
            helpPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            helpPane.setBackground(new Background(new BackgroundFill(
                    Color.valueOf("#000000"),
                    new CornerRadii(0),
                    new Insets(0)
            )));


            //Inserting the new ScrollPane into the cell (1,1)
            //in the GridPane
            gridPane.add(helpPane, 1, 1);
            helpToggle = true;
            //trying to draw it



        }

    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();

        });
    }

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }


    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisited()) musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3" ;
        else musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3" ;
        musicFile = musicFile.replace(" ","-");

        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;

    }

    /**
     * This method stops articulations
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }

    public void setCurrentTroll(Troll troll){
        currentTroll = troll;
    }

    public Troll getCurrentTroll(){
        return currentTroll;
    }

    /**
     * Handles the result of a Snake game in the AdventureGame, updating the room description label accordingly.
     *
     * @param playerWins True if the player wins the game, false otherwise.
     */
    public void handleSnakeGameResult(boolean playerWins) {
        // Display a temporary message while processing the game result
        roomDescLabel.setText("HOLD ON A MINUTE...");
        if (playerWins) {
            // If the player wins, display a victory message after a delay in actionresponse
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> {
                roomDescLabel.setText("Type: :)");
            });
            pause.play();
        } else{
            // display a game over message if lose.
            roomDescLabel.setText("GAME OVER, PRESS ENTER AND TRY AGAIN");
        }
    }


}
