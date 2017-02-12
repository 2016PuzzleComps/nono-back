package evaluation;

import core.Game;
import core.Rule;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<Integer> getListOfConstraintments(Game g) {
        List<Integer> constraintments = new ArrayList<>();

        int currentSize;
        for (Rule rule : g.getRule(true)) {
            currentSize = g.getGrid().getWidth();
            for (int i : rule.getRuleList()) {
                currentSize -= i + 1;
            }
            // The last block of filled in squares can be at the end
            currentSize += 1;
            constraintments.add(currentSize);
        }
        for (Rule rule : g.getRule(false)) {
            currentSize = g.getGrid().getHeight();
            for (int i : rule.getRuleList()) {
                currentSize -= i + 1;
            }
            // The last block of filled in squares can be at the end
            currentSize += 1;
            constraintments.add(currentSize);
        }

        return constraintments;
    }
}
