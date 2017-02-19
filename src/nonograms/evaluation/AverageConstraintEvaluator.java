package nonograms.evaluation;

import nonograms.core.Game;

import java.util.List;

public class AverageConstraintEvaluator implements Evaluator{

    @Override
    public double eval(Game g) {
        List<Integer> constrainments = Util.getListOfConstraintments(g);
        return constrainments.stream().mapToInt(Integer::intValue).sum()/constrainments.size();
    }
    @Override
    public String description() {
        return "";
    }
}
