package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        var example = Paths.get("Day16", "src", "main", "resources", "example.txt");
        var input = Paths.get("Day16", "src", "main", "resources", "input.txt");

        puzzle2(input);
    }

    private static void puzzle1(Path path) throws IOException {
        try (var lines = Files.lines(path)) {
            var cells = lines.map(
                    line -> line.chars().mapToObj(Cell::fromSpace).toArray(Cell[]::new)
            ).toArray(Cell[][]::new);
            var grid = new Grid(cells);
            grid.energize();
            System.out.println(grid.countEnergized());
        }
    }

    private static void puzzle2(Path path) throws IOException {
        try (var lines = Files.lines(path)) {
            var cells = lines.map(
                    line -> line.chars().mapToObj(Cell::fromSpace).toArray(Cell[]::new)
            ).toArray(Cell[][]::new);
            var grid = new Grid(cells);
            System.out.println(grid.energizeAll());
        }
    }
}