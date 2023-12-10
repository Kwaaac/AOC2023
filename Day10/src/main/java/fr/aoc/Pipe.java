package fr.aoc;

import java.util.Objects;

public class Pipe {


    private final PipeType type;

    private final Coord coord;

    private final Coord in;

    private final Coord out;

    public Pipe(PipeType type, Coord coord, Coord in, Coord out) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(coord);
        Objects.requireNonNull(in);
        Objects.requireNonNull(out);

        this.type = type;
        this.coord = coord;
        this.in = in;
        this.out = out;
    }

    public PipeType type() {
        return type;
    }

    public Coord in() {
        return in;
    }

    public Coord out() {
        return out;
    }

    public Coord nextCoords(Coord coord) {

        if (coord.equals(in)) {
            return out;
        }
        if (coord.equals(out)) {
            return in;
        }

        throw new IllegalStateException("wtf? " + coord);
    }

    @Override
    public String toString() {
        return type.symbol() /*+ "(" + coord +")"*/;
    }

    public Coord coord() {
        return coord;
    }
}
