package nonograms.evaluation;

import nonograms.core.*;

public interface Evaluator {

    /*
     * Not scaled
     */
    public double eval(Game g);

    public String description();
}
