/*
 * Evaluates puzzles based on the most constrained row or column;
 * if the puzzle is 5x5 and there is a 4, then that is constrained
 * to 2 locations
 */

package evaluation;

import core.*;

import java.util.Collections;

public class MostConstrainedEvaluator implements Evaluator {

    @Override
    public double eval(Game g) {
        return Collections.max(Util.getListOfConstraintments(g));
    }
}
