import core.*;

import java.util.List;
import java.util.ArrayList;

public class GameGenerator {

    public GameGenerator() {

    }

    public Game generate() {
        GridGenerator gridGen = new GridGenerator();
        Grid grid = gridGen.generate(10,10,0.3,0.3);
        Game result = new Game();
        result.setGrid(grid);
        for (int i = 0; i < result.width; i++) {
            result.getRule(true)[i] = fullRuleFromRow(result.getGrid.getColumn(i));
        }
        for (int i = 0; i < result.height; i++) {
            result.getRule(false)[i] = fullRuleFromRow(result.getGrid.getRow(i));
        }
        return result;
    }

    public Rule fillRuleFromRow(int[] row) {
        List<Integer> ruleList = new ArrayList<Integer>();
        int counter = 0;
        for (int i : row) {
            if (i == 1) {
                counter++;
            }
            else {
                if (counter !=0) {
                    ruleList.add(counter);
                    counter = 0;
                }
            }
        }
        if (counter !=0) {
            ruleList.add(counter);
            counter = 0;
        }
        int[] rl = ruleList.toArray(new int[ruleList.size()]);
        return new Rule(rl);
    }
}
