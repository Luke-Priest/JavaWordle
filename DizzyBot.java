import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A bot that guesses random words from the solutions dictionary.
 * 
 * @author Jon Roberts
*/
public class DizzyBot implements Bot {

    // internal state fields
    private ArrayList<String> solutions = new ArrayList<>();
    private HashSet<String> tried = new HashSet<>();

    /**
    * Generates a guess by selecting a random word from the solution dictionary.
    *
    * @param puzzle the puzzle to try and solve
    *
    * @return a random solution not previously selected
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

        // select a new random solution word
        do {
            results = solutions.get((int)(Math.random() * solutions.size()));
        } while (tried.contains(results));
        tried.add(results);
        return results;
    }
}
