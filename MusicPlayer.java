import javax.swing.*;
import javax.swing.border.Border;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MusicPlayer extends Applet implements ActionListener {

    String songName;
    Label label;
    Image visualization;
    Button open, show, toggle, queue, something, quit;
    Button play, skipPrevious, skipNext, shuffle, repeatOnce, repeatMany;
    boolean filesLoaded = false;

    //initialize backend objects
    MP3Chooser mp3Chooser = new MP3Chooser();
    MainMP3 mainMP3 = new MainMP3();

    //overwriting init method of Applet
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
        quit = new Button("Quit");
        gridbag.setConstraints(quit, c);
        quit.addActionListener(this);
        add(quit);

        //end of first row

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

        //force user to choose directory of mp3s as soon as applet is opened (since nothing can occur without loaded mp3s)
        File[] folder = mp3Chooser.chooseMusicFolder("/"); //choose folder
        File[] mp3Files = mp3Chooser.chooseOnlyMP3s(folder); //filter for only mp3s
        mainMP3.player(mp3Files);
        if (mainMP3.currentPlayer.size() != 0){
            filesLoaded = true; //tell program that files have been loaded, if there were mp3s in the selected folder (to enable all other commands)
        }
    }

    //will contain visualizations (hopefully...)
    public void paint(Graphics g){
    }

    //actionListener to check for button pushes
    public void actionPerformed(ActionEvent ae){
        String com = ae.getActionCommand();

        //switch case for commands
        switch(com){
            //open music (same code as above) to rechoose new directory of mp3s, if wanted
            case "Open Music":
                File[] folder = mp3Chooser.chooseMusicFolder("/");
                File[] mp3Files = mp3Chooser.chooseOnlyMP3s(folder);
                mainMP3.player(mp3Files);
                if (mainMP3.currentPlayer.size() != 0){
                    filesLoaded = true;
                }

                break;

            //open a window for graphics
            case "Show Graphic":
                if(filesLoaded == false)
                    break;
                break;

                //insert other functionality here

            //quit the program
            case "Quit":
                System.exit(0); //close the program (X button doesn't work in applet)
                break;
            //shuffle order of songs
            case "Shuffle Order":
                if(filesLoaded == false || mainMP3.everPlayed == false)
                    break;
                mainMP3.shuffle();
                break;
            //go to previous song. If at song index 0, go to last song in queue
            case "Previous":
                if(filesLoaded == false)
                    break;
                mainMP3.skipPrevious();
                break;
            //play or pause or resume song
            case "Play/Pause":
                if(filesLoaded == false)
                    break;
                if(mainMP3.everPlayed == false) { //start playing if never played
                    mainMP3.play();
                    new Thread(new Runnable(){ //create thread to detect how much song is left
                        public void run(){
                            try {
                                int i = mainMP3.currentPlayer.get(mainMP3.currentIndex).FIS.available(); //load amount of song left

                                if(i <= 10000){
                                    mainMP3.skipNext(); //if amount of song left is less than 1-2 seconds, skipNext
                                }
                                else if(i > 10000) {
                                    System.out.println("still listening" + i); 
                                    Thread.sleep(500); //otherwise, wait for 0.5 seconds
                                }

                                run(); //restart the listening thread

                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                                System.out.println("interrupted");
                                run(); //if something happens, try to restart itself
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("io");
                                run(); //if something happens, try to restart itself
                            }
                        }
                    }).start();
                    break;
                }
                else if(mainMP3.currentlyPlaying == true){ //pause if playing
                    mainMP3.pause();
                    break;
                }
                else if(mainMP3.currentlyPlaying == false){ //resume if paused
                    mainMP3.resume();
                    new Thread(new Runnable(){ //same code as above
                        public void run(){
                            try {
                                int i = mainMP3.currentPlayer.get(mainMP3.currentIndex).FIS.available();

                                if(i <= 10000){
                                    mainMP3.skipNext();
                                }

                                if(i > 10000) {
                                    System.out.println("still listening" + i);
                                    Thread.sleep(500);
                                }

                                run();

                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                                System.out.println("interrupted");
                                run();
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("io");
                                run();
                            }
                        }
                    }).start();
                    break;
                }
                else {
                    break;
                }
            //go to next song. If at last song in queue, go to first song in queue
            case "Next":
                if(filesLoaded == false)
                    break;
                mainMP3.skipNext();
                break;
            //repeat the current song one time
            case "Repeat Once":
                if(filesLoaded == false)
                    break;
                mainMP3.repeatOnce();
                break;

        }
    }
}
