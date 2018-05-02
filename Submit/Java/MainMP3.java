import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainMP3 {

    List<CustomPlayer> currentPlayer = new LinkedList<CustomPlayer>(); //creates empty list of songs to be played
    int currentIndex = 0; //stores index of song that is currently playing
    Random rand = new Random(); //create randomizer for shuffle

    public boolean everPlayed = false; //has any song been played yet?
    public boolean currentlyPlaying = false; //is a song currently playing?

    public boolean isShuffle = false;
    public File[] currentFile;
    public int[] order;



    //make a music player for each audio file & inits all variables for the new array of files -Feng
    public void player(File[] mp3Name){

        //pauses previous folder of music is it exists; solves bugs around two songs at once
        if(currentlyPlaying){
            pause();
        }

        //this is useful for reverting to non-shuffle state
        currentFile = mp3Name;


        //create temporary list of songs to be filled
        List<CustomPlayer> player = new LinkedList<CustomPlayer>();

        //loop through all mp3 files in the chosen directory
        for(int i = 0; i < mp3Name.length; i++){

            CustomPlayer current = new CustomPlayer(); //creates one player to be added to list
            current.setPath(mp3Name[i].getAbsolutePath()); //sets that player to read ith mp3 file
            player.add(current); //add that player to list of players

        }

        //make the order array the right size for the currentFile and put in normal, straightforward order (eg 1,2,3,etc)
        order = new int[mp3Name.length];
        for(int i=0; i<order.length; i++){
            order[i]=i;
        }

        System.out.println("currentPlayer made");
        //after all players are loaded, overwrite "main" list with loading list
        currentPlayer = player;

        //this allows the user to click play/pause button to start listening w/o having to click next
        play();

    }


    //choose start point in list of songs -Feng
    public void setIndex(int index){
        currentIndex = index;
    }

    //return name of MP3 file -Feng
    public String getName(int songsForward){

        if(currentIndex+songsForward <= currentPlayer.size()-1){


            File test = new File(currentPlayer.get(order[currentIndex+songsForward]).getPath());
            return test.getName();

        }else{
            File test = new File(currentPlayer.get(order[currentPlayer.size()-(currentIndex+songsForward)]).getPath());
            return test.getName();
        }


    }

    //start playing music -Feng
    public void play() {
        everPlayed = true; //records that songs have been played
        currentlyPlaying = true; //records that song is playing
        currentPlayer.get(order[currentIndex]).play(); //plays the song
    }

    //pauses music -Feng
    public void pause(){
        currentlyPlaying = false; //records that song is not playing
        currentPlayer.get(order[currentIndex]).pause(); //pauses song (records current time, makes a new copy, stops the old copy)
    }

    //restarts new player at paused time -Feng
    public void resume(){
        currentlyPlaying = true;
        currentPlayer.get(order[currentIndex]).resume(); //creates new song that starts at stopped time and starts it
    }

    //skips to next song -Feng
    public void skipNext(){
        stop();

        if(currentIndex != currentPlayer.size()-1){
            currentIndex++; //goes to next index of song list
        } else {
            currentIndex = 0; //if at last index, go to beginning
            revertToInOrder();
        }

        play();
    }

    //skips to previous song -Feng
    public void skipPrevious(){
        stop();

        if(currentIndex != 0) {
            currentIndex--; //goes to previous index of song list
            revertToInOrder();
        } else {
            currentIndex = currentPlayer.size() - 1; //if at first index, go to end
        }

        play();
    }

    //repeats song -Feng/Martin
    public void repeatAlways(){
        currentPlayer.get(order[currentIndex]).play();
    }


    public void revertToInOrder(){

        pause();
        int storedIndex = currentIndex;

        player(currentFile);

        setIndex(storedIndex);
        resume();

        isShuffle = false;
    }

    //this is what the button activates -Martin
    public void toggleShuffle(){
        System.out.println("toggle");
        if(isShuffle){
            revertToInOrder();
        }else{
            System.out.println("shuffle");
            shuffle();
        }
    }

    //new version of shuffle -Martin/Feng
    public void shuffle(){

        stop();

        int placeholder =0;
        int randNum;

        for(int i=0;i<order.length; i++){

            randNum = rand.nextInt(order.length);

            placeholder = order[i];

            order[i] = order[randNum];

            order[randNum] = placeholder;
        }

        setIndex(0);
        play();
        isShuffle = true;
    }

    //shuffles order of songs (needs to be fixed) -Feng
    //Martin improved this
    /*public void shuffle(){
      boolean wasPlaying = currentlyPlaying;

      System.out.println("starting shuffle");

      System.out.println(1);
      List<CustomPlayer> shuffledPlayer = new LinkedList<CustomPlayer>();
      System.out.println("currentIndex = " + currentIndex);
      System.out.println("currentSize = " + currentPlayer.size());

      //remember current song so it can be added to shuffled list
      CustomPlayer currentSong = currentPlayer.get(currentIndex);

      //pauses song on currentPlayer and records time stopped
      currentPlayer.get(currentIndex).pause();
      System.out.println("paused");
      int stopped = currentPlayer.get(currentIndex).getStopped();
      System.out.println(4);

      System.out.println("file path = " + currentSong.getPath());
      System.out.println(2);
      //remember currentPlayer properties
      List<CustomPlayer> unshuffledPlayer = currentPlayer;

      //add current song to beginning of newest list
      shuffledPlayer.add(0, currentSong);
      unshuffledPlayer.remove(currentIndex);

      int totalSize = unshuffledPlayer.size();
        
      System.out.println("total Size = " + totalSize);

      System.out.println(3);
      //adds songs in random order to new player
      for(int i = 0; i < totalSize; i++){

      System.out.println("i = " + i);
      int index = rand.nextInt(totalSize  - i);
      System.out.println("index = " + index);

      System.out.println(unshuffledPlayer.size());

      CustomPlayer toAdd = unshuffledPlayer.get(index);

      System.out.println("toadd");
      shuffledPlayer.add(i+1, unshuffledPlayer.get(index));
      System.out.println("added");
      unshuffledPlayer.remove(index);
      System.out.println("removed");
      }

      System.out.println("shuffling done");

      System.out.println("start new");

      //starts over at beginning
      setIndex(0);

      //overwrites current player with updated list
      currentPlayer = shuffledPlayer;

      //play (to start), pause (to set variables appropriately), and overwrite pause timer (so it resumes at the right time)
      play();
      pause();
      currentPlayer.get(0).setStopped(stopped);
      System.out.println(5);

      System.out.println(currentlyPlaying);
      //if currentlyPlaying, resume playing
      if(wasPlaying == true)
      resume();

      System.out.println(6);
      }
    */


    //stops song and makes it restart at beginning next time (pauses/removes current song and replaces it with a new version) -Feng
    public void stop(){
        currentPlayer.get(order[currentIndex]).pause(); //pauses player

        String path = currentPlayer.get(order[currentIndex]).getPath(); //figure out where to read the song

        currentPlayer.remove(order[currentIndex]); //delete the player

        currentPlayer.add(order[currentIndex], new CustomPlayer()); //re-add a blank player

        currentPlayer.get(order[currentIndex]).setPath(path); //load the mp3 of the stopped song into the new player
    }
}
