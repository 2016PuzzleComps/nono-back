/*
 * Evaluates puzzles based on the ratio: number of filled squares to
 * number of empty squares in the solved puzzle.
 */

package evaluation;

import core.*;

import java.util.Arrays;

public class RatioFilledEvaluator implements Evaluator {

    @Override
    public double eval(Game g) {
        Grid grid = g.getGrid();
        return ((double)grid.getNumFilled())/(grid.getWidth()*grid.getHeight());
    }
}
