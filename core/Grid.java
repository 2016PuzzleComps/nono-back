public class Grid {
    int[][] grid;

    public Grid(int width,int height) {
        this.grid = new int[height][width];
    }

    public int get(int x, int y) {
        return this.grid[x][y];
    }

    public void set(int val,int x, int y) {
        this.grid[x][y] = val;
    }

    public void white(int x, int y) {
        this.grid[x][y] = 0;
    }

    public void black(int x, int y) {
        this.grid[x][y] = 1;
    }

    public void cross(int x, int y) {
        this.grid[x][y] = -1;
    }

    public int[] getRow(int y) {
        return this.grid[y];
    }

    public int[] getColumn(int x) {
        int[] column = new int[this.grid[0].length];
        for(int i = 0; i< this.grid[0].length; i++) {
            column[i] = this.grid[i][x];
        }
        return column;
    }

}
