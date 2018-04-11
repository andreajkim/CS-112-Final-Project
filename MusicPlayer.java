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
    Button open, show, toggle, queue, something, something1;
    Button play, skipPrevious, skipNext, shuffle, repeatOnce, repeatMany;
    static boolean everPlayed = false;
    static boolean currentlyPlaying = false;

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

        //make something button
        something = new Button("Something");
        gridbag.setConstraints(something, c);
        something.addActionListener(this);
        add(something);

        c.gridwidth = GridBagConstraints.REMAINDER; //complete first row

        //make something button
        something1 = new Button("Something1");
        gridbag.setConstraints(something1, c);
        something1.addActionListener(this);
        add(something1);

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
                File[] folder = mp3Chooser.chooseMusicFolder("/Users/hufengling/git/GitHub/");
                File[] mp3Files = mp3Chooser.chooseOnlyMP3s(folder);
                mainMP3.player(mp3Files);
                //mainMP3.shuffle();
                break;
            case "Show Graphic":
                break;

                //insert other functionality here


            case "Shuffle Order":
                mainMP3.shuffle();
                break;
            case "Previous":
                mainMP3.skipPrevious();
                break;
            case "Play/Pause":
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
                mainMP3.skipNext();
                break;
            case "Repeat Once":
                mainMP3.repeatOnce();

        }
    }
}
