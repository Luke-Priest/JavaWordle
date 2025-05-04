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
}
