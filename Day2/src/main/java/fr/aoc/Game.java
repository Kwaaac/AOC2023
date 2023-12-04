package fr.aoc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record Game(int id, int red, int green, int blue) {

    /**
     * Check if set of cubes are valid based on the current Game set
     *
     * @param map map containing the list of cubes
     * @return true if the set is valid, false otherwise
     */
    public boolean isValid(Map<String, Integer> map) {
        var reds = map.getOrDefault("red", 0);
        var greens = map.getOrDefault("green", 0);
        var blues = map.getOrDefault("blue", 0);

        return reds <= red && greens <= green && blues <= blue;
    }

    /**
     * Takes all sets of a game and calculate the power of the game
     */
    public int power(List<Map<String, Integer>> maps) {
        var cache = new HashMap<String, Integer>();

        maps.stream()
                // entry(blue, 4), entry(blue, 6), entry(red, 5)...
                .flatMap(map -> map.entrySet().stream())
                // not very nice as it creates side effect
                .forEach(entry -> cache.merge(entry.getKey(), entry.getValue(), Math::max));
        //.collect(Collectors.groupingBy(entry -> entry.getKey(),
        //        Collectors.reducing((e1, e2) -> Integer.max(e1.getValue(), e2.getValue())));


        return cache.values().stream().reduce(1, (i1, i2) -> i1 * i2);
    }

    public static Game fromLine(String line) {
        var tokens = line.split(":");
        String id = String.valueOf(tokens[0].split(" ")[1]); // beurk

        // red, green and blue is arbitrary
        return new Game(Integer.parseInt(id), 12, 13, 14);
    }
}
