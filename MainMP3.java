import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainMP3 {
    int currentIndex = 0;
    List<CustomPlayer> currentPlayer = new LinkedList<CustomPlayer>();

    public boolean everPlayed = false;
    public boolean currentlyPlaying = true;

    //make a music player for each audio file
    public void player(File[] mp3Name){
        List<CustomPlayer> player = new LinkedList<CustomPlayer>();

        for(int i = 0; i < mp3Name.length; i++){
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

    //return name of MP3 file
    public String getName(){
        return currentPlayer.get(currentIndex).getPath();
    }

    //start playing music
    public void play() {
        System.out.println(currentIndex);
        everPlayed = true;
        currentlyPlaying = true;
        currentPlayer.get(currentIndex).play();
    }

    //records current time, makes a new copy, stops the old copy
    public void pause(){
        currentlyPlaying = false;
        currentPlayer.get(currentIndex).pause();
    }

    //restarts new player at paused time
    public void resume(){
        currentlyPlaying = true;
        currentPlayer.get(currentIndex).resume();
    }

    //skips to next song
    public void skipNext(){
        stop();

        currentIndex++;
        play();
    }

    //skips to previous song
    public void skipPrevious(){
        stop();

        currentIndex--;
        play();
    }

    //repeats song once (by adding a copy of it)
    public void repeatOnce(){
        currentPlayer.add(currentIndex + 1, currentPlayer.get(currentIndex));
    }

    public void repeatAlways(){
        currentPlayer.get(currentIndex).play();
    }

    //????
    //shuffles order of songs (needs to be fixed)
    public void shuffle(){
        Random rand = new Random();

        List<CustomPlayer> shuffledPlayer = new LinkedList<CustomPlayer>();

        //remember current song so it can be added to shuffled list
        CustomPlayer currentSong = currentPlayer.get(currentIndex);

        //remember currentPlayer properties
        List<CustomPlayer> unshuffledPlayer = currentPlayer;
        int totalSize = unshuffledPlayer.size();

        //put current song at front of new player and keep it playing
        //add current song to beginning of newest list
        shuffledPlayer.add(0, currentSong);
        unshuffledPlayer.remove(currentIndex);

        //adds songs in random order to new player
        for(int i = 0; i < totalSize; i++){
            int index = rand.nextInt(totalSize  - i);

            shuffledPlayer.add(unshuffledPlayer.get(index));
            unshuffledPlayer.remove(index);
        }

        //pauses song on currentPlayer and records time stopped
        currentPlayer.get(currentIndex).pause();
        int stopped = currentPlayer.get(currentIndex).getStopped();

        //play (to start), pause (to set variables appropriately), and overwrite pause timer (so it resumes at the right time)
        shuffledPlayer.get(0).play();
        shuffledPlayer.get(0).pause();
        shuffledPlayer.get(0).setStopped(stopped);

        //if currentlyPlaying, resume playing
        if(currentlyPlaying == true)
            shuffledPlayer.get(0).resume();

        //overwrites current player with updated list
        currentPlayer = shuffledPlayer;

        //starts over at beginning
        setIndex(0);
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
