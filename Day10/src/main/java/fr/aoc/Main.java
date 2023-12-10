package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        var example1 = Paths.get("Day10", "src", "main", "resources", "example.txt");
        var example2 = Paths.get("Day10", "src", "main", "resources", "example2.txt");
        var input = Paths.get("Day10", "src", "main", "resources", "input.txt");


        puzzle1(input);
    }

    private static void puzzle1(Path path) throws IOException {
        var lines = Files.readAllLines(path);
        var grid = new Grid(lines.size(), lines.getFirst().length());

        int i = 0;
        for (var line : lines) {
            for (int j = 0; j < line.length(); j++) {
                var type = PipeType.fromString(line.charAt(j) + "");
                var coord = new Coord(i, j);
                var pipe = new Pipe(type, coord, coord.merge(type.dir1()), coord.merge(type.dir2()));

                grid.add(pipe, i, j);
            }
            i++;
        }

        System.out.println("grid = \n" + grid);
        System.out.println(grid.parcour() / 2);
    }
}