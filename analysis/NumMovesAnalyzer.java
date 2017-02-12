package analysis;

public class NumMovesAnalyzer implements Analyzer {

    @Override
    public double analyze(Log log) {
        return log.moveList.size();
    }
}
