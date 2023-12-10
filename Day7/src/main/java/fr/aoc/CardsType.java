package fr.aoc;

public enum CardsType {
    J, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, T, Q, K, A;

    public static CardsType fromChar(char c) {
        return switch (c) {
            case '2' -> CardsType.TWO;
            case '3' -> CardsType.THREE;
            case '4' -> CardsType.FOUR;
            case '5' -> CardsType.FIVE;
            case '6' -> CardsType.SIX;
            case '7' -> CardsType.SEVEN;
            case '8' -> CardsType.EIGHT;
            case '9' -> CardsType.NINE;
            case 'T' -> CardsType.T;
            case 'J' -> CardsType.J;
            case 'Q' -> CardsType.Q;
            case 'K' -> CardsType.K;
            case 'A' -> CardsType.A;

            default -> throw new IllegalArgumentException("Unexpected value: " + c);
        };
    }
}
