package fr.aoc;

import java.util.Arrays;
import java.util.Objects;

public enum PipeType {
    /*
    | is a vertical pipe connecting north and south.
- is a horizontal pipe connecting east and west.
L is a 90-degree bend connecting north and east.
J is a 90-degree bend connecting north and west.
7 is a 90-degree bend connecting south and west.
F is a 90-degree bend connecting south and east.
. is ground; there is no pipe in this tile.
S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
     */

    NS("|", Coord.NORTH, Coord.SOUTH),
    EW("-", Coord.WEST, Coord.EAST),
    NE("L", Coord.NORTH, Coord.EAST),
    NW("J", Coord.NORTH, Coord.WEST),
    SW("7", Coord.SOUTH, Coord.WEST),
    SE("F", Coord.SOUTH, Coord.EAST),
    GROUND(".", Coord.GROUND, Coord.GROUND),
    START("S", Coord.GROUND, Coord.GROUND);

    private final String symbol;

    public Coord dir1() {
        return dir1;
    }

    public Coord dir2() {
        return dir2;
    }

    private final Coord dir1;
    private final Coord dir2;

    PipeType(String symbol, Coord dir1, Coord dir2) {
        Objects.requireNonNull(symbol);
        Objects.requireNonNull(dir1);
        Objects.requireNonNull(dir2);
        this.dir1 = dir1;
        this.dir2 = dir2;
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }

    public static PipeType fromString(String symbol) {
        return Arrays.stream(PipeType.values())
                .filter(s -> s.symbol.equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unexpected symbol" + symbol));
    }

    @Override
    public String toString() {
        return symbol;
    }
}
