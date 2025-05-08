import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A bot that guesses based on previously correct letters.
 * 
 * @author Jon Roberts
*/
public class AveryBot implements Bot {

    // internal state fields
    private int count = 0;
    private ArrayList<String> solutions = new ArrayList<>();

    /**
    * Bot that uses useful starting words to guess what the next word is.
    *
    * @param puzzle the puzzle to try and solve
    *
    * @return a guess that matches found letters
    */
    public String attempt(Wordle puzzle) {
        String results = "xxxxx";

        // bring the solution words into memory
        if (solutions == null || solutions.isEmpty()) {
		    try {
			    BufferedReader reader = new BufferedReader(new FileReader(Wordle.SOLUTIONS_FILE));
			    String line = reader.readLine();
                while (line != null) {
                    this.solutions.add(line.toLowerCase());
                    line = reader.readLine();
                }
                reader.close();
		    } catch (IOException e) {
			    System.out.println("Cannot access words file: " + e.getMessage());
		    }
        }

        // identify confirmed letters from previous guesses
        char learned[] = {'?','?','?','?','?'};
        for (Attempt attempt : puzzle.getAttempts()) {
            String word = attempt.getWord();
            boolean[] correct = attempt.getCorrects();
            for (int i = 0; i < 5; i++) {
                if (correct[i]) {
                    learned[i] = word.charAt(i);
                }
            }
        }
    
        // select the next matching word
        while (this.count < solutions.size()) {
            String candidate = solutions.get(this.count++);
            boolean matches = true;
            for (int i=0; i < 5; i++) {
                if (learned[i] != '?') {
                    if (candidate.charAt(i) != learned[i]) {
                        matches = false;
                    }
                }
            }
            if (matches) {
                // provides first guesses that improve the probability of guessing the word
                if(this.count <= 3) {
                    final String[] startingWords = {"", "crane", "slipt", "gumbo"};
                    results = startingWords[this.count];
                }else{
                    results = candidate;
                }
                break;
            }
        }
        return results;
    }
}
