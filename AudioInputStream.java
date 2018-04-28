import java.lang.Object;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
public class AudioInputStream extends InputStream {
    
    File song = new File (mp3 filename);
    AudioInputStream Stream = AudioSystem.getAudioInputStream(song);

    int frameLength = Stream.getFrameLength();
    int frameSize =  Stream.getFormat().getFrameSize();
    byte[]bytes = new byte [framelength * frameSize];
  
    int result = 0;
    try {
	result = Stream.read(bytes);
    }
    catch (Exception e) {
	e.printStackTrace();
    }
    System.out.print(bytes);
    System.out.print("Test test");
    System.out.print(result);


}
