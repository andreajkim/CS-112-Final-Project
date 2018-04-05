import java.io.File;

public class Main{

    public static void main(String[] args) throws InterruptedException {
        MP3Chooser mp3Chooser = new MP3Chooser();
        MainMP3 mainMP3 = new MainMP3();

        File[] folder = mp3Chooser.chooseMusicFolder("/Users/hufengling/git/GitHub/");

        File[] mp3Files = mp3Chooser.chooseOnlyMP3s(folder);

        mainMP3.player(mp3Files);

        mainMP3.play();

        Thread.sleep(7000);

        mainMP3.pause();

        Thread.sleep(5000);

        mainMP3.skipNext();



    }
}