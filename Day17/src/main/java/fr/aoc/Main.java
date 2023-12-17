package fr.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        var example = Paths.get("Day17", "src", "main", "resources", "example.txt");
        var example2 = Paths.get("Day17", "src", "main", "resources", "example2.txt");
        var input = Paths.get("Day17", "src", "main", "resources", "input.txt");

        puzzle2(example2);
    }

    private static void puzzle(Path path, int min, int max) throws IOException {
        var lines = Files.readAllLines(path);
        var grid = lines.stream()
                .map(row -> row.chars()
                        .map(c -> Integer.parseInt((char) c + ""))
                        .toArray()
                ).toArray(int[][]::new);

        var printGrid = lines.stream()
                .map(row -> row.chars()
                        .mapToObj(c -> (char) c + "")
                        .toArray(String[]::new)
                ).toArray(String[][]::new);

        var costMap = new HashMap<Block, Integer>();
        // minimize the heatcost, the next block to poll is the minimum one
        var todo = new PriorityQueue<HeatBlock>();
        var end = new Coord(lines.size() - 1, lines.getFirst().length() - 1);
        var rightInitial = new Block(new Coord(0, 0), new Coord(0, 1), 0);
        var southInitial = new Block(new Coord(0, 0), new Coord(1, 0), 0);
        costMap.put(rightInitial, 0);
        costMap.put(southInitial, 0);
        todo.add(new HeatBlock(rightInitial, 0));
        todo.add(new HeatBlock(southInitial, 0));

        while (!todo.isEmpty()) {
            var current = todo.poll();
            printGrid[current.block.coord().i()][current.block.coord().j()] = switch (current.block.direction()) {
                case Coord dir when dir.i() == -1 && dir.j() == 0 -> "^";
                case Coord dir when dir.i() == 1 && dir.j() == 0 -> "v";
                case Coord dir when dir.i() == 0 && dir.j() == 1 -> ">";
                case Coord dir when dir.i() == 0 && dir.j() == -1 -> "<";
                default -> throw new IllegalStateException("Unexpected value: " + current.block.direction());
            };

            if (current.block.coord().equals(end) && current.block.blocks() >= min) {
                System.out.println(current.heatCost);
                //System.out.println(print(printGrid));
                return;
            }

            var nextToAdd = current.next(min, max).stream()
                    .filter(block -> block.withinGrid(grid.length, grid[0].length))
                    .flatMap(block -> {
                        var newCost = current.heatCost + grid[block.coord().i()][block.coord().j()];
                        if (newCost < costMap.getOrDefault(block, Integer.MAX_VALUE)) {
                            costMap.put(block, newCost);
                            return Stream.of(new HeatBlock(block, newCost));
                        }
                        return Stream.empty();
                    }).toList();

            todo.addAll(nextToAdd);
        }
        System.out.println("pet");
    }

    private static void puzzle1(Path path) throws IOException {
        puzzle(path, 0, 3);
    }

    private static void puzzle2(Path path) throws IOException {
        puzzle(path, 4, 10);
    }

    private static String print(String[][] grid) {
        var sj = new StringJoiner("\n");
        for (String[] strings : grid) {
            sj.add(String.join("", strings));
        }
        return sj.toString();
    }

    private record HeatBlock(Block block, int heatCost) implements Comparable<HeatBlock> {
        @Override
        public int compareTo(HeatBlock o) {
            return Integer.compare(heatCost, o.heatCost);
        }

        public List<Block> next(int min, int max) {
            return block.next(min, max);
        }
    }
}