package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    private static void exo1(Path path) throws IOException {
        try (var lines = Files.lines(path)) {
            var res = lines.mapToInt(line -> {
                        var game = Game.fromLine(line);
                        var tokens = line.split(": ")[1].split("; ");

                        // we have a result when at least one is not valid
                        var result = Arrays.stream(tokens)
                                .map(Cube::fromLine)
                                .map(game::isValid)
                                .filter(aBoolean -> !aBoolean)
                                .findAny();

                        if (result.isEmpty()) return game.id();
                        return 0;
                    })
                    //.peek(i -> System.out.println("game: " + i))
                    .sum();

            System.out.println(res);
        }
    }

    private static void exo2(Path path) throws IOException {
        try (var lines = Files.lines(path)) {
            var res = lines.mapToInt(line -> {
                        var game = Game.fromLine(line);
                        var tokens = line.split(": ")[1].split("; ");

                        // we have a result when at least one is not valid
                        var result = Arrays.stream(tokens).map(Cube::fromLine).toList();
                        return game.power(result);

                    })
                    .sum();

            System.out.println(res);
        }
    }


    public static void main(String[] args) throws IOException {
        var example1 = Paths.get("Day2", "src", "main", "resources", "1", "example.txt");
        var example2 = Paths.get("Day2", "src", "main", "resources", "2", "example.txt");
        var input = Paths.get("Day2", "src", "main", "resources", "input.txt");
        exo2(input);
    }
}