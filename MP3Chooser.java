import javax.swing.*;
import java.io.File;

public class MP3Chooser{
    public static boolean fileChosen = false; //might be useful for "Make sure to choose files" or something

    //choose folder you want to play music from (for albums/playlists)
    public static File[] chooseMusicFolder(String defaultDir){
        JFileChooser chooser = new JFileChooser(defaultDir);

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //can only choose directories
        chooser.setTitle("Open Music Directory"); //set title

        int returnVal = chooser.showOpenDialog(null); //opens window
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File folder = new File(chooser.getSelectedFile().getAbsolutePath()); //get path to folder
            int count = 0;

            //count number of actual files
            for (int i = 0; i < folder.listFiles().length; i++) {
                if (folder.listFiles()[i].isFile()) {
                    count++;
                }
            }

            File[] listOfFiles = new File[count];
            int currentIndex = 0;

            //fill a File[] of appropriate size with actual files, ignoring the other items in the directory that aren't files
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    listOfFiles[currentIndex] = file;
                    currentIndex++;
                }
            }

            return listOfFiles;
        }

        return null; //may bug
    }

    //from all files in directory, choose only the MP3s
    public static File[] chooseOnlyMP3s(File[] listOfFiles){
        int maxMP3s = listOfFiles.length;
        int count = 0;
        fileChosen = true;

        //find number of files that are mp3s
        for(int i = 0; i < maxMP3s; i++){
            if (listOfFiles[i].getName().toLowerCase().contains(".mp3")){
                count++;
            }
        }

        //create File[] of appropriate size
        File[] onlyMP3s = new File[count];
        int currentIndex = 0;

        //fill that File[] with mp3s
        for(File file : listOfFiles){
            if (file.getName().toLowerCase().contains(".mp3")){
                onlyMP3s[currentIndex] = file;
                currentIndex++;

            }
        }

        return onlyMP3s;
    }
}