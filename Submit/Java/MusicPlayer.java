import javax.swing.*;
import javax.swing.border.Border;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class MusicPlayer extends Applet implements ActionListener {

    Button open, show, toggle, queue, setDefaultDirectory, quit;
    Button play, skipPrevious, skipNext, shuffle, repeatOnce, repeatMany;
    boolean filesLoaded = false;
    String defaultDir;
    File[] mp3Files;
    String folderPath;
    Label label;

    //initialize backend objects
    MP3Chooser mp3Chooser = new MP3Chooser();
    MainMP3 mainMP3 = new MainMP3();
    ShowQueue showQueue = new ShowQueue();

    public void init() {

	//initializing Default Directory 
	//checks to see if Default Directory exists
	//System.out.println(mp3Chooser.checkDefaultDirectory()); //troubleshooting

     if(mp3Chooser.checkDefaultDirectory()){


         mp3Files = mp3Chooser.chooseOnlyMP3s(mp3Chooser.getDefaultDirectory());
         mainMP3.player(mp3Files);

         mp3Chooser.usingDefault = true;
         filesLoaded = true;


	//if there isn't a default directory go straight to chooseMusicFolder();      
     }else{
	    //System.out.println("before");
         folderPath = mp3Chooser.chooseMusicFolder(true);
         mp3Files = mp3Chooser.chooseOnlyMP3s(folderPath);
         mainMP3.player(mp3Files);
         filesLoaded = true;
	    //System.out.println("after");

	    //Do you want this to be your default directory?	   
         int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to set this music folder as your default music folder?\n\nThis means the folder will be saved as your default when you quit and reopen the program. \nDon't worry you can always change your default music folder.", "Set Default Folder?", JOptionPane.YES_NO_OPTION);


	    //if yes, set as default directory
         if(dialogResult == JOptionPane.YES_OPTION){
          mp3Chooser.setDefaultDirectory(folderPath);
          mp3Chooser.usingDefault = true;
      }
	}//^Martin

	
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

	/*
        //make toggle graphics button
        toggle = new Button("Toggle Graphic");
        gridbag.setConstraints(toggle, c);
        toggle.addActionListener(this);
        add(toggle);
	*/

        //make show queue button
        queue = new Button("Print Queue");
        gridbag.setConstraints(queue, c);
        queue.addActionListener(this);
        add(queue);

	 //make something button
        quit = new Button("Quit");
        gridbag.setConstraints(quit, c);
        quit.addActionListener(this);
        add(quit);

        c.gridwidth = GridBagConstraints.REMAINDER; //complete first row

	 //end of first row


        //print current song playing

        label = new Label("", Label.CENTER);
        label.setForeground(Color.white);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 5);
        gridbag.setConstraints(label, c);
        add(label);

        c.gridwidth = 1;                //reset to the default
        c.weightx = 1.0;

        //make shuffle button
        shuffle = new Button("Toggle Shuffle On/Off");
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
        repeatOnce = new Button("Repeat This Song");
        gridbag.setConstraints(repeatOnce, c);
        repeatOnce.addActionListener(this);
        add(repeatOnce);

        c.gridwidth = GridBagConstraints.REMAINDER; //complete last row

	/*
        //make repeat button
        repeatMany = new Button("Repeat On/Off");
        gridbag.setConstraints(repeatMany, c);
        repeatMany.addActionListener(this);
        add(repeatMany);
	*/
        //end of last row

        setSize(400, 500);

	//done with buttons/graphics/layout
        
    }// end init();

    public void paint(Graphics g){
    }


    public void actionPerformed(ActionEvent ae){
        String com = ae.getActionCommand();

        switch(com){
            case "Open Music":
            folderPath = mp3Chooser.chooseMusicFolder(false);
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

            case "Print Queue":

            showQueue.showQueue(mainMP3.order.length, mainMP3, MP3Chooser.filePathDirectory);
            break;

            case "Quit":
                System.exit(0); //close the program (X button doesn't work in applet)
                break;

                case "Toggle Shuffle":               
                mainMP3.toggleShuffle();
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
                    
                    new Thread(new Runnable(){ //create thread to detect how much song is left
                        public void run(){
                            try {
                                int i = mainMP3.currentPlayer.get(mainMP3.currentIndex).FIS.available(); //load amount of song left

                                if(i <= 15000){
                                    Thread.sleep(3000);
                                    mainMP3.skipNext(); //if amount of song left is less than 1-2 seconds, skipNext
                                }
                                else if(i > 15000) {
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
                    break   ;
                }
                else if(mainMP3.currentlyPlaying == false){ //resume if paused
                    mainMP3.resume();

                    new Thread(new Runnable(){ //create thread to detect how much song is left
                        public void run(){
                            try {
                                int i = mainMP3.currentPlayer.get(mainMP3.currentIndex).FIS.available(); //load amount of song left

                                if(i <= 15000){
                                    Thread.sleep(3000);
                                    mainMP3.skipNext(); //if amount of song left is less than 1-2 seconds, skipNext
                                }
                                else if(i > 15000) {
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
                else {
                    break;
                }
                case "Next":
                if(filesLoaded == false)
                    break;
                mainMP3.skipNext();
                break;

                case "Repeat This Song":
                if(filesLoaded == false)
                    break;
                mainMP3.repeatAlways();
                break;

            }
        }



}//end file
