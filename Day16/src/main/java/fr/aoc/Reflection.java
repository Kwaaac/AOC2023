package fr.aoc;

public enum Reflection {
    UP(-1, 0),
    LEFT(0, -1),
    DOWN(1, 0),
    RIGHT(0, 1);

    private final int i, j;

    Reflection(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int i() {
        return i;
    }

    public int j() {
        return j;
    }
}
