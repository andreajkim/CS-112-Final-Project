import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

//--------------------------------------------------------------------------
public class GUI extends JPanel{
//--------------------------------------------------------------------------

    
    //--------------------------------------------------------------------------
    //DATA MEMBERS    
    public static final int WIDTH =350;
    public static final int HEIGHT = 200;
       
    //--------------------------------------------------------------------------

    
    //--------------------------------------------------------------------------
    public GUI(){
           
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	    // Thread mainThread = new Thread(new Runner());
            //mainThread.start();
    }
    //--------------------------------------------------------------------------

    
    //--------------------------------------------------------------------------
    public static void main(String[] args){
	JFrame frame = new JFrame("Music");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	GUI mainInstance = new GUI();
	frame.setContentPane(mainInstance);
	frame.pack();
	frame.setVisible(true);
    }
    //--------------------------------------------------------------------------
    
//--------------------------------------------------------------------------
}//end of class GUI
//--------------------------------------------------------------------------
