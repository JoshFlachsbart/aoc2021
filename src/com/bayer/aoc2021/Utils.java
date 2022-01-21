package com.bayer.aoc2021;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class Utils {
    public Path getLocalPath(String day) throws URISyntaxException, IOException {
        String fileName = "com/bayer/aoc2021/" + day + "/data.txt";
        URL file = getClass().getClassLoader().getResource(fileName);
        if (file == null) throw new IOException("Unable to locate file: " + fileName);
        return Path.of(file.toURI()).toAbsolutePath();
    }
}
