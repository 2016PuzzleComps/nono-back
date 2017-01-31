import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;


public class Rule {
    public List<Integer> ruleList;

    public Rule() {
        this.ruleList = new ArrayList<Integer>();
    }

    public Rule(List<Integer> ruleList) {
        this.ruleList = ruleList;
    }

    public boolean partiallySatisfies(int[] row, int distance) {
        Iterator<Integer> iter = ruleList.iterator();
        int squaresToRead = distance;
        boolean reading = true;
        int counter = 0;
        for (int i : row) {
            if (squaresToRead == 0) {
                return true;
            }
            squaresToRead--;
            if(reading && i==1) {
                if (!iter.hasNext()) {
                    return false;
                }
                reading = false;
                counter = iter.next()-1;
                continue;
            }
            else if (reading) {
                continue;
            }
            else if(counter>0){
                if (i != 1) {
                    return false;
                }
                else {
                    counter--;
                    continue;
                }
            }
            else if(counter<=0) {
                if (i==1) {
                    return false;
                }
                else {
                    reading = true;
                    continue;
                }
            }
        }
        if (iter.hasNext() || counter>0) {
            return false;
        }
        return true;
    }

    public boolean satisfies(int[] row) {
        return partiallySatisfies(row, row.length);
    }


    /*
     * This function gets all the rows of the given length that fulfill
     * this rule's requirements. 
     */
    public List<int[]> getAllSatisfyingRows(int length) {
        List<int[]> result = new ArrayList<int[]>();
        int availableSquares = length - (sum(ruleList) + ruleList.size() - 1);
        int availableSlots = ruleList.size() + 1;

        List<int[]>tuples = new ArrayList<int[]>();
        int[] lst = new int[availableSlots];
        lst[0] = availableSquares;
        tuples.add(lst.clone());
        while(lst[lst.length-1] != availableSquares) {
            increment(lst);
            tuples.add(lst.clone());
        }

        for(int[] arr : tuples) {
            int[] rowToAdd = new int[length];
            int index = 0;
            int blockNumber = 0;
            while (blockNumber < ruleList.size()) {
                index += arr[blockNumber];
                int counter = ruleList.get(blockNumber);
                while (counter > 0) {
                    rowToAdd[index] = 1;
                    index++;
                    counter--;
                }
                blockNumber++;
                index++;
            }
            result.add(rowToAdd);
            System.out.println(Arrays.toString(arr));
            System.out.println(Arrays.toString(rowToAdd));
        }

        return result;

    }


    /*If array is read as a number by digit, where the first index represents
     * the 1's place, this function modifies array so it represents the next
     * largest integer while preserving the digit sum.
     * This is in an attempt to generate all sequences of nonnegative
     * integers with a fixed sum.
     *
     */
    public static void increment(int[] array) {
        int firstNonZero = 0;
        while(array[firstNonZero] == 0) {
            firstNonZero++;
        }
        array[0] = array[firstNonZero] - 1;
        array[firstNonZero + 1]++;
        if (firstNonZero != 0) {
            array[firstNonZero] = 0;
        }
    }

    public static int sum(List<Integer> lst) {
        int total = 0;
        for(int x : lst) {
            total+=x;
        }
        return total;
    }


    public static void main(String[] args) {
        List<Integer> rl = new ArrayList<Integer>();
        rl.add(2);
        rl.add(5);
        Rule r = new Rule(rl);
        r.getAllSatisfyingRows(11);
    }

}
