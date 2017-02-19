package nonograms.analysis;

import nonograms.core.Game;

import java.util.ArrayList;
import java.util.List;

public class Log {
    public String status;
    public Game g;
    public List<LogMove> moveList;
    public Log() {
        this.moveList = new ArrayList<>();
    }

}
