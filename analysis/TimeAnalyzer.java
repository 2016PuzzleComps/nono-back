package analysis;

import core.Game;

public class TimeAnalyzer implements Analyzer {
    @Override
    public double analyze(Log log) {
        return log.moveList.get(log.moveList.size()-1).time - log.moveList.get(0).time;
    }
}
