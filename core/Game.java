package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
public class Game {
    Rule[] top;
    Rule[] left;
    int height;
    int width;
    Grid grid;

    public Game(int width, int height) {
        this.top = new Rule[width];
        this.left = new Rule[height];
        this.height = height;
        this.width = width;
        this.grid = new Grid(width, height);
    }

    public boolean isSolved() {
        for(int i=0;i<height;i++) {
            if(!this.left[i].satisfies(this.grid.getRow(i))) {
                return false;
            }
        }
        for(int i=0;i<width;i++) {
            if(!this.top[i].satisfies(this.grid.getColumn(i))) {
                return false;
            }
        }
        return true;
    }

    public void clear() {
        this.grid.clear();
    }


    public boolean solve() {
        clear();
        List<List<int[]>> rowPossibilities = new ArrayList<List<int[]>>();
        List<List<int[]>> columnPossibilities = new ArrayList<List<int[]>>();
        for(int i = 0;i<height;i++) {
            rowPossibilities.add(left[i].getAllSatisfyingRows(width));
        }
        for(int i = 0;i<width;i++) {
            columnPossibilities.add(top[i].getAllSatisfyingRows(height));
        }
        List<Set<List<Integer>>> columnPossibilitiesSet = new ArrayList<Set<List<Integer>>>();
        for(int i = 0;i<width;i++) {
            Set<List<Integer>> thisColumnSet = new HashSet<List<Integer>>();
            for (int[] col : columnPossibilities.get(i)) {
                thisColumnSet.add(getListFromArray(col));
            }
            columnPossibilitiesSet.add(thisColumnSet);
        }

        return solveHelper(columnPossibilitiesSet,rowPossibilities,0);

    }

    public boolean solveHelper(List<Set<List<Integer>>> columnPossibilitiesSet, List<List<int[]>> rowPossibilities, int depth) {
        if (depth == height) {
            for (int i=0;i<width;i++) {
                if (!columnPossibilitiesSet.get(i).contains(getListFromArray(this.grid.getColumn(i)))) {
                    return false;
                }
                else {
                    System.out.println("hello");
                }
            }
            return true;
        }
        for (int[] row : rowPossibilities.get(depth)) {
            this.grid.setRow(row, depth);
            if (solveHelper(columnPossibilitiesSet,rowPossibilities,depth + 1)) {
                return true;
            }
        }
        this.grid.setRow(new int[width], depth);
        return false;
    }

    public static List<Integer> getListFromArray(int[] arr) {
        List<Integer> lst = new ArrayList<Integer>(arr.length);
        for (int j = 0; j < arr.length; j++) {
            lst.add(Integer.valueOf(arr[j]));
        }
        return lst;
    }

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
        gm.top = new Rule[]{rl1, rl2, rl3,rl4,rl5};
        gm.left = new Rule[]{rl6,rl7,rl8,rl9,rl0};
        System.out.println(gm.solve());
        for (int[] row : gm.grid.grid) {
            System.out.println(Arrays.toString(row));
        }
    }
}
