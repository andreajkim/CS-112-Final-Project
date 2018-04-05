import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MainMP3 {
    int currentIndex = 0;
    List<CustomPlayer> currentPlayer = new LinkedList<CustomPlayer>();

    //make a music player for each audio file
    public void player(File[] mp3Name){
        List<CustomPlayer> player = new LinkedList<CustomPlayer>();

        for(int i = 0; i < mp3Name.length; i++){
            System.out.println("converting");

            CustomPlayer current = new CustomPlayer();
            current.setPath(mp3Name[i].getAbsolutePath());
            player.add(current);

        }

        currentPlayer = player;
    }

    //choose start point in list of songs
    public void setIndex(int index){
        currentIndex = index;
    }

    //start playing music
    public void play() {
        currentPlayer.get(currentIndex).play();
    }

    //records current time, makes a new copy, stops the old copy
    public void pause(){
        currentPlayer.get(currentIndex).pause();
    }

    //restarts new player at paused time
    public void resume(){
        currentPlayer.get(currentIndex).resume();
    }

    //skips to next song
    public void skipNext(){
        stop();

        currentIndex++;
        currentPlayer.get(currentIndex).play();
    }

    //skips to previous song
    public void skipPrevious(){
        stop();

        currentIndex--;
        currentPlayer.get(currentIndex).play();
    }

    //stops song and makes it restart at beginning next time (pauses/removes current song and replaces it with a new version)
    public void stop(){
        currentPlayer.get(currentIndex).pause();

        String path = currentPlayer.get(currentIndex).getPath();

        currentPlayer.remove(currentIndex);

        currentPlayer.add(currentIndex, new CustomPlayer());

        currentPlayer.get(currentIndex).setPath(path);
    }
}
