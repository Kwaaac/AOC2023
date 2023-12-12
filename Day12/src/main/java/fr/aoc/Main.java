package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        var example = Paths.get("Day12", "src", "main", "resources", "example.txt");
        var input = Paths.get("Day12", "src", "main", "resources", "input.txt");
        var start = System.currentTimeMillis();
        puzzle1(input);
        System.out.println(System.currentTimeMillis() - start);
    }

    private static void puzzle1(Path path) throws IOException {
        try (var lines = Files.lines(path)) {
            var res = lines.parallel().mapToLong(line -> {
                var tokens = line.split("\\s");
                //ex: ???.###. (add a . for easier checking)
                var input = STR."\{tokens[0]}.";
                //ex: [1,1,3]
                var groups = Arrays.stream(tokens[1].split(",")).mapToInt(Integer::parseInt).toArray();

                //ex: [1,1,3] --groups to regex--> #{1} #{1} #{3} --adding the . between--> \.*#{1}\.+#{1}\.+#{3}\.*
                var pattern = Arrays.stream(groups)
                        .mapToObj(g -> STR."#{\{g}}")
                        .collect(Collector.of(
                                () -> new StringJoiner("\\.+", "\\.*", "\\.*"),
                                StringJoiner::add,
                                StringJoiner::merge,
                                StringJoiner::toString
                        ));
                /* equivalent with a loop, but the stream is funnier to do - What do you mean readability ?
                var sb = new StringJoiner("\\.+", "\\.*", "\\.*");
                for (var g : groups) {
                    sb.add(STR."#{\{g}}");
                }
                var pattern = sb.toString();
                 */

                var combinaisons = new ArrayList<String>(List.of(input));
                var nbr = (int) input.chars().filter(i -> i == '?').count();

                IntStream.range(0, nbr).forEach(_ -> {
                    var tmp = combinaisons.stream().parallel().flatMap(c -> {
                        var idx = c.indexOf('?');
                        if (idx == -1) return Stream.empty();
                        return Stream.of(
                                STR."\{c.substring(0, idx)}#\{c.substring(idx + 1)}",
                                STR."\{c.substring(0, idx)}.\{c.substring(idx + 1)}"
                        );
                    }).toList();
                    combinaisons.clear();
                    combinaisons.addAll(tmp);
                });

                return combinaisons.stream().filter(s -> s.matches(pattern)).count();
            }).sum();

            System.out.println(STR."res = \{res}");
        }
    }
}