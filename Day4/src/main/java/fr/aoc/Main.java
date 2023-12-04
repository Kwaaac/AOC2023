package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static void puzzle1(Path path) throws IOException {
        try (var lines = Files.lines(path)) {
            System.out.println(lines.map(Card::fromLine).mapToInt(Card::computeWinningNumbers).sum());
        }
    }

    private static void puzzle2(Path path) throws IOException {
        try (var lines = Files.lines(path)) {
            List<Card> list = lines.map(Card::fromLine).toList();
            Card.setCachedCards(list);
            var cards = Card.stream()
                            .peek(__ -> System.out.println("split"))
                    .flatMap(Card::computeWinningScratchcards)
                    .peek(System.out::println)
                    .collect(Collectors.groupingBy(Card::id));

            cards.forEach((key, value) -> System.out.println(key + " : " + value.size()));
        }
    }


    public static void main(String[] args) throws IOException {
        var example1 = Paths.get("Day4", "src", "main", "resources", "1", "example.txt");
        var example2 = Paths.get("Day4", "src", "main", "resources", "2", "example.txt");
        var input = Paths.get("Day4", "src", "main", "resources", "input.txt");

        puzzle2(example1);
    }
}