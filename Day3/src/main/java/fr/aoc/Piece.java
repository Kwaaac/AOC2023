package fr.aoc;

public interface Piece {
    boolean compute(Piece piece);

    default int value() {
        return '.';
    }

    default int line() {
        return -1;
    }

    default int[] position() {
        return new int[0];
    }
}
