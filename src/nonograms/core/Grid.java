package nonograms.core;

public class Grid {
    int[][] grid;

    public Grid(int width,int height) {
        this.grid = new int[height][width];
    }

    public int get(int x, int y) {
        return this.grid[x][y];
    }

    public void set(int val, int x, int y) {
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

    public void setRow(int[] row, int y) {
        this.grid[y] = row;
    }

    public int[] getColumn(int x) {
        int[] column = new int[this.grid[0].length];
        for(int i = 0; i< this.grid[0].length; i++) {
            column[i] = this.grid[i][x];
        }
        return column;
    }

    public void clear() {
        for(int i = 0; i < this.grid.length; i++) {
            for(int j = 0; j <this.grid[0].length;j++) {
                set(0,j,i);
            }
        }
    }

    public int getNumFilled() {
        int filled = 0;
        for (int[] row : grid) {
            for (int i : row) {
                if (i==1) filled++;
            }
        }
        return filled;
    }

    public int getHeight() {
        return grid.length;
    }

    public int getWidth() {
        if (grid.length == 0)
            return 0;
        return grid[0].length;
    }

    public int[][] toArr() {
        return this.grid;
    }

    //Fraction of neighbors that are filled in. Used in src.nonograms.generation.
    public double fractionOfNeighborsLiving(int x, int y) {
        int numNeighbors = 0;
        int numLivingNeighbors = 0;
        for (int i = x-1; i <x+2; i++) {
            for (int j = y-1; j < y+2; j++) {
                if (i >= 0 && i< getWidth() && j>=0 && j<getHeight()) {
                    numNeighbors++;
                    if (this.get(i,j)==1) {
                        numLivingNeighbors++;
                    }
                }
            }
        }
        return ((double) numLivingNeighbors)/numNeighbors;
    }

}
