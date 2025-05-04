import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A bot that guesses each word in the solution dictionary in order.
 * 
 * @author Jon Roberts
*/
public class DuhBot implements Bot {

    // internal state fields
    private int count = 0;
    private ArrayList<String> solutions = new ArrayList<>();

    /**
    * Generates a guess by selecting the next word in the solution dictionary.
    *
    * @param puzzle the puzzle to try and solve
    *
    * @return the next word in the solution dictionary
    */
    public String attempt(Wordle puzzle) {

        // bring solution words into memory
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

        // select the next word
        return solutions.get(count++);
    }
}