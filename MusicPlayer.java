import javax.swing.*;
import javax.swing.border.Border;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MusicPlayer extends Applet implements ActionListener {

    String songName;
    Label label;
    Image visualization;
    Button open, show, toggle, queue, setDefaultDirectory, quit;
    Button play, skipPrevious, skipNext, shuffle, repeatOnce, repeatMany;
    boolean filesLoaded = false;
    String defaultDir;
    File[] mp3Files;
    String folderPath;

    //initialize backend objects
    MP3Chooser mp3Chooser = new MP3Chooser();
    MainMP3 mainMP3 = new MainMP3();

    
    protected void makebutton(String name, GridBagLayout gridbag, GridBagConstraints c) {
        Button button = new Button(name);
        gridbag.setConstraints(button, c);
        button.addActionListener(this);
        add(button);
    }

    public void init() {
	
	//setting Default Directory
	if(mp3Chooser.checkDefaultDirectory() == true){

	    //gets default and loads it into mp3player
	    folderPath = mp3Chooser.getDefaultDirectory();
	    System.out.println(mp3Chooser.getDefaultDirectory());
	    mp3Files = mp3Chooser.chooseOnlyMP3s(folderPath);
	    mainMP3.player(mp3Files);

	    mp3Chooser.usingDefault = true;
	    filesLoaded = true;
	
	    
	}
	
        //set background
        Color k = new Color(79,91,102);
        setBackground(k);

        //create layout objects
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        //set font
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setLayout(gridbag);

        //make first row of buttons
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        //make open files button
        open = new Button("Open Music");
        gridbag.setConstraints(open, c);
        open.addActionListener(this);
        add(open);

	//make Set Default Directory button
        setDefaultDirectory = new Button("Set Default Music");
        gridbag.setConstraints(setDefaultDirectory, c);
        setDefaultDirectory.addActionListener(this);
        add(setDefaultDirectory);

        //make show graphics button
        show = new Button("Show Graphic");
        gridbag.setConstraints(show, c);
        show.addActionListener(this);
        add(show);

        //make toggle graphics button
        toggle = new Button("Toggle Graphic");
        gridbag.setConstraints(toggle, c);
        toggle.addActionListener(this);
        add(toggle);

        //make show queue button
        queue = new Button("Show Queue");
        gridbag.setConstraints(queue, c);
        queue.addActionListener(this);
        add(queue);      

        c.gridwidth = GridBagConstraints.REMAINDER; //complete first row

        //make something button
        quit = new Button("Quit");
        gridbag.setConstraints(quit, c);
        quit.addActionListener(this);
        add(quit);

        //end of first row

        //print current song playing

        label = new Label(songName, Label.CENTER);
        label.setForeground(Color.white);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 5);
        gridbag.setConstraints(label, c);
        add(label);

        c.gridwidth = 1;                //reset to the default
        c.weightx = 1.0;

        //make shuffle button
        shuffle = new Button("Shuffle Order");
        gridbag.setConstraints(shuffle, c);
        shuffle.addActionListener(this);
        add(shuffle);

        //make skipBack button
        skipPrevious = new Button("Previous");
        gridbag.setConstraints(skipPrevious, c);
        skipPrevious.addActionListener(this);
        add(skipPrevious);

        //make play button
        play = new Button("Play/Pause");
        gridbag.setConstraints(play, c);
        play.addActionListener(this);
        add(play);

        //make skipForward button
        skipNext = new Button("Next");
        gridbag.setConstraints(skipNext, c);
        skipNext.addActionListener(this);
        add(skipNext);

        //make repeat button
        repeatOnce = new Button("Repeat Once");
        gridbag.setConstraints(repeatOnce, c);
        repeatOnce.addActionListener(this);
        add(repeatOnce);

        c.gridwidth = GridBagConstraints.REMAINDER; //complete last row

        //make repeat button
        repeatMany = new Button("Repeat On/Off");
        gridbag.setConstraints(repeatMany, c);
        repeatMany.addActionListener(this);
        add(repeatMany);

        //end of last row

        setSize(400, 500);
    }

    public void paint(Graphics g){
    }


    public void actionPerformed(ActionEvent ae){
        String com = ae.getActionCommand();

        switch(com){
            case "Open Music":
                folderPath = mp3Chooser.chooseMusicFolder();
                mp3Files = mp3Chooser.chooseOnlyMP3s(folderPath);
                mainMP3.player(mp3Files);
	        mp3Chooser.setFalseDefaultUse();
                break;
		
	case "Set Default Music":
	    mp3Chooser.setDefaultDirectory();
	    folderPath = mp3Chooser.getDefaultDirectory();
	    mp3Files = mp3Chooser.chooseOnlyMP3s(folderPath);
	    mainMP3.player(mp3Files);
	    break;
	    
            case "Show Graphic":
                if(filesLoaded == false)
                    break;
                break;

                //insert other functionality here

            case "Quit":
                System.exit(0); //close the program (X button doesn't work in applet)
                break;
            case "Shuffle Order":
                if(filesLoaded == false || mainMP3.everPlayed == false)
                    break;
                mainMP3.shuffle();
                break;
            case "Previous":
                if(filesLoaded == false)
                    break;
                mainMP3.skipPrevious();
                break;
            case "Play/Pause":
                if(filesLoaded == false)
                    break;
                if(mainMP3.everPlayed == false) { //start playing if never played
                    mainMP3.play();
                    break;
                }
                else if(mainMP3.currentlyPlaying == true){ //pause if playing
                    mainMP3.pause();
                    break   ;
                }
                else if(mainMP3.currentlyPlaying == false){ //resume if paused
                    mainMP3.resume();
                    break;
                }
                else {
                    break;
                }
            case "Next":
                if(filesLoaded == false)
                    break;
                mainMP3.skipNext();
                break;
            case "Repeat Once":
                if(filesLoaded == false)
                    break;
                mainMP3.repeatOnce();
                break;

        }
    }

 
	
}//end file
