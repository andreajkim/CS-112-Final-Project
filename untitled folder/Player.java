import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

public class Player extends Application {

    public static String path;

    public Player(String path){
        this.path = path;
    }

    public void setPath(String path){
        this.path = path;
    }

    @Override
    public void start(Stage primaryStage) {

        //String path = "/Users/hufengling/git/GitHub/CS-112-Final-Project/testSong.mp3";


        Media pick = new Media(new File(path).toURI().toString());
        MediaPlayer player = new MediaPlayer(pick);

        // Add a mediaView, to display the media. Its necessary !
        // This mediaView is added to a Pane
        MediaView mediaView = new MediaView(player);

        // Add to scene
        //Scene scene = new Scene(root, 500, 200);

        // Show the stage
        //primaryStage.setTitle("Media Player");
        //primaryStage.setScene(scene);
        //primaryStage.show();

        // Play the media once the stage is shown
        player.play();
    }
}