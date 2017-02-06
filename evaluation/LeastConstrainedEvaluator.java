package evaluation;

import core.*;

public class LeastConstrainedEvaluator implements Evaluator {

    @Override
    public double eval(Game g) {
        int leastConstrained = 0;
        int currentSize;
        for (Rule rule : g.getRule(true)) {
            currentSize = g.getGrid().getWidth();
            for (int i : rule.getRuleList()) {
                currentSize += i + 1;
            }
            // The last block of filled in squares can be at the end
            currentSize -= 1;
            if (currentSize > leastConstrained) {
                leastConstrained = currentSize;
            }
        }
        for (Rule rule : g.getRule(false)) {
            currentSize = g.getGrid().getHeight();
            for (int i : rule.getRuleList()) {
                currentSize -= i + 1;
            }
            // The last block of filled in squares can be at the end
            currentSize += 1;
            if (currentSize > leastConstrained) {
                leastConstrained = currentSize;
            }
        }
        return leastConstrained;
    }
}
