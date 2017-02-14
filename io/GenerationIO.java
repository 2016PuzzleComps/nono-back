package io;

import core.Game;
import generation.GameGenerator;

public class GenerationIO {

    public static void main(String[] args) {
        String outdir = "puzzles/";
        GameGenerator gen = new GameGenerator();
        for (int i=600; i<700; i++) {
            Game g = gen.generateSize(20);
            GameIO.saveToFile(GameIO.gameToFileString(g), outdir+"puzzle-"+i+".txt");
        }
    }
}
