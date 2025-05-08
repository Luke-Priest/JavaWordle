import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
// import java.util.Hashtable;

/**
 * A puzzle involving an unknown five-letter word.
 * 
 * @author Jon Roberts
*/
public class Wordle {

    // internal state fields
    private String word;
    private boolean solved = false;
    private ArrayList<Attempt> attempts = new ArrayList<>();
    private static ArrayList<String> solutions = null;
    private static ArrayList<String> potentials = null;

    /**
     * The name of the file with the solution words.
     */
    public static final String SOLUTIONS_FILE = "la-dictionary.txt";

    /**
     * The name of the file with the additional permitted words.
     */
    public static final String POTENTIALS_FILE = "ta-dictionary.txt";

    /**
     * Creates a new puzzle with a random word. 
     */
    public Wordle() {
        digest();
        this.word =solutions.get((int)(Math.random()*(solutions.size())));
    }

    /**
     * Creates a new puzzle with a specific word. 
     * 
     *  @param solution the unknown word for this puzzle
     */
    public Wordle (String solution) {
        digest();
        if (solutions.contains(solution.toLowerCase())) {
            this.word = solution.toLowerCase();
        } else {
            throw new IllegalArgumentException("The word: " + solution +" is not a valid 5 letter word.");
        }
    }

    /**
     * Indicates if the correct solution has been attempted. 
     * 
     * @return  whether the puzzle has been solved
     */
    public boolean isSolved() {
        return solved;
    }

    /**
     * Produces a record of all attempts made on the puzzle. 
     * 
     * @return  array of Attempt instances for each guess submitted
     */
    public Attempt[] getAttempts() {
        if (this.attempts != null) {
            return this.attempts.toArray(new Attempt[0]);
        } else {
            return null;
        }
    }

    // parse dictionary into list of words
    private static ArrayList<String> digest(String filename) {
        ArrayList<String> results = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
            while (line != null) {
                results.add(line.toLowerCase());
                line = reader.readLine();
            }
            reader.close();
		} catch (IOException e) {
			System.out.println("Cannot access words file: " + e.getMessage());
		}
        return results;
    }

    // brings valid words into memory
    private static void digest() {
        if ((solutions == null) || (solutions.isEmpty())) {
            solutions = digest(SOLUTIONS_FILE);
        }
        if ((potentials == null) || (potentials.isEmpty())) {
            potentials = digest(POTENTIALS_FILE);
        }
    }

    /**
     * Determine and record the results of a guess.
     * 
     * @param  candidate    a word submitted as a potential solution   
     */
    public void evaluate(String candidate) {

        // ensure the guess is valid
        char[] candidates = candidate.toLowerCase().toCharArray();
        if (candidates.length != 5) {
            throw new IllegalArgumentException("Any attempt must be a five letter word.");
        }
        char[] targets = this.word.toCharArray();

        // determine the correct and close letters
        boolean[] corrects = {false, false, false, false, false};
        boolean[] almosts = {false, false, false, false, false};
        for (int i = 0; i < targets.length; i++) {
            if (targets[i] == candidates[i]) {
                corrects[i] = true;
                if (almosts[i]) {
                    almosts[i] = false;
                }
            } else {
                int found = 0;
                int frequency = 0;
                char letter = targets[i];
                for (int j = 0; j < targets.length; j++) {
                    if (targets[j] == letter) {
                        frequency++;
                    }
                }
                for (int k = 0; k < candidates.length; k++) {
                    if (found == frequency) { break; }
                    if ((k != i) && candidates[k] == letter) {
                        almosts[k] = true;
                        found++;
                    }
                }
            }
        }

        // determine if the guess is the solution
        boolean flawless = true;
        for (boolean correct : corrects) {
            if (! correct) {
                flawless = false;
                break;
            } 
        }
        this.solved = flawless;

        // record the attempt
        this.attempts.add(new Attempt(candidate, corrects, almosts));
    }

    /**
     * Indicates if a given word is a valid guess.
     * 
     * @param word  a word to check 
     * 
     * @return  whether the word is in the dictionaries
     */
    public static boolean isValid(String word) {
        boolean result = false;
        if (solutions.contains(word.toLowerCase()) || potentials.contains(word.toLowerCase())) {
            result = true;
        }
        return result;
    }
}