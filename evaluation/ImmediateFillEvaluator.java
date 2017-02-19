package evaluation;

import core.*;

import java.util.Arrays;

public class ImmediateFillEvaluator implements Evaluator {

    @Override
    public double eval(Game g) {
        int numFilled = 0;
        boolean[][] protoGrid = new boolean[g.getGrid().getHeight()][g.getGrid().getWidth()];

        // Find how constrained everything is
        int currentSize;
        int[] topConstraints = new int[g.getGrid().getWidth()];
        int[] leftConstraints = new int[g.getGrid().getHeight()];
        int j=0;
        for (Rule rule : g.getRule(true)) {
            currentSize = g.getGrid().getWidth();
            for (int i : rule.getRuleList()) {
                currentSize -= i + 1;
            }
            // The last block of filled in squares can be at the end
            currentSize += 1;
            topConstraints[j] = currentSize;
            j++;
        }
        j=0;
        for (Rule rule : g.getRule(false)) {
            currentSize = g.getGrid().getHeight();
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
        for (Rule rule: g.getRule(true)) {
            int currentPos = 0;
            for (int i : rule.getRuleList()) {
                if (i > topConstraints[j]) {
                    // Fill in currentPos + constraint up to currentPos + i
                    for (int k=currentPos + topConstraints[j]; k < currentPos + i; k++) {
                        protoGrid[k][j] = true;
                    }
                }
                currentPos += i + 1;
            }
            j++;
        }
        j=0;
        for (Rule rule: g.getRule(false)) {
            int currentPos = 0;
            for (int i : rule.getRuleList()) {
                if (i > leftConstraints[j]) {
                    // Fill in currentPos + constraint up to currentPos + i
                    for (int k=currentPos + leftConstraints[j]; k < currentPos + i; k++) {
                        protoGrid[j][k] = true;
                    }
                }
                currentPos += i + 1;
            }
            j++;
        }

        // Count up all the places that have been filled for sure
        for (boolean[] row : protoGrid) {
            for (boolean val : row) {
                numFilled += val ? 1 : 0;
            }
        }
        double total = 0.0;
        for (int[] row : g.getGrid().toArr()) {
            for (int i : row) {
                total+=i;
            }
        }

        return numFilled/total;
    }

    public static void printArr(boolean[][] arr) {
        for (int i=0; i< arr.length; i++) {
            for (int j=0; j<arr[i].length; j++) {
                System.out.print(arr[i][j]+" " );
            }
            System.out.println();
        }
    }

    @Override
    public String description() {
        return "";
    }
}
