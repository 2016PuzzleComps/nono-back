package io;


import core.Game;
import core.Rule;
import generation.GameGenerator;

public class GameIO {
    public static String saveToFile(Game game) {
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

    public static void main(String[] args) {
        GameGenerator gen = new GameGenerator();
        Game g = gen.generate();
        System.out.print(saveToFile(g));
    }
}
