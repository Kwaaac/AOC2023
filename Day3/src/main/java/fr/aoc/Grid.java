package fr.aoc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Grid implements Piece {
    private final int[][] grid;

    public Grid(int[][] grid) {
        this.grid = grid;
    }

    public static Grid fromLines(List<String> lines) {
        var bob = lines.stream().map(line -> line.chars().map(i -> i - 48).toArray()).toArray(int[][]::new);
        System.out.println(Arrays.stream(bob).map(Arrays::toString).collect(Collectors.joining("\n")));
        return new Grid(bob);
    }

    @Override
    public boolean compute(Piece piece) {
        var line = piece.line();
        var pos = Arrays.stream(piece.position()).boxed().toList();
        int bound = line + 1;
        for (int i = line - 1; i <= bound; i++) {
            for (int j = pos.getFirst() - 1; j <= pos.getLast() + 1; j++) {

                // We try to escape any ArrayOutOfBoundException ;-;
                if (i < 0 || j < 0 || i >= grid.length || j >= grid[i].length) {
                    continue;
                }
                int check = grid[i][j];
                if (check < 0 && check != -2 || check > 9) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int value() {
        return 0;
    }
}
