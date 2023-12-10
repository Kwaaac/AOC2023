package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static void puzzle1(Path path) throws IOException {
        var lines = Files.readAllLines(path);
        var grid = Grid.fromLines(lines);
        int i = 0;
        var numbers = new ArrayList<Piece>();

        for (var line : lines) {
            var pouet = Number.pattern.matcher(line);
            while (pouet.find()) {
                var number = new Number(Integer.parseInt(pouet.group()), i, IntStream.rangeClosed(pouet.start(), pouet.end() - 1).toArray());
                numbers.add(number);
            }

            i++;
        }

        var res = numbers.stream().filter(n -> n.compute(grid)).mapToInt(Piece::value).sum();
        System.out.println(res);
    }

    private static void puzzle2(Path path) throws IOException {
        var lines = Files.readAllLines(path);
        var grid = Grid.fromLines(lines);
        int i = 0;
        var numbers = new ArrayList<Piece>();

        var gears = new ArrayList<Piece>();
        for (var line : lines) {
            var pouet = Number.pattern.matcher(line);
            var gearPouet = Gear.pattern.matcher(line);
            while (pouet.find()) {
                var number = new Number(Integer.parseInt(pouet.group()), i, IntStream.rangeClosed(pouet.start(), pouet.end() - 1).toArray());
                numbers.add(number);
            }
            while (gearPouet.find()) {
                gears.add(new Gear(i, new int[]{gearPouet.start()}));
            }

            i++;
        }

        var numbs = numbers.stream().filter(n -> n.compute(grid)).toList();

        var res = gears.stream().mapToInt(gear -> {
            var set = numbs.stream().filter(gear::compute).collect(Collectors.toSet());
            if (set.size() == 2) {
                return set.stream().map(Piece::value).reduce(Math::multiplyExact).orElse(0);
            }
            return 0;
        }).sum();

        System.out.println(res);
    }

    public static void main(String[] args) throws IOException {
        var example1 = Paths.get("Day3", "src", "main", "resources", "1", "example.txt");
        var example2 = Paths.get("Day3", "src", "main", "resources", "2", "example.txt");
        var input = Paths.get("Day3", "src", "main", "resources", "input.txt");

        puzzle2(input);
    }
}