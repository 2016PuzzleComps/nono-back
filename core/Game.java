import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
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


    public void solve() {
        clear();
        List<int[]>[] rowPossibilities = new List<int[]>[this.height];
        List<int[]>[] columnPossibilities = new List<int[]>[this.width];
        for(int i = 0;i<height;i++) {
            rowPossibilities[i] = left[i].getAllSatisfyingRows(width);
        }
        for(int i = 0;i<width;i++) {
            columnPossibilities[i] = top[i].getAllSatisfyingRows(height);
        }
        Set<List<Integer>>[] columnPossibilitiesSet = new HashSet<List<Integer>>[this.width];
        for(int i = 0;i<width;i++) {
            Set<List<Integer>> thisColumnSet = new HashSet<List<Integer>>;
            for (int[] col : columnPossibilities[i]) {
                thisColumnSet.add(Arrays.asList(col));
            }
            columnPossibilitiesSet[i] = thisColumnSet;
        }

        return solveHelper(columnPossibilitiesSet,rowPossibilities,0);
        }
    }

    public boolean solveHelper(Set<List<Integer>>[] columnPossibilitiesSet, List<int[]>[] rowPossibilities, int depth) {
        if (depth == height) {
            for (int i=0;i<width;i++) {
                if (!columnPossibilitiesSet.contains(this.grid.getColumn(i))) {
                    return false;
                }
            }
            return true;
        }
        for (int[] row : rowPossibilities[i]) {
            this.grid.setRow(row, depth)
            if (solveHelper(columnPossibilitiesSet,rowPossibilities,depth + 1)) {
                return true;
            }
        }
        this.grid.setRow(new int[width], depth);
        return false;
    }
}
