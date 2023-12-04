package fr.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Card(int id, List<Integer> winningNumbers, List<Integer> numbers) {

    private static List<Card> cachedCards;

    public int computeWinningNumbers() {
        return (int) Math.pow(2, numbers.stream().filter(winningNumbers::contains).count() - 1);
    }

    public static void setCachedCards(List<Card> cards) {
        Objects.requireNonNull(cards);
        cachedCards = List.copyOf(cards);
    }

    public static Stream<Card> stream() {
        return cachedCards.stream();
    }

    public Stream<Card> computeWinningScratchcards() {
        var winningCards = (int) numbers.stream().filter(winningNumbers::contains).count();
        if (winningCards == 0) {
            return Stream.of(this);
        }

        System.out.println("this = " + this);
        System.out.println(IntStream.rangeClosed(id, winningCards + id - 1).mapToObj(cachedCards::get).toList());
        return Stream.concat(Stream.of(this), IntStream.rangeClosed(id, Math.min(winningCards + id - 1, cachedCards.size() - 1))
                .mapToObj(cachedCards::get)
                .flatMap(card -> {
                    var cardsCount = (int) card.numbers.stream().filter(card.winningNumbers::contains).count();
                    if (cardsCount == 0) return Stream.of(card);

                    System.out.println("currend: " + card + " added = " + IntStream.rangeClosed(card.id, Math.min(cardsCount + card.id - 1, cachedCards.size() - 1))
                            .mapToObj(cachedCards::get).toList());

                    return Stream.concat(Stream.of(card), IntStream.rangeClosed(card.id, Math.min(cardsCount + card.id - 1, cachedCards.size() - 1))
                            .mapToObj(cachedCards::get));
                }));

    }


    public static Card fromLine(String line) {
        //Card 1: 41 48 83 86 17 | 83 86 6 31 17  9 48 53
        // 0    |       1        |          2
        var tokens = line.split(":|(\\|)");

        int id = Integer.parseInt(tokens[0].split("\\s+")[1]);

        var winningNumbers = Arrays.stream(tokens[1].trim().split("\\s+")).map(Integer::parseInt).toList();
        var numbers = Arrays.stream(tokens[2].trim().split("\\s+")).map(Integer::parseInt).toList();

        return new Card(id, winningNumbers, numbers);
    }

    @Override
    public String toString() {
        return "Card: " + id;
    }
}
