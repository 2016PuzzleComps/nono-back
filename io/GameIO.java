package io;


import core.Game;
import core.Rule;
import generation.GameGenerator;

import java.io.*;

public class GameIO {
    public static String gameToFileString(Game game) {
        String line = "";
        boolean isFirstInRule = true, isFirstRule = true;
        for (Rule rule : game.getRule(true)) {
            if (!isFirstRule) line += ",";
            isFirstRule = false;
            isFirstInRule = true;
            for (int i : rule.getRuleList()) {
                if (!isFirstInRule) line += " ";
                line += i;
                isFirstInRule = false;
            }
        }
        line += "\n";
        isFirstRule = true;
        for (Rule rule : game.getRule(false)) {
            if (!isFirstRule) line += ",";
            isFirstRule = false;
            isFirstInRule = true;
            for (int i : rule.getRuleList()) {
                if (!isFirstInRule) line += " ";
                line += i;
                isFirstInRule = false;
            }
        }

        return line;
    }

    public static void saveToFile(String toSave, String filename) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename), "utf-8"))) {
            writer.write(toSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GameGenerator gen = new GameGenerator();
        Game g = gen.generate();
        saveToFile(gameToFileString(g), "out.txt");
    }
}
