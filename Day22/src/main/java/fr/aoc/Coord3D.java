package fr.aoc;

public record Coord3D(int x, int y, int z) {
    public static Coord3D fromLine(String line) {
        var tokens = line.split(",");
        return new Coord3D(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
    }

    @Override
    public String toString() {
        return STR."\{x},\{y},\{z}";
    }
}
