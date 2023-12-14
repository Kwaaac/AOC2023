package fr.aoc;

public enum Dir {
    NORTH(-1, 0),
    WEST(0, -1),
    SOUTH(1, 0),
    EAST(0, 1);

    private final int i, j;

    Dir(int i, int j) {
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
