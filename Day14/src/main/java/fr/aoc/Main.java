package fr.aoc;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) throws IOException {
        var example = Paths.get("Day14", "src", "main", "resources", "example.txt");
        var input = Paths.get("Day14", "src", "main", "resources", "input.txt");

        puzzle2(input);
    }


    private static void puzzle1(Path path) throws IOException {
        var lines = Files.readAllLines(path);

        List<Grid.Cell[]> list = new ArrayList<>();
        var cells = new ArrayList<Grid.Cell>();
        for (int i = 0; i < lines.size(); i++) {
            String s = lines.get(i);
            int value = i;
            Grid.Cell[] array = s.chars()
                    .mapToObj(j -> {
                        if (j == 'O') {
                            Grid.Cell cell = new Grid.Cell(j, lines.size() - value);
                            cells.add(cell);
                            return cell;
                        }
                        return new Grid.Cell(j, 0);
                    })
                    .toArray(Grid.Cell[]::new);
            list.add(array);
        }
        var arr = list.toArray(new Grid.Cell[0][]);

        var grid = new Grid(arr, cells);
        var dirs = Dir.values();
        grid.moveAll(Dir.NORTH);
        System.out.println(grid.sum());
    }


    private static void puzzle2(Path path) throws IOException {
        var lines = Files.readAllLines(path);

        List<Grid.Cell[]> list = new ArrayList<>();
        var cells = new ArrayList<Grid.Cell>();
        for (int i = 0; i < lines.size(); i++) {
            String s = lines.get(i);
            int value = i;
            Grid.Cell[] array = s.chars()
                    .mapToObj(j -> {
                        if (j == 'O') {
                            Grid.Cell cell = new Grid.Cell(j, lines.size() - value);
                            cells.add(cell);
                            return cell;
                        }
                        return new Grid.Cell(j, 0);
                    })
                    .toArray(Grid.Cell[]::new);
            list.add(array);
        }
        var arr = list.toArray(new Grid.Cell[0][]);

        var grid = new Grid(arr, cells);
        var dirs = Dir.values();
        var map = new HashMap<Integer, List<Integer>>();
        for (int i = 1; i <= 200; i++) {
            for (var dir : Dir.values()) {
                grid.moveAll(dir);
            }
            var sum = grid.sum();
//            if (1_000_000_000 % i == 0) {
//                System.out.println(sum);
//            }
            map.computeIfAbsent(sum, k -> new ArrayList<>()).add(i);
        }

        System.out.println(map.size());
        map.keySet().stream().map(s -> lcm(BigInteger.valueOf(s), BigInteger.valueOf(1_000_000_000))).forEach(System.out::println);
    }

    public static int lcm(BigInteger number1, BigInteger number2) {
        BigInteger gcd = number1.gcd(number2);
        BigInteger absProduct = number1.multiply(number2).abs();
        return absProduct.divide(gcd).intValue();
    }
}

