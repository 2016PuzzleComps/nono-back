/*
 * Evaluates puzzles based on the most constrained row or column;
 * if the puzzle is 5x5 and there is a 4, then that is constrained
 * to 2 locations
 */

package evaluation;

import core.*;

public class MostConstrainedEvaluator implements Evaluator {

    @Override
    public double eval(Game g) {
        int mostConstrained = Integer.MAX_VALUE;
        int currentSize;
        for (Rule rule : g.getRule(true)) {
            currentSize = g.getGrid().getWidth();
            for (int i : rule.getRuleList()) {
                currentSize -= i + 1;
            }
            // The last block of filled in squares can be at the end
            currentSize += 1;
            if (currentSize < mostConstrained) {
                mostConstrained = currentSize;
            }
        }
        for (Rule rule : g.getRule(false)) {
            currentSize = g.getGrid().getHeight();
            for (int i : rule.getRuleList()) {
                currentSize -= i + 1;
            }
            // The last block of filled in squares can be at the end
            currentSize += 1;
            if (currentSize < mostConstrained) {
                mostConstrained = currentSize;
            }
        }
        return mostConstrained;
    }
}
