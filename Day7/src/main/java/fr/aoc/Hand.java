package fr.aoc;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record Hand(List<CardsType> hand, HandType handType, int bid) implements Comparable<Hand> {
    public Hand(List<CardsType> hand, HandType handType, int bid) {
        Objects.requireNonNull(handType);
        Objects.requireNonNull(hand);
        if (hand.size() != 5) {
            throw new IllegalArgumentException("The hand doesn't contain exactly 5 cards");
        }

        this.hand = List.copyOf(hand);
        this.handType = handType;
        this.bid = bid;
    }

    static Hand fromLine(String line) {
        var tokens = line.split("\\s+");
        var strHand = tokens[0];
        var bid = Integer.parseInt(tokens[1]);
        var hand = strHand.chars().mapToObj(c -> CardsType.fromChar((char) c)).toList();

        var handType = HandType.fromHand(hand);

        return new Hand(hand, handType, bid);
    }

    public int compute(int i) {
        System.out.println(i + " * " + bid);
        return i * bid;
    }

    @Override
    public String toString() {
        return handType.toString();
    }

    @Override
    public int compareTo(Hand o) {
        int type = handType.compareTo(o.handType);
        if (type == 0) {
            System.out.println(hand);
            System.out.println(o.hand);
            int letter = -1;
            int i = 0;
            do {
                letter = hand.get(i).compareTo(o.hand.get(i));
                System.out.println("i = " + i);
                System.out.println("letter = " + letter);
                i++;
            } while (letter == 0);
            return letter;
        }

        return type;
    }
}
