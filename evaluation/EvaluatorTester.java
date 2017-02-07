package evaluation;

import core.Game;
import core.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Tester class for evaluators
 */

public class EvaluatorTester {


    public static void main(String[] args) {
        Game gm = new Game(5,5);
        Rule rl1 = new Rule(new int[]{3,1});
        Rule rl2 = new Rule(new int[]{2});
        Rule rl3 = new Rule(new int[]{3});
        Rule rl4 = new Rule(new int[]{1});
        Rule rl5 = new Rule(new int[]{3});
        Rule rl6 = new Rule(new int[]{3});
        Rule rl7 = new Rule(new int[]{3});
        Rule rl8 = new Rule(new int[]{1,3});
        Rule rl9 = new Rule(new int[]{1});
        Rule rl0 = new Rule(new int[]{1,1});
        gm.setTop(new Rule[]{rl1, rl2, rl3,rl4,rl5});
        gm.setLeft(new Rule[]{rl6,rl7,rl8,rl9,rl0});
        gm.solve();

        List<Evaluator> evals = new ArrayList<>();
        evals.add(new RatioFilledEvaluator());
        evals.add(new LeastConstrainedEvaluator());
        evals.add(new MostConstrainedEvaluator());
        for (Evaluator eval : evals) {
            System.out.println(eval.eval(gm));
        }
    }
}
