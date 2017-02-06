package evaluation;

import core.*;

public class RatioFilledEvaluator implements Evaluator {

    @Override
    public double eval(Game g) {
        Grid grid = g.getGrid();
        return ((double)grid.getNumFilled())/(grid.getWidth()*grid.getHeight());
    }
}
