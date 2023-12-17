package fr.aoc;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        var example = Paths.get("Day17", "src", "main", "resources", "example.txt");
        var input = Paths.get("Day17", "src", "main", "resources", "input.txt");

        puzzle1(input);
    }

    private static void puzzle1(Path path) throws IOException {
        var lines = Files.readAllLines(path);
        var grid = lines.stream()
                .map(row -> row.chars()
                        .map(c -> Integer.parseInt((char) c + ""))
                        .toArray()
                ).toArray(int[][]::new);

        var costMap = new HashMap<Block, Integer>();
        var todo = new PriorityQueue<HeatBlock>();
        var end = new Coord(lines.size() - 1, lines.getFirst().length() - 1);
        int max = 3;

        var initial = new Block(new Coord(0, 0), new Coord(0, 1), 0);

        costMap.put(initial, 0);
        todo.add(new HeatBlock(initial, 0));

        while (!todo.isEmpty()) {
            var current = todo.poll();
            if (current.block.coord().equals(end)) {
                System.out.println(current.heatCost);
                return;
            }

            var nextToAdd = current.next(3).stream()
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

    private record HeatBlock(Block block, int heatCost) implements Comparable<HeatBlock> {
        @Override
        public int compareTo(@NotNull HeatBlock o) {
            return Integer.compare(heatCost, o.heatCost);
        }

        public List<Block> next(int max) {
            return block.next(max);
        }
    }
}