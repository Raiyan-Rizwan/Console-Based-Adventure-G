package AdventureModel;

import AdventureModel.Room;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class RoomSound {

    private static RoomSound roomSound = null;
    private boolean isPaused = true;
    private int volume = 0;
    private String adventureName;
    private MediaPlayer mediaPlayer;
    private boolean mediaPlaying = false;

    private RoomSound(){
    }

    public static RoomSound getInstance(){
        if (roomSound == null){
            roomSound = new RoomSound();
        }
        return roomSound;
    }

    public boolean isPlaying(){
        return mediaPlaying;
    }

    public void pause(){
        if(!isPaused){
            mediaPlayer.pause();
            isPaused = true;
        }

    }

    public void play(){
        if (isPaused){
            mediaPlayer.play();
            isPaused = false;
        }
    }

    public void updateSounds(Room playerRoom){
        String musicFile;

        for (String sound: playerRoom.soundsInRoom){
            musicFile = "./" + adventureName + "/sounds/" + sound.toLowerCase() + ".mp3" ;
            musicFile = musicFile.replace(" ","-");

            Media soundToPlay = new Media(new File(musicFile).toURI().toString());

            mediaPlayer = new MediaPlayer(soundToPlay);
            mediaPlayer.play();
            mediaPlaying = true;
        }

    }

    public void setAdventureName(String name){
        adventureName = name;
    }

}
