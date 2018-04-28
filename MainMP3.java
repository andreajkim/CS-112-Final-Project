import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainMP3 {
    List<CustomPlayer> currentPlayer = new LinkedList<CustomPlayer>(); //creates empty list of songs to be played
    int currentIndex = 0; //stores index of song that is currently playing
    Random rand = new Random(); //create randomizer for shuffle

    public boolean everPlayed = false; //has any song been played yet?
    public boolean currentlyPlaying = true; //is a song currently playing?

    //make a music player for each audio file
    public void player(File[] mp3Name){
        List<CustomPlayer> player = new LinkedList<CustomPlayer>(); //create temporary list of songs to be filled

        for(int i = 0; i < mp3Name.length; i++){ //loop through all mp3 files in the chosen directory
            CustomPlayer current = new CustomPlayer(); //creates one player to be added to list
            current.setPath(mp3Name[i].getAbsolutePath()); //sets that player to read ith mp3 file
            player.add(current); //add that player to list of players
        }
        System.out.println("currentPlayer made");
        currentPlayer = player; //after all players are loaded, overwrite "main" list with loading list
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
        everPlayed = true; //records that songs have been played
        currentlyPlaying = true; //records that song is playing
        currentPlayer.get(currentIndex).play(); //plays the song
    }

    //pauses music
    public void pause(){
        currentlyPlaying = false; //records that song is not playing
        currentPlayer.get(currentIndex).pause(); //pauses song (records current time, makes a new copy, stops the old copy)
    }

    //restarts new player at paused time
    public void resume(){
        currentlyPlaying = true;
        currentPlayer.get(currentIndex).resume(); //creates new song that starts at stopped time and starts it
    }

    //skips to next song
    public void skipNext(){
        stop();

        if(currentIndex != currentPlayer.size()-1){
            currentIndex++; //goes to next index of song list
        } else {
            currentIndex = 0; //if at last index, go to beginning
        }

        play();
    }

    //skips to previous song
    public void skipPrevious(){
        stop();

        if(currentIndex != 0) {
            currentIndex--; //goes to previous index of song list
        } else {
            currentIndex = currentPlayer.size() - 1; //if at first index, go to end
        }

        play();
    }

    //repeats song once (by adding a copy of it)
    public void repeatOnce(){
        currentPlayer.add(currentIndex + 1, currentPlayer.get(currentIndex)); 
    }

    public void repeatAlways(){
        currentPlayer.get(currentIndex).play();
    }

    //shuffles order of songs 
    public void shuffle(){
        boolean wasPlaying = currentlyPlaying;

        List<CustomPlayer> shuffledPlayer = new LinkedList<CustomPlayer>();

        //remember current song so it can be added to shuffled list
        CustomPlayer currentSong = currentPlayer.get(currentIndex);

        //pauses song on currentPlayer and records time stopped
        currentPlayer.get(currentIndex).pause();
        int stopped = currentPlayer.get(currentIndex).getStopped();

        //remember currentPlayer properties
        List<CustomPlayer> unshuffledPlayer = currentPlayer;

        //add current song to beginning of newest list
        shuffledPlayer.add(0, currentSong);
        unshuffledPlayer.remove(currentIndex);

        int totalSize = unshuffledPlayer.size();

        //adds songs in random order to new player
        for(int i = 0; i < totalSize; i++){

            int index = rand.nextInt(totalSize  - i);

            CustomPlayer toAdd = unshuffledPlayer.get(index);

            shuffledPlayer.add(i+1, unshuffledPlayer.get(index));
            unshuffledPlayer.remove(index);
        }

        //starts over at beginning
        setIndex(0);

        //overwrites current player with updated list
        currentPlayer = shuffledPlayer;

        //play (to start), pause (to set variables appropriately), and overwrite pause timer (so it resumes at the right time)
        play();
        pause();
        currentPlayer.get(0).setStopped(stopped);

        //if currentlyPlaying, resume playing
        if(wasPlaying == true)
            resume();
    }

    //stops song and makes it restart at beginning next time (pauses/removes current song and replaces it with a new version)
    public void stop(){
        currentPlayer.get(currentIndex).pause(); //pauses player

        String path = currentPlayer.get(currentIndex).getPath(); //figure out where to read the song

        currentPlayer.remove(currentIndex); //delete the player

        currentPlayer.add(currentIndex, new CustomPlayer()); //re-add a blank player

        currentPlayer.get(currentIndex).setPath(path); //load the mp3 of the stopped song into the new player
    }
}
