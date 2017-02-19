package nonograms.generation;

import java.util.Random;
import java.util.Arrays;

import nonograms.core.Grid;

public class GridGenerator {

    Random rng;

    public GridGenerator() {
        rng = new Random();
    }

    public Grid generate(int width, int height, double seed, double threshold) {
        Grid g = new Grid(width,height);
        for(int i = 0;i < width; i++) {
            for(int j = 0;j< height;j++) {
                if (rng.nextDouble() < seed) {
                    g.set(1, i, j);
                }
            }
        }

        for(int i = 0;i < width; i++) {
            for(int j = 0;j< height;j++) {
                if (g.fractionOfNeighborsLiving(i,j) > threshold) {
                    g.set(3, i, j);
                }
            }
        }

        for(int i = 0;i < width; i++) {
            for(int j = 0;j< height;j++) {
                if (g.get(i,j)==3) {
                    g.set(1, i, j);
                }
            }
        }
        return g;
    }

    public static void main(String[] args) {
        GridGenerator gn = new GridGenerator();
        Grid g =  gn.generate(10,10,0.3,0.3);

        for (int[] row : g.toArr()) {
            System.out.println(Arrays.toString(row));
        }
    }


}
