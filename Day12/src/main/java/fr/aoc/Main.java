package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        var example = Paths.get("Day12", "src", "main", "resources", "example.txt");
        var input = Paths.get("Day12", "src", "main", "resources", "input.txt");

        puzzle1(input);
    }


    private static void puzzle1(Path path) throws IOException {

        try (var lines = Files.lines(path)) {
            var res = lines.mapToLong(line -> {
                var tokens = line.split("\\s");
                //???.###
                var input = STR."\{tokens[0]}.";
                //[1,1,3]
                var groups = Arrays.stream(tokens[1].split(",")).mapToInt(Integer::parseInt).toArray();


                var sb = new StringJoiner("\\.+", "\\.*", "\\.*");
                for (var g : groups) {
                    sb.add(STR."#{\{g}}");
                }
                
                // [1,1,3] --> \.*#{1}\.+#{1}\.+#{3}\.*
                var pattern = sb.toString();

                var combinaisons = new ArrayList<String>(List.of(input));
                var nbr = (int) input.chars().filter(i -> i == '?').count();

                IntStream.range(0, nbr).forEach(_ -> {
                    var tmp = combinaisons.stream().flatMap(c -> {
                        var idx = c.indexOf('?');
                        if (idx == -1) return Stream.empty();
                        var working = Stream.of(STR."\{c.substring(0, idx)}#\{c.substring(idx + 1)}");
                        var broken = Stream.of(STR."\{c.substring(0, idx)}.\{c.substring(idx + 1)}");
                        return Stream.concat(working, broken);
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