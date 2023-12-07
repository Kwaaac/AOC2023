package fr.aoc;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum HandType {
    __, HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_KIND, FULL_HOUSE, FOUR_KIND, FIVE_KIND;

    private static void convertJ(Map<CardsType, Integer> group) {
        var j = group.get(CardsType.J);
        if (j == null || j == 5) {
            return;
        }
        group.remove(CardsType.J);
        var max = group.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow();
        group.put(max.getKey(), max.getValue() + j);
    }

    static HandType fromHand(List<CardsType> hand) {


        var group = hand.stream()
                .collect(Collectors.toMap(
                        Function.identity(), // grouping by key -> Enum CardsType
                        cardsType -> 1, // mapping value
                        Integer::sum, // merging method --> sum (act similar to xxx.mapToLong(obj -> 1L).sum() or xxx.count() )
                        () -> new EnumMap<CardsType, Integer>(CardsType.class) // EnumMap can be more efficient than HashMap / not thread safe
                ));
        // for part 2 only
        convertJ(group);
        return switch (group.size()) {
            case 1 -> FIVE_KIND;
            case 2 -> {
                if (group.containsValue(4)) yield FOUR_KIND;
                yield FULL_HOUSE;
            }
            case 3 -> {
                if (group.containsValue(3)) yield THREE_KIND;
                yield TWO_PAIR;
            }
            case 4 -> ONE_PAIR;
            case 5 -> HIGH_CARD;
            default -> throw new IllegalStateException("Seems like this hand is not allowed");
        };
    }
}
