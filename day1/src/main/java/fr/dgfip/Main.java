package fr.dgfip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.MarshalledObject;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    private static enum Numbers {
        __, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;
    }

    /**
     * Edges cases de c mor ( beurk )
     */
    private static String replaceAll(String line) {
        return line
                .replaceAll("eightwo", "eighttwo").replaceAll("twone", "twoone").replaceAll("eighthree", "eightthree").replaceAll("oneight", "oneeight").replaceAll("threeight", "threeeight").replaceAll("fiveight", "fiveeight").replaceAll("nineight", "nineeight");
    }

    private static int checkReplaceOld(String line) {
        var pattern = "\\d|one|two|three|four|five|six|seven|eight|nine";
        var pat = Pattern.compile(pattern);
        var results = pat.matcher(line).results().map(m -> convert(m.group())).toList();

        String s = convert(results.getFirst()) + convert(results.getLast());
        return Integer.parseInt(s);
    }

    private static int checkReplace(String line) {
        var pattern = "\\d|one|two|three|four|five|six|seven|eight|nine";
        var pat = Pattern.compile("(" + pattern + ")+?.*(" + pattern + ")|" + pattern);
        var first = pat.matcher(line).results().toList().getFirst();
        System.out.println("line = " + line);
        for (int i = 0; i < first.groupCount(); i++) {
            System.out.println("first.group(" + i + ") = " + first.group(i));
        }

        // Equals the edge case with only one number
        if (first.group().matches("^" + pattern + "?$")) {
            return Integer.parseInt(convert(first.group()));
        }
        System.out.println("first.group(2) = " + first.group(2));
        String s = convert(first.group(1)) + convert(first.group(2));
        System.out.println("s = " + s);
        return Integer.parseInt(s);
    }

    private static String convert(String number) {
        // case when only 1 number
        if (number == null) return "";
        if (number.matches("\\d")) {
            return number;
        }

        return Numbers.valueOf(number.toUpperCase()).ordinal() + "";
    }

    private static void exo2(Path path) throws IOException {
        try (var lines = Files.lines(path)) {
            System.out.println(lines
                    //.map(Main::replaceAll)
                    .mapToInt(Main::checkReplace)
                    .peek(System.out::println)
                    .sum());
        }
    }

    private static void exo1(Path path) throws IOException {
        try (var lines = Files.lines(path)) {
            var res = lines.map(line -> line.replaceAll("\\D", "")).mapToInt(line -> Integer.parseInt("" + line.charAt(0) + line.charAt(line.length() - 1))).sum();
            System.out.println(res);
        }
    }

    public static void main(String[] args) throws IOException {
        var example2 = Paths.get("day1", "src", "main", "resources", "2", "example.txt");
        var input = Paths.get("day1", "src", "main", "resources", "input.txt");
        exo2(example2);
    }
}