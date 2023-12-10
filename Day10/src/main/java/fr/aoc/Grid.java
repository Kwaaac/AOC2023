package fr.aoc;

import java.util.StringJoiner;

public class Grid {
    private final Pipe[][] grid;

    private Coord start;

    public Grid(int i, int j) {
        this.grid = new Pipe[i][j];
    }

    public void add(Pipe pipe, int i, int j) {
        if (pipe.type() == PipeType.START) {
            this.start = new Coord(i, j);
        }

        grid[i][j] = pipe;
    }

    public int parcour() {
        //ex 2
        // var startPipe = new Pipe(PipeType.SE, new Coord(2, 0), start.merge(Coord.SOUTH), start.merge(Coord.EAST));
        
        //input
        var startPipe = new Pipe(PipeType.EW, new Coord(92, 43), start.merge(Coord.WEST), start.merge(Coord.EAST));
        var nextCoord = startPipe.out();
        var currentPipe = startPipe;
        var beforePipe = grid[nextCoord.x()][nextCoord.y()];

        int i = 0;
        while (!PipeType.START.equals(currentPipe.type())) {
            var newCoord = currentPipe.nextCoords(beforePipe.coord());
            beforePipe = currentPipe;
            currentPipe = grid[newCoord.x()][newCoord.y()];
            i++;
        }

        return i;
    }

    @Override
    public String toString() {
        var sj = new StringJoiner("\n");
        for (int i = 0; i < grid.length; i++) {
            var sb = new StringBuilder();
            for (int j = 0; j < grid[i].length; j++) {
                sb.append(grid[i][j]);
            }
            sj.add(sb);
        }
        return sj.toString();
    }
}
