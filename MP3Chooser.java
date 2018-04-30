import javax.swing.*;
import java.io.File;

public class MP3Chooser{
    public static boolean fileChosen = false; //might be useful for "Make sure to choose files" or something

    //UNUSED: want to have a preference pane where you can choose your default music folder
    public static String setDefaultDirectory(){
        JFileChooser chooser = new JFileChooser();

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {

            return chooser.getSelectedFile().getAbsolutePath();
        }
        return null; //may bug
    }
    //choose folder you want to play music from (for albums/playlists)
    public static File[] chooseMusicFolder(String defaultDir){
        JFileChooser chooser = new JFileChooser(defaultDir);

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File folder = new File(chooser.getSelectedFile().getAbsolutePath());
            int count = 0;

            //count number of actual files
            for (int i = 0; i < folder.listFiles().length; i++) {
                if (folder.listFiles()[i].isFile()) {
                    count++;
                }
            }

            File[] listOfFiles = new File[count];
            int currentIndex = 0;

            //fill a File[] of appropriate size with actual files
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    listOfFiles[currentIndex] = file;
                    currentIndex++;
                }
            }

	    //scans for mp3 files; if none asks for reselection
	    boolean anyMP3 =false;
	    for(File file : folder.listFiles()) {
		if (file.getName().toLowerCase().contains(".mp3")) {
                    anyMP3 =true;
                }
            }
	    if(anyMP3==false){
	        JOptionPane.showMessageDialog(null, "You selected a folder with no mp3 files in it. Please select another folder.", "Bad Folder", JOptionPane.ERROR_MESSAGE);
		return chooseMusicFolder(defaultDir); //recursion until successful folder is found
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

        for(int i = 0; i < maxMP3s; i++){
            if (listOfFiles[i].getName().toLowerCase().contains(".mp3")){
                count++;
            }
        }

        File[] onlyMP3s = new File[count];
        int currentIndex = 0;

        for(File file : listOfFiles){
            if (file.getName().toLowerCase().contains(".mp3")){
                onlyMP3s[currentIndex] = file;
                currentIndex++;

            }
        }

        return onlyMP3s;
    }
}
