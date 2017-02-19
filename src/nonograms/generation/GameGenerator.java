package nonograms.generation;

import nonograms.core.Game;
import nonograms.core.Grid;
import nonograms.core.Rule;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class GameGenerator {

    public GameGenerator() {

    }

    public Game generate() {
        int size = 10;
        return generateSize(size);
    }

    public Game generateSize(int size) {
        GridGenerator gridGen = new GridGenerator();
        Grid grid = gridGen.generate(size,size,0.3,0.3);
        Game result = new Game(size,size);
        result.setGrid(grid);
        for (int i = 0; i < result.width(); i++) {
            result.getRule(true)[i] = fillRuleFromRow(result.getGrid().getColumn(i));
        }
        for (int i = 0; i < result.height(); i++) {
            result.getRule(false)[i] = fillRuleFromRow(result.getGrid().getRow(i));
        }
        result.clear();
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
        int[] rl = new int[ruleList.size()];
        for(int i = 0; i < ruleList.size(); i++) {
            rl[i] = ruleList.get(i);
        }
        return new Rule(rl);
    }

    public static void main(String[] args) {
        GameGenerator gg = new GameGenerator();
        Game g = gg.generate();
        for (int[] row : g.getGrid().toArr()) {
            System.out.println(Arrays.toString(row));
        }
        g.solve();
        for (int[] row : g.getGrid().toArr()) {
            System.out.println(Arrays.toString(row));
        }
    }
}
