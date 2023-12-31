package Trolls;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import views.AdventureGameView;


/** This is an interface that allows
different implementations of Trolls
 to operate smoothly with the interface.
 **/

public interface Troll {
    /**
     * Indicates at what point in the dialogue
     * the player has reached.
     */
    int stage = 0;
    Label roomDesc = null;
    String name = null;
    /**
     * returns the name of the Troll
     */
    public String getName();
    /**
     * Notifies AdventureGameView that a response from the
     * Player is required.
     */
    public boolean expectingAnswer();
    /**
     * This runs the game for a Troll. It responds
     * accordingly to what the player answers.
     */
    public boolean actionResponse(String answer);
    /**
     * This ends the game once it has been finished and allows the player
     * to continue playing from the room they were in.
     */
    public void exit(AdventureGameView view);
    /**
     * Advances the stage by 1.
     */
    public void nextStage();

}
