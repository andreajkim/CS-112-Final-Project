import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShowQueue{

   
    
	     
	
    public void showQueue(int length, MainMP3 mainMP3, String directory){
	/*
	JFrame frame = new JFrame("Your Music Queue");
	TextField[] textFields = new TextField[length];
	
	JPanel panel = new JPanel();
	panel.setLayout(new FlowLayout(FlowLayout.LEFT));
	frame.add(panel);
	
	frame.setSize(10, 10);
        
	frame.setVisible(true);	
		
	*/
	String[] names = new String[length];

	System.out.println(directory);
	System.out.println();
	for (int i = 0; i <length; i++){
	    names[i] = mainMP3.getName(i);
	    names[i].replace(directory,"");
	    names[i].replace(".mp3","");
	    System.out.println(i + "     " + names[i]);
	    
	    
	}
	    
	


			
    }
    
 
    
}

