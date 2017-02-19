package nonograms;

import java.util.List;
import java.util.LinkedList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.io.IOException;

public class Util {

    public static List<Path> getFilePaths(String filename) {
        Path path = Paths.get(filename);
        if(path.toFile().isDirectory()) {
            return getFilePathsHelper(new LinkedList<Path>(), path);
        } else {
            List<Path> ret = new LinkedList<>();
            ret.add(path);
            return ret;
        }
    }

    public static List<Path> getFilePathsHelper(List<Path> ret, Path dir) {
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for(Path path : stream) {
                if(path.toFile().isDirectory()) {
                    getFilePathsHelper(ret, path);
                } else {
                    ret.add(path);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
