package fr.aoc;

import java.util.regex.Pattern;

public record Number(int value, int line, int[] position) implements Piece {

    public static final Pattern pattern = Pattern.compile("\\d+");

    public boolean compute(Piece grid) {
        return grid.compute(this);
    }

}
