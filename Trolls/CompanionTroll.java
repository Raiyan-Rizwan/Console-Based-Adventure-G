package Trolls;

import AdventureModel.AdventureGame;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import views.AdventureGameView;

import javax.sound.midi.SysexMessage;
import java.util.*;

/**
 * Class Companion.
 * This is a friendly troll that may join a player's quest if they
 * solve a riddle.
 *  */
public class CompanionTroll implements Troll {
    Label roomDesc;
    String name = "COMPANION_TROLL";
    private Map riddles = Map.of(" I speak without a mouth and hear without ears. I have no body, but I come alive with the wind. What am I?",
            "ECHO",
            "I’m tall when I’m young, and I’m short when I’m old. What am I?",
            "CANDLE",
            "I am not alive, but I grow; I don't have lungs, but I need air; I don't have a mouth, but water kills me. What am I?",
            "FIRE",
            "What has keys but can't open locks?",
            "PIANO",
            "What has a head and a tail but no body?",
            "COIN",
            "What begins with T, ends with T, and has T in it?",
            "TEAPOT",
            "What has words but never speaks?",
            "BOOK");

    private String riddle;

    private String[] lines;


    private int stage = 0;

    public CompanionTroll(Label roomDsc){
//        roomDesc = roomDsc;
        roomDesc = roomDsc;
        Random rand = new Random();
        ArrayList<String> riddleList = new ArrayList<>(riddles.keySet());
        riddle = riddleList.get(rand.nextInt(riddleList.size()));
        String[] linez = {"Hey what are you doing here? I haven't seen a soul pass through here in a long time.\n"+
                "Whats that? You look surprised. Never seen a mystical Troll talk before have you?\n"+
                "...",
                "Well nothing to see here. I will resume my duties.\n" +
                        "Not much else for me to do in this world *sigh*\n" +
                        "It may not look like it but before I was a great adventurer. I was apart of many different guilds.\n" +
                        "Oh the stories from those days!\n" +
                        "Alas those days are long gone. Now I have my duty here.\n",
                "I see you have not left as yet.\n" +
                        "Is there something you seek? Wait. That look on your face. No.\n" +
                        "You do not mean to tell me you want ME to join you on whatever quest this is you are on!?\n" +
                        "I have remained here guarding these chambers for a time longer than even the very sky above can remember.\n" +
                        "...\n" +
                        "Though I must admit. After the first two millennia this has gotten rather boring.\n" +
                        "Alright I may take you up on this offer.\n",
                "HOWEVER!!! I must know that you are worthy of having my company.\n" +
                        "...\n" +
                        "Humph yes yes classic Troll business here. Come on you should have known better than to not expect a riddle.\n" +
                        "I have had thousands of years to myself so of course I have constructed many tricky rhymes.\n" +
                        "If you can solve my riddle than I know you have what it takes.\n" +
                        "* clears throat *\n" +
                        "So here it goes:",
                riddle};
        lines = linez;
    }

    /**
     * Prints a random riddle for the user and processes their guess.
     *
     * @return true if player guesses right, else false
     */
    public boolean actionResponse(String guess){

        if (guess.equalsIgnoreCase(riddles.get(riddle).toString())){
            roomDesc.setText("Marvelous! That is right!\n"+
                    "No one has ever managed to solve any of my riddles. Certainly you are worthy. Alright I shall join you on this quest!\n");
            return true;
        } else{
            String thirdDi = "...\n" +
                    "Good try. Not quite right though. I cannot fault you. I have worked on these for longer than you have probably been alive.\n" +
                    "In fact some of these I can't quite remember the answer to. But what you said definitely sounded wrong.\n" +
                    "Honestly I was hoping you would get it right so I would have an excuse to leave this place.\n" +
                    "I thank you still for coming by. I have not had such fun in a long time.\n";
            roomDesc.setText(thirdDi);
            return false;
        }
    }
    /**
     * Advances the stage by 1.
     */
    public void nextStage(){

            roomDesc.setText(lines[stage]);
            stage++;

    }

    /**
     * Notifies AdventureGameView that a response from the
     * Player is required.
     *
     * @return true if player has reached the appropriate stage in the dialogue
     * to begin playing the game, else false
     */
    public boolean expectingAnswer(){
        return stage == 5;
    }
    /**
     * This ends the game once it has been finished and allows the player
     * to continue playing from the room they were in.
     */
    @Override
    public void exit(AdventureGameView view) {
        roomDesc.setText("Take care now. This really is no place for soft humans.");
        view.setCurrentTroll(null);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {view.updateScene("");});
        pause.play();

    }
    /**
     * returns the name of the Troll
     */
    @Override
    public String getName() {
        return name;
    }
}
