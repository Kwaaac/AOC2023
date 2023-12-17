package fr.aoc;

import java.util.ArrayList;
import java.util.List;

public record Block(Coord coord, Coord direction, int blocks) {
    public List<Block> next(int min, int max) {
        if (blocks < min) {
            return List.of(new Block(coord.plus(direction), direction, blocks + 1));
        }
        var lst = new ArrayList<Block>();
        // up       -1,  0
        // down      1,  0
        // left      0, -1
        // right     0,  1
        var left = new Coord(direction.j(), direction.i());
        var right = new Coord(-direction.j(), -direction.i());

        lst.add(new Block(coord.plus(left), left, 1));
        lst.add(new Block(coord.plus(right), right, 1));

        if (blocks < max) {
            lst.add(new Block(coord.plus(direction), direction, blocks + 1));
        }

        return lst;
    }

    public boolean withinGrid(int maxI, int maxJ) {
        return coord.i() >= 0 && coord.i() < maxI && coord.j() >= 0 && coord.j() < maxJ;
    }
}
