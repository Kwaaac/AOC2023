package fr.dgfip;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public record Pipeline(String name, List<Mapper> mappers) {

    public Pipeline(String name, List<Mapper> mappers) {
        this.name = Objects.requireNonNull(name);
        this.mappers = List.copyOf(mappers);
    }

    @Override
    public String toString() {
        return name;
    }

    public LongStream map(LongStream seeds) {
        return seeds.map(seed -> {
            for (var mapper : mappers) {
                long mappedSeed = mapper.map(seed);
                if(mappedSeed != seed){
                    return mappedSeed;
                }
            }
            return seed;
        });
    }
}
