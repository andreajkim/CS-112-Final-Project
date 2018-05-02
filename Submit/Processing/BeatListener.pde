import ddf.minim.*;
import ddf.minim.analysis.*;

Minim minim;
AudioPlayer song;
BeatDetect beat;
BeatListener bl;
PImage img;
PImage pic1;
PImage pic2;
PImage pic3;
float textSize = 20;



class BeatListener implements AudioListener
{
  private BeatDetect beat;
  private AudioPlayer source;
  
  BeatListener(BeatDetect beat, AudioPlayer source)
  {
    this.source = source;
    this.source.addListener(this);
    this.beat = beat;
  }
  
  void samples(float[] samps)
  {
    beat.detect(source.mix);
  }
  
  void samples(float[] sampsL, float[] sampsR)
  {
    beat.detect(source.mix);
  }
}
//this method was taken directly from http://code.compartmental.net/minim/beatdetect_field_freq_energy.html
void setup()
{
  size(1024, 768, P3D);
  
  minim = new Minim(this);
  
  song = minim.loadFile("REPLACE WITH MP3 FILE", 1024); 
  //Place mp3 file within quotations
  song.play();
 
  beat = new BeatDetect(song.bufferSize(), song.sampleRate());
  beat.setSensitivity(150);  
  bl = new BeatListener(beat, song);  
  img = loadImage("1830572.jpg"); 

}

void draw()
{
  background(0);
  textSize(textSize);
  fill(255);
  text("Hold down on the mouse to change visuals!", width/3, height/8);
  



  if (beat.isKick()) {
   if(mousePressed) {
     image(img, 0, height/4); 
   }
   else {
     ellipse(width/4, height/2, 100, 100);
     fill(100);
  }
  if (beat.isSnare()) {
   if(mousePressed){
     image(img, 2*(width/3), height/4);
   }
   else{
     ellipse(3*(width/4), height/2, 100, 100);
     fill(200);
   }
  }
  if (beat.isHat()) {
    if(mousePressed){
     image(img, width/3, height/4); 
    }
    else{
        ellipse(width/2, height/2, 100, 100);
        fill(300);
        }
    }
  } 
 

}
