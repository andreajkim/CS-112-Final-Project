import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainMP3 {
    int currentIndex = 0;
    List<CustomPlayer> currentPlayer = new LinkedList<CustomPlayer>();

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

        //adds songs in random order to new player
        for(int i = 0; i < totalSize; i++){
            int index = rand.nextInt(totalSize  -i);

            shuffledPlayer.add(unshuffledPlayer.get(index));
            unshuffledPlayer.remove(index);
        }

        //add current song to beginning of newest list
        shuffledPlayer.add(0, currentSong);

        //restart(currentPlayer, shuffledPlayer);

        //overwrites current player with updated list
        currentPlayer = shuffledPlayer;

        //starts over at beginning
        setIndex(0);
    }

    //?????
    //Move currently playing song to new list and keep it playing
    public void restart(List<CustomPlayer> toReplace, List<CustomPlayer> replacement){
        int stopped;
        boolean canResume;

        //pauses song on toReplace and records time stopped and whether or not it was playing or paused
        toReplace.get(currentIndex).pause();
        stopped = toReplace.get(currentIndex).getStopped();
        canResume = toReplace.get(currentIndex).getCanResume();


        replacement.get(0).play();
        replacement.get(0).pause();
        replacement.get(0).setStopped(stopped);

        /*
        replacement.get(0).setStopped(stopped);
        replacement.get(0).setCanResume(true);
        */

        if(canResume == false){
            replacement.get(0).resume();
        }

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
