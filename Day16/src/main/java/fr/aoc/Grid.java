package fr.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;

public class Grid {

    private final Cell[][] grid;


    public Grid(Cell[][] grid) {
        this.grid = grid;
    }

    public long countEnergized() {
        return Arrays.stream(grid).parallel().mapToLong(row ->
                        Arrays.stream(row).filter(Cell::isEnergized).count())
                .sum();
    }

    @Override
    public String toString() {
        var sj = new StringJoiner("\n");
        for (Cell[] c : grid) {
            sj.add(Arrays.stream(c).map(ch -> (char) ch.symbol() + "").collect(Collectors.joining("")));
        }
        return sj.toString();
    }

    public String toStringEnergized() {
        var sj = new StringJoiner("\n");
        for (Cell[] row : grid) {
            sj.add(Arrays.stream(row).map(c -> c.isEnergized() ? "#" : ".").collect(Collectors.joining("")));
        }
        return sj.toString();
    }

    private void energize(Reflection dir, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return;
        }
        var cell = grid[i][j];
        if (cell.visited(dir)) return;
        cell.energize();
        //System.out.println(toStringEnergized());
        var dirs = cell.nextDirection(dir);

        for (var next : dirs) {
            energize(next, i + next.i(), j + next.j());
        }
    }

    public void energize() {
        energize(Reflection.RIGHT, 0, 0);

    }

    private void clear() {
        Arrays.stream(grid).forEach(row -> Arrays.stream(row).forEach(Cell::clear));
    }

    public long energizeAll() {
        var lst = new ArrayList<LongSupplier>();
        for (int i = 0; i < grid.length; i++) {
            int finalI = i;
            lst.add(() -> {
                energize(Reflection.RIGHT, finalI, 0);
                var res = countEnergized();
                clear();
                System.out.println(STR."\{finalI} - 0, RIGHT");
                return res;
            });
            lst.add(() -> {
                energize(Reflection.LEFT, finalI, grid[0].length - 1);
                var res = countEnergized();
                clear();
                System.out.println(STR."\{finalI} - \{grid[0].length - 1}, LEFT");
                return res;
            });
        }

        for (int j = 0; j < grid[0].length; j++) {
            int finalJ = j;
            lst.add(() -> {
                energize(Reflection.DOWN, 0, finalJ);
                var res = countEnergized();
                clear();
                System.out.println(STR."0 - \{finalJ}, DOWN");
                return res;
            });
            lst.add(() -> {
                energize(Reflection.UP, grid.length - 1, finalJ);
                var res = countEnergized();
                clear();
                System.out.println(STR."\{grid.length - 1} - \{finalJ}, UP");
                return res;
            });
        }

        return lst.stream().mapToLong(LongSupplier::getAsLong).max().orElseThrow();
    }
}
