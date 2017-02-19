package nonograms;

import nonograms.core.*;
import nonograms.evaluation.*;
import nonograms.io.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String usage =
            "Usage: java nonograms.Main [OPERATION] [ARGUMENTS]\n" +
                    "Supported operations:\n" +
                    "\tgenerate <puzzle_size> <number>" +
                    "\tevaluate [ --csv | --fields ] <puzzle_file>\n"+
                    "\tcheck-unique <old_paths> <new_paths>";

    public static void main(String[] args) {
        if(args.length > 1) {
            String operation = args[0];
            String puzzleFile = null;
             if(operation.equals("evaluate")) {
                String option = null;
                boolean asCsv = false, fields = false;
                if(args.length < 2 || args.length > 3) {
                    usage();
                } else {
                    if(args.length == 2) {
                        option = args[1];
                        if(option.equals("--fields")) {
                            fields = true;
                        } else {
                            puzzleFile = args[1];
                        }
                    } else if(args.length == 3) {
                        option = args[1];
                        if(option.equals("--csv")) {
                            asCsv = true;
                        } else {
                            usage();
                        }
                        puzzleFile = args[2];
                    }
                }
                List<Evaluator> evaluators = new ArrayList<>();
                evaluators.add(new WeightedRatioFilledEvaluator());
                if(fields) {
                    for(Evaluator e : evaluators) {
                        System.out.print(e.description() + ",");
                    }
                } else {
                    Game g = GameIO.loadGameFromFile(puzzleFile);
                    if(asCsv) {
                        for(Evaluator e : evaluators) {
                            System.out.print(e.eval(g) + ",");
                        }
                    } else {
                        for(Evaluator e : evaluators) {
                            System.out.println(e.description() + ": " + e.eval(g));
                        }
                    }
                }
            } else if(operation.equals("check-unique")) {
                if(args.length == 3) {
                    List<Path> oldPaths = Util.getFilePaths(args[1]);
                    List<Path> newPaths = Util.getFilePaths(args[2]);
                    Map<Long,Path> oldHashes = new HashMap<>();
                    for(Path path : oldPaths) {
                        oldHashes.put(GameIO.loadGameFromFile(path.toAbsolutePath().toString()).hash(), path);
                    }
                    boolean allUnique = true;
                    for(Path path : newPaths) {
                        Long newHash = GameIO.loadGameFromFile(path.toAbsolutePath().toString()).hash();
                        if(oldHashes.containsKey(newHash)) {
                            allUnique = false;
                            System.out.println(path.toString() + " is in the same equivalence class as " + oldHashes.get(newHash).toString());
                        }
                    }
                    if(allUnique) {
                        System.out.println("all unique");
                    }
                } else {
                    usage();
                }
            } else {
                usage();
            }
        } else {
            usage();
        }
    }

    private static void usage() {
        System.err.println(usage);
        System.exit(1);
    }
}
