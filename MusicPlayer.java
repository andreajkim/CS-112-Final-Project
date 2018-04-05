import javax.swing.*;
import javax.swing.border.Border;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusicPlayer extends Applet implements ActionListener {

    String songName;
    Label label;
    Image visualization;
    Button open, show, toggle, queue, something;
    Button play, skipPrevious, skipNext, shuffle, repeat;

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

        c.gridwidth = GridBagConstraints.REMAINDER; //complete first row

        //make something button
        something = new Button("Something");
        gridbag.setConstraints(something, c);
        something.addActionListener(this);
        add(something);

        //end of first row

        label = new Label(songName, Label.CENTER);
        label.setForeground(Color.white);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 5);
        gridbag.setConstraints(label, c);
        add(label);

        c.gridwidth = 1;                //reset to the default
        c.weightx = 1.0;

        //make shuffle button
        shuffle = new Button("Shuffle");
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

        c.gridwidth = GridBagConstraints.REMAINDER; //complete last row

        //make repeat button
        repeat = new Button("Repeat");
        gridbag.setConstraints(repeat, c);
        repeat.addActionListener(this);
        add(repeat);

        //end of last row

        setSize(300, 500);
    }

    public void paint(Graphics g){
    }

    public void actionPerformed(ActionEvent ae){

    }
}
