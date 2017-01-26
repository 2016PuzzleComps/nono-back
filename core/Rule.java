import java.util.ArrayList;
import java.util.Iterator;


public class Rule {
    public ArrayList<Integer> ruleList;

    public Rule() {
        this.ruleList = new ArrayList<Integer>();
    }

    public boolean satisfies(int[] row) {
        Iterator<Integer> iter = ruleList.iterator();
        boolean reading = true;
        int counter = 0;
        for (int i : row) {
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
}
