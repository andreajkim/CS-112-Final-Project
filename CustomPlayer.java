import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

//Credit to Josh M (https://stackoverflow.com/questions/12057214/jlayer-pause-and-resume-song)
public class CustomPlayer {

    private AdvancedPlayer player;
    private FileInputStream FIS;
    private BufferedInputStream BIS;
    private boolean canResume;
    private String path;
    private int total;
    private int stopped;
    private boolean valid;

    public CustomPlayer(){
        player = null;
        FIS = null;
        valid = false;
        BIS = null;
        path = null;
        total = 0;
        stopped = 0;
        canResume = false;
    }

    public int getStopped() {
        return stopped;
    }

    public void setStopped(int stopped){
        this.stopped = stopped;
    }

    public boolean getCanResume(){
        return canResume;
    }

    public void setCanResume(boolean canResume){
        this.canResume = canResume;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public void pause(){
        try{
            stopped = FIS.available(); //check how many bytes are unread in the song
            player.close(); //kill the player
            FIS = null; //set appropriate variables
            BIS = null;
            player = null;
            if(valid) canResume = true;
        }catch(Exception e){

        }
    }

    public void resume(){
        if(!canResume) return; //doesn't do anything if a song is currently playing
        if(play(total-stopped-7500)) //plays the song, but starting at paused time.
            canResume = false; //sets can resume
    }

    public boolean play(int pos){
        valid = true;
        canResume = false;
        try{
            FIS = new FileInputStream(path);
            total = FIS.available();
            if(pos > -1) FIS.skip(pos);
            BIS = new BufferedInputStream(FIS);
            player = new AdvancedPlayer(BIS);
            new Thread(
                    new Runnable(){
                        public void run(){
                            try{
                                player.play();
                            }catch(Exception e){
                                JOptionPane.showMessageDialog(null, "Error playing mp3 file: 1");
                                valid = false;
                            }
                        }
                    }
            ).start();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error playing mp3 file: 2");
            valid = false;
        }
        return valid;
    }

    public boolean play(){ //same player as above, but only run if no stop time is specified
        valid = true;
        canResume = false;
        try{
            FIS = new FileInputStream(path);
            total = FIS.available();
            BIS = new BufferedInputStream(FIS);
            player = new AdvancedPlayer(BIS);
            new Thread(
                    new Runnable(){
                        public void run(){
                            try{
                                player.play();
                            }catch(Exception e){
                                //JOptionPane.showMessageDialog(null, "Error playing mp3 file: 3");
                                valid = false;
                            }
                        }
                    }
            ).start();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error playing mp3 file: 4");
            valid = false;
        }
        return valid;
    }
}