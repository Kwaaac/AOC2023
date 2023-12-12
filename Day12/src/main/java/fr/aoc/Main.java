package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    private static final HashMap<Part, Long> memoization = new HashMap<>();

    public static void main(String[] args) throws IOException {
        var example = Paths.get("Day12", "src", "main", "resources", "example.txt");
        var input = Paths.get("Day12", "src", "main", "resources", "input.txt");
        puzzle2(input);

    }

    private static long countRec(String line, List<Integer> nbr) {
        var key = new Part(line, nbr);
        var total = memoization.get(key);
        if (total != null) {
            return total;
        }

        // recursion ends
        if (line.isEmpty()) {
            if (nbr.isEmpty()) return 1;// bon cas
            return 0; // mauvais cas
        }

        var c = line.charAt(0);

        var result = switch (c) {
            case '.' -> countRec(stripLeftPoint(line), nbr);
            case '?' -> countRec(STR."#\{line.substring(1)}", nbr)
                    + countRec(STR.".\{line.substring(1)}", nbr);
            case '#' -> {
                // cas ou on arrête:
                // on atteint la fin ou on ne peut plus finir le groupe
                if (nbr.isEmpty() || line.length() < nbr.getFirst()) yield 0;
                var currentGroup = nbr.getFirst();
                // si on ne peut pas créer le groupe car y'a un .
                if (line.substring(0, currentGroup).contains(".")) yield 0;
                // il y a un # en trop à la fin, on ne peut pas finir
                if (line.substring(currentGroup).startsWith("#")) yield 0;

                var newGroups = new ArrayList<>(nbr);
                newGroups.removeFirst();

                // Si le dernier est un ?, on passe au groupe suivant
                if (line.length() > currentGroup && line.charAt(currentGroup) == '?') {
                    yield countRec(stripLeftPoint(line.substring(currentGroup + 1)), newGroups);
                }

                // groupe suivant
                yield countRec(stripLeftPoint(line.substring(currentGroup)), newGroups);
            }
            default -> throw new IllegalStateException(STR."Unexpected value: \{c}");
        };

        memoization.put(key, result);
        if (result < 0) System.out.println(key);
        return result;
    }

    private static String stripLeftPoint(String line) {
        int i = 0;
        for (; i < line.length(); i++) {
            if (line.charAt(i) != '.') {
                break;
            }
        }
        return line.substring(i);
    }

    private static void puzzle2(Path path) throws IOException {
        var lines = Files.readAllLines(path);


        var parts = lines.stream().map(line -> {
            var tokens = line.split("\\s");
            var input = STR."\{tokens[0]}?".repeat(4) + tokens[0];
            var g = Arrays.stream(tokens[1].split(",")).mapToInt(Integer::parseInt).toArray();
            var groups = IntStream.range(0, 5).flatMap(i -> Arrays.stream(g)).boxed().toList();
            return new Part(input, groups);
        }).toList();

        var res = parts.stream().mapToLong(part -> countRec(part.line, part.nbr)).sum();
        System.out.println(res);
        System.out.println(STR."memoization.size() = \{memoization.size()}");
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

    private record Part(String line, List<Integer> nbr) {
        @Override
        public String toString() {
            return STR."\{line} \{nbr}";
        }
    }
}