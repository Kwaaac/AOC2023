package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws IOException {
        var example1 = Paths.get("Day9", "src", "main", "resources", "1", "example.txt");
        var example2 = Paths.get("Day9", "src", "main", "resources", "2", "example.txt");
        var input = Paths.get("Day9", "src", "main", "resources", "input.txt");


        puzzle1(input);
    }

    private static void puzzle1(Path path) throws IOException {
        var lines = Files.readAllLines(path);

        //System.out.println(Arrays.toString(lines.stream().map(Report::fromLine).mapToInt(Report::extrapolate).toArray()));
        System.out.println(lines.stream().map(Report::fromLine).mapToInt(Report::extrapolate).sum());

    }
}