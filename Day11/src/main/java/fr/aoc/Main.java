package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Main {
    public static void main(String[] args) throws IOException {
        var input = Paths.get("Day11", "src", "main", "resources", "input.txt");

        puzzle1(input);
        puzzle2(input);
    }

    private static List<String> transposeMatrix(List<String> matrix) {
        return IntStream.range(0, matrix.getFirst().length())
                .mapToObj(i -> matrix.stream().map(row -> row.charAt(i)).collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString
                )))
                .toList();
    }

    private static void puzzle1(Path path) throws IOException {
        puzzle(Files.readAllLines(path), 2);
    }

    private static void puzzle2(Path path) throws IOException {
        puzzle(Files.readAllLines(path), 1000000);
    }

    private static void puzzle(List<String> lines, int expansion) {

        var transposedLines = transposeMatrix(lines);

        Function<List<String>, List<Integer>> getIdxEmpty = lst -> IntStream.range(0, lst.size()).flatMap(i -> {
            if (lst.get(i).chars().allMatch(c -> c == '.')) return IntStream.of(i);
            return IntStream.empty();
        }).boxed().toList();

        var emptyRows = getIdxEmpty.apply(lines);
        var emptyColumn = getIdxEmpty.apply(transposedLines);

        record Point(int x, int y) {
        }
        var galaxies = IntStream
                .range(0, lines.size())
                .mapToObj(i -> IntStream.range(0, lines.getFirst().length()).filter(j -> lines.get(i).charAt(j) == '#').mapToObj(j -> new Point(i, j)))
                .flatMap(Function.identity())
                .collect(Collectors.toCollection(ArrayList::new));


        var res = LongStream.range(0, galaxies.size()).flatMap(_ -> {
            var p = galaxies.removeFirst();
            return galaxies.stream().mapToLong(p2 -> {
                var emptyRowsBetween = IntStream.range(Math.min(p.x, p2.x + 1), Math.max(p.x, p2.x)).filter(emptyRows::contains).count();
                var emptyColsBetween = IntStream.range(Math.min(p.y, p2.y + 1), Math.max(p.y, p2.y)).filter(emptyColumn::contains).count();

                return (Math.abs(p.x - p2.x) + Math.abs(p.y - p2.y) + (expansion - 1) * emptyRowsBetween + (expansion - 1) * emptyColsBetween);
            });
        }).sum();

        System.out.println(STR."res = \{res}");
    }

}