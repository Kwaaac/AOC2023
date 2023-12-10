package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Main {

    private static record Race(long time, long distance) {
        public long compute() {
            return (int) LongStream.range(0, time).flatMap(i -> {
                if ((time - i) * i > distance) return LongStream.of(i);
                return LongStream.empty();
            }).count();
        }
    }

    private static void puzzle2(Path path) throws IOException {
        var lines = Files.readAllLines(path);

        var time = Long.parseLong(lines.getFirst().replaceAll("Time:|\\s", ""));
        var distance = Long.parseLong(lines.getLast().replaceAll("Distance:|\\s", ""));
        var race = new Race(time, distance);
        System.out.println("races = " + race);
        var res = race.compute();
        System.out.println("res = " + res);
    }

    private static void puzzle1(Path path) throws IOException {
        var lines = Files.readAllLines(path);

        var times = lines.getFirst().split("\\s+");
        var distances = lines.getLast().split("\\s+");
        var races = new ArrayList<Race>();
        for (int i = 1; i < times.length; i++) {
            races.add(new Race(Long.parseLong(times[i]), Long.parseLong(distances[i])));
        }

        var res = races.stream().mapToLong(Race::compute).reduce(Math::multiplyExact);
        System.out.println("res = " + res);
    }

    public static void main(String[] args) throws IOException {
        var example1 = Paths.get("Day6", "src", "main", "resources", "1", "example.txt");
        var example2 = Paths.get("Day6", "src", "main", "resources", "2", "example.txt");
        var input = Paths.get("Day6", "src", "main", "resources", "input.txt");

        puzzle2(input);
    }
}