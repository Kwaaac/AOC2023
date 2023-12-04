package fr.aoc;

import java.util.HashMap;
import java.util.Map;

public record Cube() {

    public static Map<String, Integer> fromLine(String line) {
        var map = new HashMap<String, Integer>();

        // line -> 3 blue, 4 red
        // [3 blue, 4 red]
        var setTokens = line.split(", ");

        for (var token : setTokens) {
            // [3, blue]
            var cubes = token.split(" ");
            var nbr = cubes[0];
            var color = cubes[1];

            map.put(color, Integer.parseInt(nbr));
        }

        return map;
    }
}
