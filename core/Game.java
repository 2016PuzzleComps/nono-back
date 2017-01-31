import java.util.ArrayList;

public class Game {
    List<Rule> top;
    List<Rule> left;
    int height;
    int width;
    Grid grid;

    public Game(int width, int height) {
        this.top = new ArrayList<Rule>();
        this.left = new ArrayList<Rule>();
        this.height = height;
        this.width = width;
        this.grid = new Grid(width, height);
    }

    public boolean isSolved() {
        for(int i=0;i<height;i++) {
            if(!this.left.get(i).satisfies(this.grid.getRow(i))) {
                return false;
            }
        }
        for(int i=0;i<width;i++) {
            if(!this.top.get(i).satisfies(this.grid.getColumn(i))) {
                return false;
            }
        }
        return true;
    }

    public void clear() {
        this.grid.clear();
    }

}
