package SnakeGameModel;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class SnakeGame that represents the logic for the Snake Game.
 * Handles the movement of the snake, collision detection, scoring, and food generation.
 */
public class SnakeGame {

    private static final int INITIAL_LENGTH = 1; //initial length of snake when game begins
    private List<Point> snake; //holds segments of the snake.
    private Point food; //position of food in game
    private final int gridSize; //size of game grid
    public  int score; //number of points player earns
    private KeyCode currentDirection; //current direction of the snake, based on the player's key presses.

    /**
     * Creates a new instance of SnakeGame with the specified grid size.
     *
     * @param gridSize The size of the game grid.
     */
    public SnakeGame(int gridSize) {
        this.gridSize = gridSize;
        initializePositions();
    }

    /**
     * Initializes the game by setting up the initial snake position and generating the first food.
     */
    private void initializePositions() {
        snake = new ArrayList<>();
        currentDirection = KeyCode.RIGHT;

        // Initialize snake at the center of the grid
        int startX = gridSize / 2;
        int startY = gridSize / 2;
        for (int i = 0; i < INITIAL_LENGTH; i++) {
            snake.add(new Point(startX - i, startY));
        }
        generateFood();
    }

    /**
     * Gets the current score of the game.
     *
     * @return The score.
     */
    public  int getScore() {
        return score;
    }

    /**
     * Handles the player's key presses to change the direction of the snake.
     *
     * @param direction The direction indicated by the key press.
     */
    public void handleKeyPress(KeyCode direction) {
        // Ensures that the snake cannot reverse its direction
        if ((direction == KeyCode.LEFT && currentDirection != KeyCode.RIGHT) ||
                (direction == KeyCode.RIGHT && currentDirection != KeyCode.LEFT) ||
                (direction == KeyCode.UP && currentDirection != KeyCode.DOWN) ||
                (direction == KeyCode.DOWN && currentDirection != KeyCode.UP)) {
            currentDirection = direction;
        }
    }

    /**
     * Moves the snake in the current direction, handles collisions with food, and updates the score.
     */
    public void move() {
        Point head = snake.get(0); //snake's head

        // Move in the pressed direction
        Point newHead;
        switch (currentDirection) {
            case UP:
                newHead = new Point(head.getXCoord(), head.getYCoord() - 1); //move snake  head up
                break;
            case DOWN:
                newHead = new Point(head.getXCoord(), head.getYCoord() + 1);
                break;
            case LEFT:
                newHead = new Point(head.getXCoord() - 1, head.getYCoord());
                break;
            case RIGHT:
                newHead = new Point(head.getXCoord() + 1, head.getYCoord());
                break;
            default:
                newHead = head;
        }

        // Add the new head to the front of the snake
        snake.add(0, newHead);

        // Check for collision with food
        if (newHead.equals(food)) {
            generateFood();
            score++;
        } else {
            // Remove the tail of the snake
            snake.remove(snake.size() - 1);
        }
    }

    /**
     * Checks wheather snake collides with walls or with itself.
     *
     * @return True if a collision is detected, otherwise false.
     */
    public boolean checkCollision() {
        // Check collision with walls
        Point head = snake.get(0);
        if (head.getXCoord() < 0 || head.getXCoord() >= gridSize || head.getYCoord() < 0 || head.getYCoord() >= gridSize) {
            return true;
        }

        // Check collision with self
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Generates a new food position that is not on the snake.
     */
    public void generateFood() {
        Random random = new Random();
        int xVal, yVal; //store the coordinates of the new food position

        // Keep generating a new food position until it is not on the snake
        do {
            xVal = random.nextInt(gridSize);
            yVal = random.nextInt(gridSize);
        } while (snake.contains(new Point(xVal, yVal)));

        // Set the randomly generated coordinates as the position of the food
        food = new Point(xVal, yVal);
    }

    /**
     * Gets the current positions of the snake segments.
     *
     * @return A list of Points representing the snake segments.
     */
    public List<Point> getSnake() {
        return snake;
    }

    /**
     * Gets the current position of the food.
     *
     * @return The Point representing the food position.
     */
    public Point getFood() {
        return food;
    }
}
