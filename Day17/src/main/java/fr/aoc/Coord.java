package fr.aoc;

public record Coord(int i, int j) {
    public Coord plus(Coord dir) {
        return new Coord(i + dir.i, j + dir.j);
    }
}
