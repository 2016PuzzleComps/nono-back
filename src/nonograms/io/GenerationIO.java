package nonograms.io;

import nonograms.Util;
import nonograms.core.Game;
import nonograms.generation.GameGenerator;

import java.nio.file.Path;
import java.util.List;

public class GenerationIO {
    private int boardsToSave = 0;
    private int boardsSaved = 0;
    private int minSize = 1;
    private int maxSize = 1;
    private boolean quiet = false;
    private String prevGraphsDir;

    public boolean readArgs(String[] args) {
        if (args.length < 3) {
            return false;
        }
        for (int i=1; i< args.length; i++) {
            if(args[i].equals("--numBoards")) {
                this.boardsToSave = Integer.parseInt(args[i+1]);
                i++;
            } else if(args[i].equals("--minSize")) {
                this.minSize = Integer.parseInt(args[i+1]);
                i++;
            } else if(args[i].equals("--maxSize")) {
                this.maxSize = Integer.parseInt(args[i+1]);
                i++;
            } else if(args[i].equals("--prevGraphs")) {
                this.prevGraphsDir = args[i+1];
                i++;
            }else if(args[i].equals("--quiet")) {
                this.quiet = true;
            } else {
                System.err.println("unrecognized option '" + args[i] + "'");
                return false;
            }
        }
        return true;
    }

    public void generate() {
        if (!quiet) System.out.println("importing previous puzzles");

        List<Path> paths = Util.getFilePaths(prevGraphsDir);

        String outdir = "generated_puzzles/";
        GameGenerator gen = new GameGenerator();
        for (int i = minSize; i < maxSize; i++) {
            for (int j = 0; j < this.boardsToSave; j++) {
                Game g = gen.generateSize(i);
                GameIO.saveToFile(g.toString(), outdir + "puzzle" + i + "-" + j + ".txt");
            }
        }
    }

    public static void main(String[] args) {
        String outdir = "puzzles/";
        GameGenerator gen = new GameGenerator();
        for (int i=600; i<700; i++) {
            Game g = gen.generateSize(20);
            GameIO.saveToFile(GameIO.gameToFileString(g), outdir+"puzzle-"+i+".txt");
        }
    }
}
