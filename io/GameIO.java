package io;


import core.Game;
import core.Rule;
import generation.GameGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        try (FileOutputStream out = new FileOutputStream(filename)) {
            out.write(toSave.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename), "utf-8"))) {
            writer.write(toSave);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static Game loadGameFromFile(String filename) {
        Game g = null;
        List<Rule> top = new ArrayList<>();
        List<Rule> left = new ArrayList<>();
        try {
            String line;
            FileReader fileReader = new FileReader(filename);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            boolean isTop = true;
            while((line = bufferedReader.readLine()) != null) {
                // Deal with bad inputs
                System.out.println(line);
                line = line.replaceAll(",,",",0,");
                line = line.replaceAll(",,",",0,");
                if (line.charAt(line.length()-1) == ',')
                    line = line + "0";
                System.out.println(line);

                List<Rule> listToAdd = isTop ? top : left;
                for (String rule : line.split(",")) {
                    int[] ruleList = Arrays.stream(rule.split(" "))
                            .map(String::trim).mapToInt(Integer::parseInt).toArray();
                    listToAdd.add(new Rule(ruleList));
                }
                isTop = false;
            }

            bufferedReader.close();

            // Once we've read the file to see what size the board is,
            // create the game object
            g = new Game(top.size(), left.size());

            g.setTop(top.toArray(new Rule[top.size()]));
            g.setLeft(left.toArray(new Rule[left.size()]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return g;
    }

    public static void main(String[] args) {
        GameGenerator gen = new GameGenerator();
        Game g = gen.generate();
        //saveToFile(gameToFileString(g), "out.txt");
        //g = loadGameFromFile("game2.txt");
        g.printClues();
        System.out.println("----------------");
        g.solve();
        System.out.println("----------------");
        g.printGrid();
    }
}
