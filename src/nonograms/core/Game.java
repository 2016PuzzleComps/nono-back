package nonograms.core;

import java.util.*;

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

    public int height() {
        return this.height;
    }

    public int width() {
        return this.width;
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

    //Returns true if the board can be solved. Changes the grid to solved state.
    public boolean solve() {
        clear();
        //Precompute row and column possibilities.
        List<List<int[]>> rowPossibilities = new ArrayList<List<int[]>>();
        List<List<int[]>> columnPossibilities = new ArrayList<List<int[]>>();
        for(int i = 0;i<height;i++) {
            rowPossibilities.add(left[i].getAllSatisfyingRows(width));
        }

        for(int i = 0;i<width;i++) {
            columnPossibilities.add(top[i].getAllSatisfyingRows(height));
        }

        //Fill in the items that can be immediately filled in given what we know
        solveImmediateConstraints();

        //Identify all the row and column possibilities that don't work with the current values
        List<List<int[]>> toRemove = new ArrayList<>();
        for (int i=0; i<rowPossibilities.size(); i++) {
            toRemove.add(new ArrayList<>());
            for (int[] possibility : rowPossibilities.get(i)) {
                for (int j=0; j<width; j++) {
                    if (grid.get(j,i) == 1 && possibility[j] == 0) toRemove.get(i).add(possibility);
                }
            }
        }

        //Remove all the row possibilities that don't work with the current values
        for (int i=0; i<toRemove.size(); i++) {
            for (int j=0; j<toRemove.get(i).size(); j++) {
                rowPossibilities.get(i).remove(toRemove.get(i).get(j));
            }
        }
        printGrid();

        //Change the list of column possibilities to a set for quick lookup.
        //Can't have a set of int[]'s so we have to convert to List<Integer>
        //We're creating a list of sets here, one for each column.
        List<Set<List<Integer>>> columnPossibilitiesSet = new ArrayList<Set<List<Integer>>>();
        for(int i = 0;i<width;i++) {
            Set<List<Integer>> thisColumnSet = new HashSet<List<Integer>>();
            for (int[] col : columnPossibilities.get(i)) {
                thisColumnSet.add(getListFromArray(col));
            }
            columnPossibilitiesSet.add(thisColumnSet);
        }

        //Calls recursive method, see below.
        return solveHelper(columnPossibilitiesSet,rowPossibilities,0);

    }

    //Each call of this function has an associated depth to it.
    public boolean solveHelper(List<Set<List<Integer>>> columnPossibilitiesSet, List<List<int[]>> rowPossibilities, int depth) {
        //If this is the last depth, check if the columns are valid.
        if (depth == height) {
            for (int i=0;i<width;i++) {
                if (!columnPossibilitiesSet.get(i).contains(getListFromArray(this.grid.getColumn(i)))) {
                    return false;
                }
                else {
                    //System.out.println("hello");
                }
            }
            return true;
        }
        //Otherwise, try filling in the next row with each choice and
        //call recursively.
        for (int[] row : rowPossibilities.get(depth)) {
            this.grid.setRow(row, depth);
            if (solveHelper(columnPossibilitiesSet,rowPossibilities,depth + 1)) {
                return true;
            }
        }
        //Clear the row. 
        this.grid.setRow(new int[width], depth);
        return false;
    }

    public void solveImmediateConstraints() {
        // Find how constrained everything is
        int currentSize;
        int[] topConstraints = new int[grid.getWidth()];
        int[] leftConstraints = new int[grid.getHeight()];
        int j=0;
        for (Rule rule : getRule(true)) {
            currentSize = getGrid().getWidth();
            for (int i : rule.getRuleList()) {
                currentSize -= i + 1;
            }
            // The last block of filled in squares can be at the end
            currentSize += 1;
            topConstraints[j] = currentSize;
            j++;
        }
        j=0;
        for (Rule rule : getRule(false)) {
            currentSize = getGrid().getHeight();
            for (int i : rule.getRuleList()) {
                currentSize -= i + 1;
            }
            // The last block of filled in squares can be at the end
            currentSize += 1;
            leftConstraints[j] = currentSize;
            j++;
        }

        // Once we have how constrained each row and column is, go through and
        // mark  true for all the places that can be immediately filled in
        j=0;
        for (Rule rule: top) {
            int currentPos = 0;
            for (int i : rule.getRuleList()) {
                if (i > topConstraints[j]) {
                    // Fill in currentPos + constraint up to currentPos + i
                    for (int k=currentPos + topConstraints[j]; k < currentPos + i; k++) {
                        grid.toArr()[j][k] = 1;
                    }
                }
                currentPos += i + 1;
            }
            j++;
        }
        j=0;
        for (Rule rule: left) {
            int currentPos = 0;
            for (int i : rule.getRuleList()) {
                if (i > leftConstraints[j]) {
                    // Fill in currentPos + constraint up to currentPos + i
                    for (int k=currentPos + leftConstraints[j]; k < currentPos + i; k++) {
                        grid.toArr()[k][j] = 1;
                    }
                }
                currentPos += i + 1;
            }
            j++;
        }
    }

    public void printGrid(){
        for (int[] row : grid.grid) {
            System.out.println(Arrays.toString(row));
        }
    }

    public void printClues() {
        boolean isFirstRule = true;
        boolean isFirstInRule = true;
        String line = "";
        for (Rule rule : top) {
            if (!isFirstRule) line += ",";
            isFirstRule = false;
            isFirstInRule = true;
            for (int i : rule.getRuleList()) {
                if (!isFirstInRule) line += " ";
                line += i;
                isFirstInRule = false;
            }
        }
        line += "\n";
        isFirstRule = true;
        for (Rule rule : left) {
            if (!isFirstRule) line += ",";
            isFirstRule = false;
            isFirstInRule = true;
            for (int i : rule.getRuleList()) {
                if (!isFirstInRule) line += " ";
                line += i;
                isFirstInRule = false;
            }
        }
        System.out.println(line);
    }

    public long hash() {
        return this.toString().hashCode();
    }

    public static List<Integer> getListFromArray(int[] arr) {
        List<Integer> lst = new ArrayList<Integer>(arr.length);
        for (int j = 0; j < arr.length; j++) {
            lst.add(Integer.valueOf(arr[j]));
        }
        return lst;
    }

    public void setTop(Rule[] top) {
        this.top = top;
    }

    public void setLeft(Rule[] left) {
        this.left = left;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Rule[] getRule(boolean top) {
        return top ? this.top : this.left;
    }

    @Override
    public String toString() {
        String line = "";
        boolean isFirstInRule = true, isFirstRule = true;
        for (Rule rule : this.top) {
            if (!isFirstRule) line += ",";
            isFirstRule = false;
            isFirstInRule = true;
            for (int i : rule.getRuleList()) {
                if (!isFirstInRule) line += " ";
                line += i;
                isFirstInRule = false;
            }
        }
        line += "\n";
        isFirstRule = true;
        for (Rule rule : this.left) {
            if (!isFirstRule) line += ",";
            isFirstRule = false;
            isFirstInRule = true;
            for (int i : rule.getRuleList()) {
                if (!isFirstInRule) line += " ";
                line += i;
                isFirstInRule = false;
            }
        }

        return line;
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
