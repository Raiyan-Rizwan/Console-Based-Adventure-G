package Trolls;

import AdventureModel.AdventureObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import AdventureModel.AdventureGame;
import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import views.AdventureGameView;


/**
 * Class GameTroll.
 * Course code tailored by the CSC207 instructional
 * team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public class TicTacToeTroll implements Troll {

    //Write your own code here!

    String name;
    public ArrayList<String> GameBoard = new ArrayList<String>();
    public ArrayList<Button> butList = new ArrayList<Button>();

    Label lab;
    int stage = 0;

    ArrayList<String> stageList = new ArrayList<String>();

    boolean winOrNot = false;

    GridPane newGp = new GridPane();



    public TicTacToeTroll(Label l){
        this.name = "TICTACTOE_TROLL";

        for (int i = 0; i < 9; i++){
            GameBoard.add("empty");
        }

        lab = l;

        stageList.add("Yarrrhhhhh. You will make a delicious snack. " +
                "I am hungry but also bored. Say what, if you can beat me in a game of tic tac toe, I" +
                "'ll let you go. Otherwise, be transformed into fricassée! \n\n" +
                "TO PLAY THE GAME, CLICK ON THE BOX YOU WANT TO MARK. ENTER [B] TO BEGIN." );
    }

    /**
     * Play the GameTroll game
     *
     * @return true if player wins the game, else false
     */
    public boolean playRound() {
        for (int i = 0; i < 9; i++){
            if (GameBoard.get(i) == "empty"){
                GameBoard.set(i, "troll");

                String result = this.check();

                if (result == "troll wins"){
                    return (true);
                }
                else{
                    GameBoard.set(i, "empty");
                }
            }
        }

        for (int i = 0; i < 9; i++){
            if (GameBoard.get(i) == "empty"){
                GameBoard.set(i, "player");

                String result = this.check();

                if (result == "player wins"){
                    GameBoard.set(i, "troll");
                    return (true);
                }
                else{
                    GameBoard.set(i, "empty");
                }
            }
        }

        for (int i = 0; i < 9; i++){
            if (GameBoard.get(i) == "empty"){
                GameBoard.set(i, "troll");
                return (true);
            }
        }

        return (false);
    }

    public String check() {

        if (GameBoard.get(0) == "troll" && GameBoard.get(3) == "troll" && GameBoard.get(6) == "troll"){
            return ("troll wins");
        }
        else if (GameBoard.get(1) == "troll" && GameBoard.get(4) == "troll" && GameBoard.get(7) == "troll"){
            return ("troll wins");
        }
        else if (GameBoard.get(2) == "troll" && GameBoard.get(5) == "troll" && GameBoard.get(8) == "troll"){
            return ("troll wins");
        }

        else if (GameBoard.get(0) == "troll" && GameBoard.get(1) == "troll" && GameBoard.get(2) == "troll"){
            return ("troll wins");
        }
        else if (GameBoard.get(3) == "troll" && GameBoard.get(4) == "troll" && GameBoard.get(5) == "troll"){
            return ("troll wins");
        }
        else if (GameBoard.get(6) == "troll" && GameBoard.get(7) == "troll" && GameBoard.get(8) == "troll"){
            return ("troll wins");
        }

        else if (GameBoard.get(0) == "troll" && GameBoard.get(4) == "troll" && GameBoard.get(8) == "troll"){
            return ("troll wins");
        }
        else if (GameBoard.get(2) == "troll" && GameBoard.get(4) == "troll" && GameBoard.get(6) == "troll"){
            return ("troll wins");
        }

        else if (GameBoard.get(0) == "player" && GameBoard.get(3) == "player" && GameBoard.get(6) == "player"){
            return ("player wins");
        }
        else if (GameBoard.get(1) == "player" && GameBoard.get(4) == "player" && GameBoard.get(7) == "player"){
            return ("player wins");
        }
        else if (GameBoard.get(2) == "player" && GameBoard.get(5) == "player" && GameBoard.get(8) == "player"){
            return ("player wins");
        }


        else if (GameBoard.get(0) == "player" && GameBoard.get(1) == "player" && GameBoard.get(2) == "player"){
            return ("player wins");
        }
        else if (GameBoard.get(3) == "player" && GameBoard.get(4) == "player" && GameBoard.get(5) == "player"){
            return ("player wins");
        }
        else if (GameBoard.get(6) == "player" && GameBoard.get(7) == "player" && GameBoard.get(8) == "player"){
            return ("player wins");
        }


        else if (GameBoard.get(0) == "player" && GameBoard.get(4) == "player" && GameBoard.get(8) == "player"){
            return ("player wins");
        }
        else if (GameBoard.get(2) == "player" && GameBoard.get(4) == "player" && GameBoard.get(6) == "player"){
            return ("player wins");
        }

        else{
            int dummy = 0;
            for (int i = 0; i < 9; i++){
                if (GameBoard.get(i) != "empty"){
                    dummy++;
                }
            }

            if (dummy == 9){
                return ("tie");
            }

            else{
                return ("midgame");
            }
        }
    }

    public String getName(){
        return name;
    }

    public void nextStage(){

        System.out.println("Boo");

        if (stage == 0) {
            lab.setText(stageList.get(stage));
            stage = 1;
        }

        else{

            Image newImA1 = new Image("Games/TinyGame" + "/objectImages/" + "a1" + ".png");
            ImageView newIvA1 = new ImageView(newImA1);
            newIvA1.setFitHeight(75);
            newIvA1.setFitWidth(80);
            Button b0 = new Button();
            b0.setGraphic(newIvA1);

            b0.setOnAction(e -> {
                butClick("a1a", b0, 0);
            });


            Image newImA2 = new Image("Games/TinyGame" + "/objectImages/" + "a2" + ".png");
            ImageView newIvA2 = new ImageView(newImA2);
            newIvA2.setFitHeight(75);
            newIvA2.setFitWidth(80);
            Button b1 = new Button();
            b1.setGraphic(newIvA2);

            b1.setOnAction(e -> {
                butClick("a2a", b1, 1);
            });

            Image newImA3 = new Image("Games/TinyGame" + "/objectImages/" + "a3" + ".png");
            ImageView newIvA3 = new ImageView(newImA3);
            newIvA3.setFitHeight(75);
            newIvA3.setFitWidth(80);
            Button b2 = new Button();
            b2.setGraphic(newIvA3);

            b2.setOnAction(e -> {
                butClick("a3a", b2, 2);
            });

            Image newImB1 = new Image("Games/TinyGame" + "/objectImages/" + "b1" + ".png");
            ImageView newIvB1 = new ImageView(newImB1);
            newIvB1.setFitHeight(75);
            newIvB1.setFitWidth(80);
            Button b3 = new Button();
            b3.setGraphic(newIvB1);

            b3.setOnAction(e -> {
                butClick("b1a", b3, 3);
            });

            Image newImB2 = new Image("Games/TinyGame" + "/objectImages/" + "b2" + ".png");
            ImageView newIvB2 = new ImageView(newImB2);
            newIvB2.setFitHeight(75);
            newIvB2.setFitWidth(80);
            Button b4 = new Button();
            b4.setGraphic(newIvB2);

            b4.setOnAction(e -> {
                butClick("b2a", b4, 4);
            });

            Image newImB3 = new Image("Games/TinyGame" + "/objectImages/" + "b3" + ".png");
            ImageView newIvB3 = new ImageView(newImB3);
            newIvB3.setFitHeight(75);
            newIvB3.setFitWidth(80);
            Button b5 = new Button();
            b5.setGraphic(newIvB3);

            b5.setOnAction(e -> {
                butClick("b3a", b5, 5);
            });

            Image newImC1 = new Image("Games/TinyGame" + "/objectImages/" + "c1" + ".png");
            ImageView newIvC1 = new ImageView(newImC1);
            newIvC1.setFitHeight(75);
            newIvC1.setFitWidth(80);
            Button b6 = new Button();
            b6.setGraphic(newIvC1);

            b6.setOnAction(e -> {
                butClick("c1a", b6, 6);
            });

            Image newImC2 = new Image("Games/TinyGame" + "/objectImages/" + "c2" + ".png");
            ImageView newIvC2 = new ImageView(newImC2);
            newIvC2.setFitHeight(75);
            newIvC2.setFitWidth(80);
            Button b7 = new Button();
            b7.setGraphic(newIvC2);

            b7.setOnAction(e -> {
                butClick("b2a", b7, 7);
            });

            Image newImC3 = new Image("Games/TinyGame" + "/objectImages/" + "c3" + ".png");
            ImageView newIvC3 = new ImageView(newImC3);
            newIvC3.setFitHeight(75);
            newIvC3.setFitWidth(80);
            Button b8 = new Button();
            b8.setGraphic(newIvC3);

            b8.setOnAction(e -> {
                butClick("b3a", b8, 8);
            });


            butList.add(b0);
            butList.add(b1);
            butList.add(b2);
            butList.add(b3);
            butList.add(b4);
            butList.add(b5);
            butList.add(b6);
            butList.add(b7);
            butList.add(b8);

            newGp.add(b0, 0, 0);
            newGp.add(b1, 1, 0);
            newGp.add(b2, 2, 0);
            newGp.add(b3, 0, 1);
            newGp.add(b4, 1, 1);
            newGp.add(b5, 2, 1);
            newGp.add(b6, 0, 2);
            newGp.add(b7, 1, 2);
            newGp.add(b8, 2, 2);

            lab.setGraphic(newGp);
            lab.setText(" ");

            stage = 5;
        }

    }

    public void butClick(String s, Button b, int num){

        System.out.println(stage);

        Image i = new Image("Games/TinyGame" + "/objectImages/" + s + ".png");
        ImageView iv = new ImageView(i);
        iv.setFitHeight(75);
        iv.setFitWidth(80);
        b.setGraphic(iv);

        PauseTransition pt = new PauseTransition(Duration.seconds(2.5));

        GameBoard.set(num, "player");


        if (check() == "player wins"){
            winOrNot = true;

            pt.setOnFinished(event -> {
                lab.setGraphic(null);

                lab.setText("You won! Well, I'll hold up my end of the bargain. Good luck on your journey, " +
                        "adventurer! PRESS [X] TO BIDE FAREWELLS AND LEAVE.");

            });
            pt.play();

            

            return;

        }
        else if (check() != "midgame"){

            pt.setOnFinished(event -> {
                lab.setGraphic(null);

                lab.setText("You lost! That was an intellectually stimulating experience, and I like your" +
                        "company. Begone, be fast, before I regain my appetite! PRESS [X] TO LEAVE QUICKLY.");
            });
            pt.play();


            return;
        }

        pt.setOnFinished(event -> {
            playRound();
            trollMark();

            if (Objects.equals(check(), "player wins")){
                winOrNot = true;

                pt.setOnFinished(event2 -> {
                    lab.setGraphic(null);

                    lab.setText("You won! Well, I'll hold up my end of the bargain. Good luck on your journey, " +
                            "adventurer! PRESS [X] TO BIDE FAREWELLS AND LEAVE.");

                });
                pt.play();

                return;

            }
            else if (check() != "midgame"){

                winOrNot = false;

                pt.setOnFinished(event2 -> {
                    lab.setGraphic(null);

                    lab.setText("You lost! However, that was a fun experience, and I like your" +
                            "company. Begone, be fast, before I regain my appetite! PRESS [X] TO LEAVE QUICKLY.");
                });
                pt.play();


                return;
            }
        });
        pt.play();


    }

    public void trollMark(){
        ArrayList<String> l = new ArrayList<String>();
        l.add("a1b");
        l.add("a2b");
        l.add("a3b");
        l.add("b1b");
        l.add("b2b");
        l.add("b3b");
        l.add("c1b");
        l.add("c2b");
        l.add("c3b");

        for (int i = 0; i < 9; i++){

            Image im = new Image("Games/TinyGame" + "/objectImages/" + l.get(i) + ".png");
            ImageView iv = new ImageView(im);
            iv.setFitHeight(75);
            iv.setFitWidth(80);

            if (Objects.equals(GameBoard.get(i), "troll")){
                butList.get(i).setGraphic(iv);
            }
        }
    }

    public boolean actionResponse(String answer){

        System.out.println("cool");

        if (winOrNot){

            return (true);
        }
        else if (!winOrNot){
            return (false);
        }
        return (true);
    }


    public boolean expectingAnswer(){
        return stage == 5;
    }


    @Override
    public void exit(AdventureGameView view) {
        String s = "Goodbye! Now skidaddle!";
        lab.setText(s);
        view.setCurrentTroll(null);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {view.updateScene("");});
        pause.play();
    }

}
