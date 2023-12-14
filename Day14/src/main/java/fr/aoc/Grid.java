package fr.aoc;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Grid {

    private final Cell[][] grid;

    private final List<Cell> cells;

    public Grid(Cell[][] grid, List<Cell> cells) {
        this.grid = grid;
        this.cells = cells;
    }

    @Override
    public String toString() {
        var sj = new StringJoiner("\n");
        for (Cell[] c : grid) {
            sj.add(Arrays.toString(Arrays.stream(c).map(ch -> (char) ch.symbol).toArray()));
        }
        return sj.toString();
    }

    public String toStringValue() {
        var sj = new StringJoiner("\n");
        for (Cell[] c : grid) {
            sj.add(Arrays.toString(Arrays.stream(c).map(Cell::value).toArray()));
        }
        return sj.toString();
    }

    public int sum() {
        return cells.stream().mapToInt(Cell::value).sum();
    }

    private void move(Dir dir, int i, int j) {
        var c = grid[i][j];
        if (c.symbol != 'O') return;
        int newI = i + dir.i();
        int newJ = j + dir.j();

        while (newI >= 0 && newI < grid.length && newJ >= 0 && newJ < grid.length) {
            var next = grid[newI][newJ];
            if (next.symbol == '#' || next.symbol == 'O') return;

            grid[newI][newJ] = grid[i][j];
            grid[i][j] = new Cell('.', 0);

            if (dir == Dir.NORTH) grid[newI][newJ].inc();
            else if (dir == Dir.SOUTH) grid[newI][newJ].dec();
            i = newI;
            j = newJ;
            newI = i + dir.i();
            newJ = j + dir.j();
        }

    }

    public void moveAll(Dir dir) {
        switch (dir) {
            case NORTH, WEST -> {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        move(dir, i, j);
                    }
                }
            }
            case SOUTH -> {
                for (int i = grid.length - 1; i >= 0; i--) {
                    for (int j = 0; j < grid[i].length; j++) {
                        move(Dir.SOUTH, i, j);
                    }
                }
            }
            case EAST -> {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = grid[i].length - 1; j >= 0; j--) {
                        move(Dir.EAST, i, j);
                    }
                }
            }
            default -> throw new AssertionError();
        }
    }

    public Cell[][] getGrid() {
        return Arrays.stream(grid).toArray(Cell[][]::new);
    }

    public static class Cell {
        int symbol;
        int value;

        public Cell(int symbol, int value) {
            this.symbol = symbol;
            this.value = value;
        }

        public int symbol() {
            return symbol;
        }

        public int value() {
            return value;
        }

        public void inc() {
            if (symbol == 'O') {
                this.value += 1;
            }
        }

        public void dec() {
            if (symbol == 'O') {
                this.value -= 1;
                if (this.value < 0) {
                    throw new IllegalStateException("inferior");
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return symbol == cell.symbol;
        }

        @Override
        public int hashCode() {
            return Objects.hash(symbol);
        }
    }
}
