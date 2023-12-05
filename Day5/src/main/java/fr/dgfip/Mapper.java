package fr.dgfip;

import java.util.HashMap;

public record Mapper(long source, long destination, long range) {

    public static Mapper fromLine(String line) {
        // "destination source range"
        var tokens = line.split("\\s");
        var source = Long.parseLong(tokens[1]);
        var destination = Long.parseLong(tokens[0]);
        var range = Long.parseLong(tokens[2]);

        return new Mapper(source, destination, range);
    }

    public long map(long seed) {
        if (source <= seed && seed < source + range) {
            return seed - (source - destination);
        }
        return seed;
    }
}
