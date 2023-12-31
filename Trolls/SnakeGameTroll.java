package Trolls;
import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.AdventureGameView;
import views.SnakeGameView;

/**
 * SnakeGameTroll class that Implements the Troll interface.
 * This troll challenges the player to play the Snake Game.
 */
public class SnakeGameTroll implements Troll {

    private AdventureGameView adventureGameView;
    int stage = 0;
    Label roomDesc;
    String name = "SNAKEGAME_TROLL";
    private String[] lines; //to hold intro lines
    private String[] lines2; //to hold instructions lines

    /**
     * Constructor for SnakeGameTroll.
     *
     * @param roomDsc            The label used to display room descriptions.
     * @param adventureGameView The AdventureGameView associated with this troll.
     */
    public SnakeGameTroll(Label roomDsc, AdventureGameView adventureGameView) {
        roomDesc = roomDsc;
        this.adventureGameView = adventureGameView;
        String[] intro = {"I AM THE SNAKE TROLL! \n" +
                "I CHALLENGE YOU TO PLAY THE SNAKEGAME!\n"

        };
        String[] instruction = {
                "Use the arrow keys to control the snake's direction.\n" +
                        "Your goal is to eat the red food to grow longer.\n" +
                        "Be careful not to run into the walls or yourself! \n" +
                        "The game ends if the snake collides with the boundaries or itself.  \n" +
                        "Achieve a score of 13 or more to successfully complete the challenge.  \n" +
                        "PRESS ENTER TO BEGIN! \n"
        };

        lines = intro;
        lines2 = instruction;

    }

    /**
     * Gets the name of the troll.
     *
     * @return The name of the troll.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Checks if the troll is expecting an answer from the player.
     *
     * @return True if expecting an answer, false otherwise.
     */
    @Override
    public boolean expectingAnswer() {
      return (stage == 1);
    }

    /**
     * Advances to the next stage of the troll's interaction.
     */
    @Override
    public void nextStage() {
        if (stage < lines.length) {
            PauseTransition pause = new PauseTransition(Duration.seconds(9));  // to give time for articulation to finish
            pause.setOnFinished(event -> {
                roomDesc.setText(lines[stage]); // introduce troll
                PauseTransition pause2 = new PauseTransition(Duration.seconds(5));
                pause2.setOnFinished(event2 -> {
                    roomDesc.setText(lines2[stage]);// add instrctions
                    stage++;
                });
                pause2.play();
            });
            pause.play();
        } else {
            // If there are no more stages, clear the text
            roomDesc.setText("");
            if (stage == lines.length) {
                SnakeGameView snakeGameView = new SnakeGameView(adventureGameView);
                snakeGameView.show(new Stage());
                // This is the last stage,start the game
            }
        }
    }

    /**
     * Exits the troll's interaction.
     *
     * @param view The AdventureGameView associated with this troll.
     */
    @Override
    public void exit (AdventureGameView view){
        roomDesc.setText("Take care now. This really is no place for soft humans.");
        view.setCurrentTroll(null);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            adventureGameView.updateScene("");
        });
        pause.play();

    }

    /**
     * Handles the player's action response.
     *
     * @param resp The player's response.
     * @return True if the player won, false otherwise.
     */
    @Override
    public boolean actionResponse(String resp)  {
        if (resp.contains(":)")) {
            roomDesc.setText("YOU WON!");
            return true;

        } else {
            roomDesc.setText("GAME OVER");
            return false;
        }
    }

}












