package evaluation;

import core.*;

public class RatioFilledEvaluator implements Evaluator {

    @Override
    public double eval(Grid g) {
        return ((double)g.getNumFilled())/(g.getWidth()*g.getHeight());
    }
}
