package nonograms.io;

import nonograms.analysis.Log;
import nonograms.analysis.LogMove;
import nonograms.analysis.TimeAnalyzer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class LogIO {
    /**
     * Converter method
     * @param filePath the file path of the log
     * @return the array representation of the log file
     */
    public static Log read(String filePath) {
        Log log = new Log();
        Path path = Paths.get(filePath);
        try (Scanner scanner = new Scanner(path, StandardCharsets.UTF_8.name())) {
            while (scanner.hasNextLine()){
                String[] line = scanner.nextLine().split(" ");
                LogMove logMove = new LogMove();
                logMove.time = Long.parseLong(line[0]);
                logMove.x = Integer.parseInt(line[1]);
                logMove.y = Integer.parseInt(line[2]);
                logMove.type = Integer.parseInt(line[3]);
                log.moveList.add(logMove);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't find file");
        }
        return log;
    }

    public static void main(String[] args) {
        Log log = read("log.txt");
        TimeAnalyzer timeAnalyzer = new TimeAnalyzer();
        System.out.println(timeAnalyzer.analyze(log));
    }
}