package evaluation;

import core.*;

import java.util.Collections;

public class LeastConstrainedEvaluator implements Evaluator {

    @Override
    public double eval(Game g) {
        return Collections.min(Util.getListOfConstraintments(g));

    }

    @Override
    public String description() {
        return "";
    }
}
