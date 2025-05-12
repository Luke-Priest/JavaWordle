import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * A smarter Wordle bot
 * Avoids repeating guesses and uses logical deduction from past attempts.
 * 
 * Assumes Attempt provides: getWord(), getCorrects(), getAlmosts()
 */
public class LukeBot implements Bot {

    private int guessCount = 0;
    private final List<String> solutions = new ArrayList<>();
    private final Set<Character> confirmedPresent = new HashSet<>();
    private final Set<Character> confirmedAbsent = new HashSet<>();
    private final char[] learned = {'?', '?', '?', '?', '?'};

    public LukeBot() {
        // solution words - luke
        try (BufferedReader reader = new BufferedReader(new FileReader(Wordle.SOLUTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                solutions.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Cannot access words file: " + e.getMessage());
        }
    }

    @Override
    public String attempt(Wordle puzzle) {
        updateKnowledge(puzzle);

        // Awesome Avery Starting word combos! (crane is the best so far) - avery
        final String[] startingWords = {"crane", "slipt", "gumbo"}; // best - avery
        //final String[] startingWords = {"tails", "round", "guide"};
        if (guessCount < startingWords.length) {
            String guess = startingWords[guessCount++];
            solutions.remove(guess);
            return guess;
        }

        // filters all the words based on what it knows
        for (Iterator<String> it = solutions.iterator(); it.hasNext(); ) {
            String candidate = it.next();
            if (matchesKnowledge(candidate)) {
                it.remove();
                guessCount++;
                return candidate;
            }
        }

        // if it has no matches it just vomits out zebra - avery
        return "zebra";
    }

    private void updateKnowledge(Wordle puzzle) {
        for (Attempt attempt : puzzle.getAttempts()) {
            String word = attempt.getWord();
            boolean[] corrects = attempt.getCorrects();
            boolean[] almosts = attempt.getAlmosts(); 
            // sorts them - avery
            for (int i = 0; i < 5; i++) {
                char c = word.charAt(i);
                if (corrects[i]) {
                    learned[i] = c;
                    confirmedPresent.add(c);
                } else if (almosts[i]) {
                    confirmedPresent.add(c);
                } else {
                    confirmedAbsent.add(c);
                }
            }
        }
    }

    private boolean matchesKnowledge(String word) {
        // check for learned
        for (int i = 0; i < 5; i++) {
            if (learned[i] != '?' && word.charAt(i) != learned[i]) {
                return false;
            }
        }

        // Must include all known present letters
        for (char c : confirmedPresent) {
            if (!word.contains(String.valueOf(c))) {
                return false;
            }
        }

        // no absent allowed (unless they're also confirmed present)
        for (char c : confirmedAbsent) {
            if (!confirmedPresent.contains(c) && word.contains(String.valueOf(c))) {
                return false;
            }
        }

        return true;
    }
}
