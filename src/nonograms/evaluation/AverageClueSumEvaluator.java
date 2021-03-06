package nonograms.evaluation;

import nonograms.core.Game;
import nonograms.core.Rule;

import java.util.Arrays;

public class AverageClueSumEvaluator implements Evaluator {

    @Override
    public double eval(Game g) {
        double sum=0.0;
        for (Rule rule : g.getRule(true)) {
            sum += Arrays.stream(rule.getRuleList()).sum();
        }
        for (Rule rule : g.getRule(false)) {
            sum += Arrays.stream(rule.getRuleList()).sum();
        }
        return sum/(g.getGrid().toArr().length+g.getGrid().toArr()[0].length);
    }

    @Override
    public String description() {
        return "";
    }
}
