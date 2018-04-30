import ddf.minim.*;
import ddf.minim.analysis.*;
import java.awt.Graphics;
import java.awt.Color;

Minim minim;
AudioPlayer song;
BeatDetect beat;
BeatListener bl;
float eRadius;


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

void setup()
{
  size(1024, 768, P3D);
  
  minim = new Minim(this);
  
  song = minim.loadFile("Marky Mark & the Funky Bunch - Good Vibrations _ LYRICS   SONG _.mp3", 1024);
  song.play();
 
  beat = new BeatDetect(song.bufferSize(), song.sampleRate());
  beat.setSensitivity(300);  
  bl = new BeatListener(beat, song);  
 ellipseMode(RADIUS);
 eRadius = 75;
}

void draw()
{
  background(0);
    

  float f = map(eRadius, 75, 200, 80, 255);
  fill(80, 255, 0, f);

  if ( beat.isKick() ) {
      eRadius = 200;
    
  
    ellipse(width/4, height/4, eRadius, eRadius);
    eRadius *=0.95;
  }
  if ( beat.isSnare() ) {
     eRadius = 200;
  
    ellipse(width/2, height/2, eRadius, eRadius);
    eRadius *=0.95;
  }
  if ( beat.isHat() ) {
    eRadius = 200;

    ellipse(3*width/4, 3*height/4, eRadius, eRadius);
    eRadius *=0.95;
  }
}
 
