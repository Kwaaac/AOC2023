package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        var example1 = Paths.get("Day7", "src", "main", "resources", "1", "example.txt");
        var example2 = Paths.get("Day7", "src", "main", "resources", "2", "example.txt");
        var input = Paths.get("Day7", "src", "main", "resources", "input.txt");

        puzzle(input);
    }

    private static void puzzle(Path path) throws IOException {
        try (var lines = Files.lines(path)) {
            var hands = lines.map(Hand::fromLine).sorted().toList();

            // illisible mais rigolo
            for (int i = 1, res = 0; i <= hands.size(); i++, res += hands.get(i - 1).compute(i)) {
                if (i == hands.size() - 1) System.out.println("res = " + res);
            }
        }
    }
}