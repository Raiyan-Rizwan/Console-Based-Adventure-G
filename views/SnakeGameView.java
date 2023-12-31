package views;
import SnakeGameModel.Point;
import SnakeGameModel.SnakeGame;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Class SnakeGameView.
 * This is the Class that will visualize the snake game model.
 */
public class SnakeGameView {

    private static final int TILE_SIZE = 20; //size of each tile in pixels
    private static final int GRID_SIZE = 30; //size of the game grid
    private static final int GAME_SPEED = 90_000_000; //game speed in nanoseconds
    private final AdventureGameView adventureGameView; //Reference to the AdventureGameView
    public Stage stage; //for displaying the SnakeGameView
    private Canvas canvas; //to render the SnakeGameView graphics
    private SnakeGame snakeGame; //SnakeGame instance managing the game logic.
    public AnimationTimer gameLoop; //controls the continuous game loop
    public  boolean playerWon = false; //indicates whether the player has won the game.

    /**
     * Constructor for SnakeGameView.
     *
     * @param adventureGameView The associated AdventureGameView.
     */
    public SnakeGameView(AdventureGameView adventureGameView) {
        this.adventureGameView = adventureGameView;
        this.snakeGame = new SnakeGame(GRID_SIZE);
        initializeUI();
    }

    /**
     * Shows the SnakeGameView on the specified stage.
     *
     * @param primaryStage The primary stage for displaying the SnakeGameView.
     */
    public void show(Stage primaryStage) {
        // Set the stage for the SnakeGameView
        this.stage = primaryStage;

        //Create a new scene with the UI content
        this.stage.setScene(new Scene(createContent()));

        // Set the event handler for if user chooses to close the stage
        this.stage.setOnCloseRequest(event -> adventureGameView.handleSnakeGameResult(false));
        this.stage.show();
    }

    /**
     * Initializes the UI elements.
     */
    public void initializeUI() {
        this.canvas = new Canvas(GRID_SIZE * TILE_SIZE, GRID_SIZE * TILE_SIZE);

        // Allow the canvas to receive keyboard focus for handling key events
        this.canvas.setFocusTraversable(true);
    }

    /**
     * Creates the main content for SnakeGameView.
     *
     * @return The root StackPane containing the UI elements.
     */
    private StackPane createContent() {
        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        root.setAlignment(Pos.CENTER);

        // Add instruction button
        Button instructionButton = new Button("Instructions");
        instructionButton.setOnAction(e -> showInstructions());
        root.getChildren().add(instructionButton);
        root.setAlignment(Pos.TOP_CENTER);

        // Add play button
        Button playButton = new Button("Play");
        playButton.setOnAction(e -> initializeGameLoop());
        root.getChildren().add(playButton);
        StackPane.setAlignment(playButton, Pos.CENTER);

        // Handle keyboard input
        canvas.setOnKeyPressed(e -> {
            KeyCode direction = e.getCode();
            snakeGame.handleKeyPress(direction);
        });
         return root;
    }

    /**
     * Initializes the game loop for continuous updates.
     */
    private void initializeGameLoop() {
        // Create a new AnimationTimer to handle continuous updates
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                // Update and render the game graphics at regular intervals
                if (now - lastUpdate >= GAME_SPEED) {
                    update();
                    render();
                    lastUpdate = now;
                }
            }
        };
        Platform.runLater(() -> gameLoop.start());
        // Hide the "Play" button when loop starts
        ((Button) ((StackPane) stage.getScene().getRoot()).getChildren().get(2)).setVisible(false);
    }

    /**
     * Updates the game state.
     */
    private void update() {
        snakeGame.move();
        //check if snake collided with self or wall when move
        if (snakeGame.checkCollision()) {
            playerWon = false;
            backToAdventure();

        } else {
            // Check if the score is 13
            if (snakeGame.getScore() >= 13) {
                playerWon = true;
                backToAdventure();
            }
        }
    }

    /**
     * Renders the game graphics.
     */
    private void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw snake
        gc.setFill(Color.WHITE);
        snakeGame.getSnake().forEach(p -> gc.fillRect(p.getXCoord() * TILE_SIZE, p.getYCoord() * TILE_SIZE, TILE_SIZE, TILE_SIZE));

        // Draw food
        gc.setFill(Color.GREY);
        Point food = snakeGame.getFood();
        gc.fillRect(food.getXCoord() * TILE_SIZE, food.getYCoord() * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Display score
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + snakeGame.getScore(), 10, 20);
    }


    /**
     * Exits the SnakeGameView and returns to the AdventureGameView.
     */
    public void backToAdventure() {
        if (gameLoop != null) {
            gameLoop.stop(); // stop snakegame loop
        }
        this.stage.close();
        adventureGameView.handleSnakeGameResult(playerWon); //send results to the adventureGameView
    }

    /**
     * Displays instructions for playing the Snake Game.
     */
    private void showInstructions() {
        // Pause the game by stopping the AnimationTimer
        gameLoop.stop();

        // Display instructions in a ScrollPane
        TextArea instructionsTextArea = new TextArea();
        instructionsTextArea.setEditable(false);
        instructionsTextArea.setWrapText(true);
        instructionsTextArea.setText(
                "Snake Game Instructions:\n" +
                        "- Use arrow keys to control the snake.\n" +
                        "- Eat the red food to grow and score points.\n" +
                        "- Avoid colliding with the walls or yourself.\n" +
                        "- Achieve a score of 13 to win the game."
        );

        ScrollPane scrollPane = new ScrollPane(instructionsTextArea);
        scrollPane.setFitToWidth(true);

        StackPane instructionsPane = new StackPane(scrollPane);
        instructionsPane.setAlignment(Pos.CENTER);

        // Create a scene for instructions and show it
        Scene instructionsScene = new Scene(instructionsPane, 400, 300);
        Stage instructionsStage = new Stage();
        instructionsStage.setScene(instructionsScene);
        instructionsStage.setTitle("Instructions");
        instructionsStage.show();

        // Handle closing the instructions window
        instructionsStage.setOnCloseRequest(event -> {
            Platform.runLater(() -> gameLoop.start());
            // Resume the game by restarting the AnimationTimer
            canvas.requestFocus();
        });
    }
}
