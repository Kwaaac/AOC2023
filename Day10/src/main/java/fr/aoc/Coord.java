package fr.aoc;

public record Coord(int x, int y) {
    static Coord WEST = new Coord(0, -1);
    static Coord EAST = new Coord(0, 1);
    static Coord NORTH = new Coord(-1, 0);
    static Coord SOUTH = new Coord(1, 0);

    static Coord GROUND = new Coord(0, 0);

    public Coord merge(Coord coord) {
        return new Coord(x + coord.x, y + coord.y);
    }
}