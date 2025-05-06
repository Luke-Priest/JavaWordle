/**
 * An autonomous agent capable of submitting a guess to a given puzzle.
 * Any implementation can use the dictionaries, history of attempts on the puzzle, 
 * and its own state to determine a next best guess. 
 * 
 * @author Jon Roberts
*/
public interface Bot {

    /**
     * Generate a guess to a given puzzle.
     * 
     * @param puzzle the puzzle to try and solve
     *
     * @return the word to guess
    */
    public String attempt(Wordle puzzle);

    /**
     * Test select bot against a puzzle. 
    */
    public static void main(String[] args) {

        // create a puzzle and player
        // Wordle puzzle = new Wordle("pixel");
        Wordle puzzle = new Wordle();
        // Bot player = new DuhBot();
        // Bot player = new DizzyBot();
        // Bot player = new DingBot();
        Bot player = new CoolerBot();

        // have bot attempt until solving the wordle
        do {
            puzzle.evaluate(player.attempt(puzzle));
        } while (! puzzle.isSolved());
        
        // output the results for the bot
        for (Attempt attempt : puzzle.getAttempts()) {
            System.out.println("guess: " + attempt.getWord());
        }
        System.out.println("  It took " + puzzle.getAttempts().length + " tries, but you got it.");
    }
}
