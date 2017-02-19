/*
 * Evaluates puzzles based on the ratio: number of filled squares to
 * number of empty squares in the solved puzzle.
 */

package nonograms.evaluation;

import nonograms.core.*;

public class WeightedRatioFilledEvaluator implements Evaluator {

    @Override
    public double eval(Game g) {
        Grid grid = g.getGrid();
        double weight = 1000;
        RatioFilledEvaluator evaler = new RatioFilledEvaluator();
        return weight*(.5-Math.abs(.5-evaler.eval(g)));
    }

    @Override
    public String description() {
        return "Gives a weight from 0 to 1000 based on the linear closeness to"+
                "a ratio of 1/2 filled in squares in the solution.";
    }
}
