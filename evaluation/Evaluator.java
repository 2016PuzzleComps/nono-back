package evaluation;

import core.*;

public interface Evaluator {

    /*
     * Not scaled
     */
    public double eval(Game g);

    public String description();
}
