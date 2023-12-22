package fr.aoc;

public class Brick {
    private final Coord3D start, end;

    public Brick(Coord3D start, Coord3D end) {
        this.start = start;
        this.end = end;
    }

    public static Brick fromLine(String line) {
        var coords = line.split("~");
        var start = Coord3D.fromLine(coords[0]);
        var end = Coord3D.fromLine(coords[1]);

        return new Brick(start, end);
    }

    public Coord3D start() {
        return start;
    }

    public Coord3D end() {
        return end;
    }

    public int lowerEnd() {
        return Math.min(start.z(), end.z());
    }

    public int higherEnd() {
        return Math.max(start.z(), end.z());
    }

    @Override
    public String toString() {
        return STR."Brick{start=\{start}, end=\{end}}";
    }
}
