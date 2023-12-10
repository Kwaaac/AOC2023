package fr.aoc;

import java.util.Arrays;
import java.util.regex.Pattern;

public record Gear(int line, int[] position) implements Piece {

    public static Pattern pattern = Pattern.compile("\\*");

    @Override
    public boolean compute(Piece number) {
        var pos = position[0];
        var numberPos = Arrays.stream(number.position()).boxed().toList();
        for (int i = line - 1; i <= line + 1; i++) {
            for (int j = pos - 1; j <= pos + 1; j++) {
                if (number.line() == i && numberPos.contains(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int value() {
        return '*';
    }
}
