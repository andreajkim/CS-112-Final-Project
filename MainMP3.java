import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class MainMP3 {

    //make a music player for each audio file
    public List<Player> player(File[] mp3Name){
        FileInputStream[] fileInputStream = new FileInputStream[mp3Name.length];
        List<Player> player = new LinkedList<Player>();

        for(int i = 0; i < mp3Name.length; i++){
            try {
                System.out.println("converting");
                fileInputStream[i] = new FileInputStream(mp3Name[i].getAbsolutePath());
                player.add(new Player(fileInputStream[i]));
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch(JavaLayerException e){
                e.printStackTrace();
            }

        }

        return player;
    }

    public void play(){

    }

    public void pause(){

    }
}
