package fr.aoc;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws IOException {
        var example = Paths.get("Day22", "src", "main", "resources", "example.txt");
        var input = Paths.get("Day22", "src", "main", "resources", "input.txt");
        
        puzzle1(example);
    }

    private static void puzzle1(Path input) throws IOException {
        var lines = Files.readAllLines(input);
        lines.stream().map(Brick::fromLine).sorted(Comparator.comparingInt(Brick::lowerEnd)).forEach(System.out::println);
    }
}