import java.awt.*;

public class Main{

    public static void main(String args[]) {
        Frame f = new Frame("Music Player");
        MusicPlayer ex1 = new MusicPlayer();

        ex1.init();

        f.add("Center", ex1);
        f.pack();
        f.setSize(f.getPreferredSize());
        f.show();
    }
}