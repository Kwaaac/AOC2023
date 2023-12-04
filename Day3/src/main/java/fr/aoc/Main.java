package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Main {

    private static void puzzle1(Path path) throws IOException {
        var lines = Files.readAllLines(path);
        var pattern = Pattern.compile("\\d+");
        int i = 0;
        for (var line : lines) {
            var bob = pattern.matcher(line);
            while (bob.find()) {
                System.out.println("bob.group() = " + bob.group());
                IntStream.range(bob.start(), bob.end()).forEach(System.out::println);
            }
        }

    }

    public static void main(String[] args) throws IOException {
        var example1 = Paths.get("Day3", "src", "main", "resources", "1", "example.txt");
        var example2 = Paths.get("Day3", "src", "main", "resources", "2", "example.txt");
        var input = Paths.get("Day3", "src", "main", "resources", "input.txt");

        puzzle1(example1);
    }
}