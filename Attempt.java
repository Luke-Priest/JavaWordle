/**
 * A submitted guess to a puzzle and the results of its evaluation.
 * An attempt includes indications for which letters of the guess match the solution
 * by position (Corrects) and which letters of the guess are in the solution but are
 * not in the same position as they are in the solution (Almosts).
 * 
 * @author Jon Roberts
*/
public class Attempt {
    
    // internal state fields
    private String word;
    private boolean[] corrects = {false, false, false, false, false};
    private boolean[] almosts = {false, false, false, false, false};

    // initialize values for an attempt
    /**
    * Create a new attempt record with associated results.
    *
    * @param word   the word that was guessed
    * @param corrects   an array of indicators for correct letters in correct position
    * @param almosts   an array of indicators for correct letters in incorrect postion
    */
    protected Attempt(String word, boolean[] corrects, boolean[] almosts) {
        this.word = word;
        this.corrects = corrects;
        this.almosts = almosts;
    }

    /**
     * Retrieve the word submitted as a guess.
     * 
     * @return  the guessed word
    */
    public String getWord() {
        return this.word;
    }

    /**
     * Retrieve indications for correctly matched letter positions. 
     * 
     * @return  array of boolean values for each letter position
    */
    public boolean[] getCorrects() {
        return this.corrects;
    }

    /**
     * Retrieve indications for matched letters in incorrect position. 
     * 
     * @return  array of boolean values for each letter position
    */
    public boolean[] getAlmosts() {
        return this.almosts;
    }

    /**
     * Retrieve a formatted string representation of the attempt.
     * Results have letters colored green and yellow with ANSI color escape sequences
     * to indicate correct and almost letters.
     * 
     * @return  formatted resulting string
    */
    public String toString() {
        StringBuffer result = new StringBuffer();
        char[] letters = this.word.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            if (this.corrects[i]) {
                result.append("\033[1;32m" + letters[i] + "\033[0;0m");
            } else {
                if (this.almosts[i]) {
                    result.append("\033[1;33m" + letters[i] + "\033[0;0m");
                } else {
                    result.append(letters[i]);
                }
            }
        }
        return result.toString();
    }
}