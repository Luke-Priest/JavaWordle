import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CoolerBot implements Bot {

    // variables and stuff here
    String[] wrong;
    String[] right;
    boolean[] rightSpot = {false, false, false, false, false};
    String[] peeSpot; 
    static String[] words;
    boolean fart = true;

    /**
    * Generates a guess by selecting the next word in the solution dictionary.
    *
    * @param puzzle the puzzle to try and solve
    *
    * @return the next word in the solution dictionary
    */

    try {
        List<String> wordsList = Files.readAllLines(Paths.get("la-dictionary.txt"));
        words = wordsList.toArray(new String[0]);
    } catch (IOException e) {
        System.err.println("Error reading the file: " + e.getMessage());
    }



    public String attempt(Wordle puzzle) {
        if (fart){
            fart = false;
            return 'tales';
        }
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < 5; j++;) {
                if (rightSpot[j] == false) {
                    
                } else {
                    
                }
            }
        } 
        
    }
}