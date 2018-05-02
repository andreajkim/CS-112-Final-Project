import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;


public class MP3Chooser{
    
    public static boolean fileChosen = false; //might be useful for "Make sure to choose files" or something
    public static boolean usingDefault = true; //whether the user is using the default directory or not
    public static String filePathDirectory; 
    
    //checks to see if a default directory exists or not; useful for default initiation -Martin
    public static boolean checkDefaultDirectory(){
	try{
	    String checkString;
	    File input = new File("DefaultDirectorySavePath.txt");

	    Scanner sc = new Scanner(input);	   
	    File testFile = new File(sc.nextLine());
	    
	    //check to see if directory exists
	    if(testFile.isDirectory() == false){
		
	        input.delete();
		return false;
		
	    }else{
		return true;
	    }
	    
	} catch(FileNotFoundException e) {
	    System.err.println(e);
	    System.out.println("4");
	    return false;
	}
    } //end check Default directory method

    
    //saves the filePath of the DefaultDirectory in a .txt file called "DefaultDirectorySavePath.txt" -Martin
    public static void setDefaultDirectory(){
	String toPrint = chooseMusicFolder(false);
	
	try{	    
	    File output = new File("DefaultDirectorySavePath.txt");	    
	    PrintWriter printer = new PrintWriter(output);
	    
	    printer.write(toPrint);	    
	    printer.close();
	    return;
	
	} catch(FileNotFoundException e) {
	    System.err.println("Set Default Directory didn't work.");
	    System.err.println(e);
	    return;
	}     
    }

    //overloads setDefaultDirectory, this way, if no string is passed in it will ask for one (as above)
    //However, if a String is passed in it doesn't need to ask for one -Martin
    public static void setDefaultDirectory(String toPrint){	
	try{	    
	    File output = new File("DefaultDirectorySavePath.txt");	    
	    PrintWriter printer = new PrintWriter(output);
	    
	    printer.write(toPrint);	    
	    printer.close();
	    return;
	
	} catch(FileNotFoundException e) {
	    System.err.println("Set Default Directory didn't work.");
	    System.err.println(e);
	    return;
	}     
    }

    //method for making Default Use bool false -Martin
    public static void setFalseDefaultUse(){
        usingDefault = false;
	return;
    }


    //gets the filePath string from the .txt file and returns it -Martin
    public static String getDefaultDirectory(){
	//similar to LoadPointfromfile from FileIO lab

	try{
	    String toReturn = null;
	    File input = new File("DefaultDirectorySavePath.txt");
	    
	    Scanner sc = new Scanner(input);
	    
	    if(sc.hasNextLine()){		
		toReturn = sc.nextLine();
	    }
		    
	    return toReturn;
	    
	} catch(FileNotFoundException e) {
	    System.err.println("File not found.");
	    System.err.println(e);
	    return null;
	}
	
	   
    }//end getDefaultDir()
     

    //choose folder you want to play music from (for albums/playlists)
    //will check to insure that it contains at least one mp3 and then return the filePath name
    //this is useful for default directory usage
    //bool gottaPick indicates whether this use of the method, it is necessary for the user to pick a song
    public static String chooseMusicFolder(boolean gottaPick){
	System.out.println("chooseMusicFolder");
	
        JFileChooser chooser = new JFileChooser();
	
	
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
	
	chooser.setDialogTitle("Open Music Folder");
	
	
	//File currentdirectory = new File("/Users/martin_glusker/Documents/GitHub/CS-112-Final-Project");
	//chooser.setCurrentDirectory(currentdirectory);
        
	    
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

	    //in the case there aren't any mp3s, an error message is thrown
	    if(anyMP3==false){
		
	        JOptionPane.showMessageDialog(null, "You selected a folder with no mp3 files in it. Please select another folder.", "Bad Folder", JOptionPane.ERROR_MESSAGE);
		
		return chooseMusicFolder(false); //recursion until successful folder is found
	    }
	    	    		
            return chooser.getSelectedFile().getAbsolutePath();
	    
        } else {
	    
	    if(gottaPick){
	        JOptionPane.showMessageDialog(null, "You need to pick a folder for us to play music.", "Pick a Folder -_-", JOptionPane.ERROR_MESSAGE);
		
		return chooseMusicFolder(true); //recursion until successful folder is found
	    }
	}
	    

        return null; //may bug
    }
    

    //from all files in directory, choose only the MP3s
    //this method takes in filePath names and then convert it into a file array with only the mp3s in the name in it
    public static File[] chooseOnlyMP3s(String filePath){    
        
        File folder = new File(filePath);
	filePathDirectory = filePath;
	
	//count number of actual files
	int countFiles =0;
	for (int i = 0; i < folder.listFiles().length; i++) {
	    if (folder.listFiles()[i].isFile()) {
		countFiles++;
	    }
	}

	//create correctly sized array
	File[] listOfFiles = new File[countFiles];
        

	//fill a array of files (File[]) of appropriate size with actual files
	int currentIndex=0;
	for (File file : folder.listFiles()) {
	    if (file.isFile()) {
		listOfFiles[currentIndex] = file;
		currentIndex++;
	    }
	}

	//count the number of mp3s in array
	int countMP3s =0;
        for(int i = 0; i < countFiles; i++){
            if (listOfFiles[i].getName().toLowerCase().contains(".mp3")){
                countMP3s++;
            }
        }

	//create correctly sized array for exclusively mp3 files
        File[] onlyMP3s = new File[countMP3s];

	//fill array with mp3 files
        currentIndex = 0;
        for(File file : listOfFiles){
            if (file.getName().toLowerCase().contains(".mp3")){
                onlyMP3s[currentIndex] = file;
                currentIndex++;
            }
        }

        return onlyMP3s;
	
    }//end chooseOnlyMP3s();
    
}//end MP3Chooser
