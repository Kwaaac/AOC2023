package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.LongStream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static void puzzl1(Path path) throws IOException {
        var lines = Files.readAllLines(path);

        LongStream seeds = Arrays.stream(lines.removeFirst().split(": ")[1].split(" ")).mapToLong(Long::parseLong);
        lines.removeFirst();
        var pipelines = new ArrayList<Pipeline>();
        var mappers = new ArrayList<Mapper>();
        Pipeline pipeline;
        String name = "";
        for (var line : lines) {
            switch (line) {
                case String s when s.matches("([a-zA-z: ]|-)+") -> {
                    mappers.clear();
                    name = s;
                }
                case String s when s.isEmpty() -> pipelines.add(new Pipeline(name, mappers));
                case String s -> mappers.add(Mapper.fromLine(s));
            }
        }
        pipelines.add(new Pipeline(name, mappers));

        System.out.println(pipelines);
        for (var pipe : pipelines) {
            seeds = pipe.map(seeds);
        }

        System.out.println(seeds.min().orElseThrow());
    }

    private static LongStream flatArrayToIntStream(String[] seeds) {
        LongStream res = LongStream.empty();
        for (int i = 0; i < seeds.length; i += 2) {
            long start = Long.parseLong(seeds[i]);
            long end = start + Long.parseLong(seeds[i + 1]);
            res = LongStream.concat(res, LongStream.range(start, end));
        }

        return res;
    }

    private static void puzzl2(Path path) throws IOException {
        var lines = Files.readAllLines(path);

        var arr = Arrays.stream(lines.removeFirst().split(": ")[1].split(" ")).toArray(String[]::new);
        LongStream seeds = flatArrayToIntStream(arr);

        lines.removeFirst();
        var pipelines = new ArrayList<Pipeline>();
        var mappers = new ArrayList<Mapper>();
        Pipeline pipeline;
        String name = "";
        for (var line : lines) {
            switch (line) {
                case String s when s.matches("([a-zA-z: ]|-)+") -> {
                    mappers.clear();
                    name = s;
                }
                case String s when s.isEmpty() -> pipelines.add(new Pipeline(name, mappers));
                case String s -> mappers.add(Mapper.fromLine(s));
            }
        }
        pipelines.add(new Pipeline(name, mappers));

        for (var pipe : pipelines) {
            seeds = pipe.map(seeds);
        }

        System.out.println(seeds.parallel().min().orElseThrow());
    }

    public static void main(String[] args) throws IOException {
        var example1 = Paths.get("Day5", "src", "main", "resources", "1", "example.txt");
        var example2 = Paths.get("Day5", "src", "main", "resources", "2", "example.txt");
        var input = Paths.get("Day5", "src", "main", "resources", "input.txt");

        puzzl2(input);
    }
}