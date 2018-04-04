import javazoom.jl.player.Player;

import java.io.File;
import java.util.List;

public class Main{

    public static void main(String[] args){
        MP3Chooser mp3Chooser = new MP3Chooser();
        MainMP3 mainMP3 = new MainMP3();

        File[] folder = mp3Chooser.chooseMusicFolder("/Users/hufengling/git/GitHub/");

        File[] mp3Files = mp3Chooser.chooseOnlyMP3s(folder);

        List<Player> mp3Players = mainMP3.player(mp3Files);
    }
}