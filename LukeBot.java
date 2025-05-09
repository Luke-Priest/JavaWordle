import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * A smarter Wordle bot that guesses based on all known feedback.
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
        // Load solution words from file
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

        // High-quality starting words
        final String[] startingWords = {"crane", "slipt", "gumbo"};
        if (guessCount < startingWords.length) {
            String guess = startingWords[guessCount++];
            solutions.remove(guess);
            return guess;
        }

        // Filter valid words based on knowledge
        for (Iterator<String> it = solutions.iterator(); it.hasNext(); ) {
            String candidate = it.next();
            if (matchesKnowledge(candidate)) {
                it.remove();
                guessCount++;
                return candidate;
            }
        }

        // Fallback if no matches
        return "adieu";
    }

    private void updateKnowledge(Wordle puzzle) {
        for (Attempt attempt : puzzle.getAttempts()) {
            String word = attempt.getWord();
            boolean[] corrects = attempt.getCorrects();
            boolean[] almosts = attempt.getAlmosts();  // CORRECT method from Attempt

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
        // Match known letter positions
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

        // Must not include known absent letters (unless they're also confirmed present)
        for (char c : confirmedAbsent) {
            if (!confirmedPresent.contains(c) && word.contains(String.valueOf(c))) {
                return false;
            }
        }

        return true;
    }
}
