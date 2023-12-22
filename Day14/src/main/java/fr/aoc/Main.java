package fr.aoc;

import java.io.IOException;
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

        List<Cell[]> list = new ArrayList<>();
        var cells = new ArrayList<Cell>();
        for (int i = 0; i < lines.size(); i++) {
            String s = lines.get(i);
            int value = i;
            Cell[] array = s.chars()
                    .mapToObj(j -> {
                        if (j == 'O') {
                            Cell cell = new Cell(j, lines.size() - value);
                            cells.add(cell);
                            return cell;
                        }
                        return new Cell(j, 0);
                    })
                    .toArray(Cell[]::new);
            list.add(array);
        }
        var arr = list.toArray(new Cell[0][]);

        var grid = new Grid(arr, cells);
        var dirs = Dir.values();
        grid.moveAll(Dir.NORTH);
        System.out.println(grid.sum());
    }

    private static void puzzle2(Path path) throws IOException {
        var lines = Files.readAllLines(path);

        List<Cell[]> list = new ArrayList<>();
        var cells = new ArrayList<Cell>();
        for (int i = 0; i < lines.size(); i++) {
            String s = lines.get(i);
            int value = i;
            Cell[] array = s.chars()
                    .mapToObj(j -> {
                        if (j == 'O') {
                            Cell cell = new Cell(j, lines.size() - value);
                            cells.add(cell);
                            return cell;
                        }
                        return new Cell(j, 0);
                    })
                    .toArray(Cell[]::new);
            list.add(array);
        }
        var arr = list.toArray(new Cell[0][]);
        var grid = new Grid(arr, cells);
        var dirs = Dir.values();
        var map = new HashMap<List<List<Cell>>, Integer>();
        var index = new ArrayList<Integer>();

        for (int i = 0; ; i++) {
            for (var dir : Dir.values()) {
                grid.moveAll(dir);
            }
            var sum = grid.sum();
            var old = map.put(grid.getGrid(), grid.sum());
            if (old != null) {
                // cycle found !
                map.clear();
                index.clear();
                for (int j = 0; ; j++) {
                    for (var dir : Dir.values()) {
                        grid.moveAll(dir);
                    }
                    sum = grid.sum();

                    old = map.put(grid.getGrid(), sum);
                    if (old == null) index.add(sum);
                    else {
                        var i1 = i - index.size();
                        var index1 = (1_000_000_000 % i1) + i1 % index.size();

                        System.out.println(index.get(index1));
                        return;
                    }
                }
            } else {
                index.add(sum);
            }
        }
    }
}

