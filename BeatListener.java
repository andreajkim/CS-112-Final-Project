import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import ddf.minim.*;
import ddf.minim.analysis.*;
import java.awt.Graphics;
import java.awt.Color;

public class BeatListener implements AudioListener{
    Minim minim;
    BeatListener bl;
    
    
    private BeatDetect beat;
    private AudioPlayer source;
    
    public void BeatListener(BeatDetect beat, AudioPlayer source){
	this.source = source;
	this.source.addListener(this);
	this.beat = beat;
    }
    
    public void samples(float[] samps){
	beat.detect(source.mix);
    }
    
    public void samples(float[] sampsL, float[] sampsR){
	beat.detect(source.mix);
    }
    
    

    public void setup(){
	
    
	minim = new Minim(this);
	
	song = , 1024);
 
    beat = new BeatDetect(song.bufferSize(), song.sampleRate());
    beat.setSensitivity(300);  
    bl = new BeatListener(beat, song);  
}


public class Draw extends JPanel{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    BufferedImage myPicture = ImageIO.read(new File("path-to-file"));
    JLabel picLabel = new JLabel(new ImageIcon(myPicture));
    add(picLabel);
    picLabel.setBounds(WIDTH/2, HEIGHT/2, 50, 75);
    picLabel.setBounds(WIDTH/4, HEIGHT/4, 50, 75);
    picLabel.setBounds(3*(WIDTH/4),3* (HEIGHT/4), 50, 75);
 

    if (beat.isKick()) {
	picLabel.setBounds(WIDTH/2, HEIGHT/2, 100, 150);
    }
    if (beat.isSnare()) {
	 picLabel.setBounds(WIDTH/4, HEIGHT/4, 100, 150);
    }
    if (beat.isHat()) {
	picLabel.setBounds(3*(WIDTH/4),3* (HEIGHT/4), 100, 150);
    }
}
