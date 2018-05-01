import ddf.minim.*;

public class AudioListener{
    Minim minim;
    boolean listening;
    BeatListener beats;
    public void setup(){
	beats = new BeatListener();
	if (currentlyPlaying){
	   currentPlayer.addListener(beats);
	    listening = true;
	}
	else{
	     currentPlayer.removeListener(beats );
	     listening = false;
	  }
  }
}
