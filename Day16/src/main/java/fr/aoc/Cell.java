package fr.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Cell {
    private final int symbol;
    private final Function<Reflection, List<Reflection>> reflection;

    private final List<Reflection> beams = new ArrayList<>();
    private boolean energized;

    public Cell(int symbol, Function<Reflection, List<Reflection>> reflections) {
        this.reflection = reflections;
        this.symbol = symbol;
    }

    public static Cell fromSpace(int space) {
        return switch (space) {
            case '.' -> new Cell('.', List::of);
            case '/' -> new Cell('/', sourceDir -> List.of(switch (sourceDir) {
                case UP -> Reflection.RIGHT;
                case LEFT -> Reflection.DOWN;
                case RIGHT -> Reflection.UP;
                case DOWN -> Reflection.LEFT;
            }));
            case '\\' -> new Cell('\\', sourceDir -> List.of(switch (sourceDir) {
                case UP -> Reflection.LEFT;
                case RIGHT -> Reflection.DOWN;
                case LEFT -> Reflection.UP;
                case DOWN -> Reflection.RIGHT;
            }));
            case '|' -> new Cell('|', sourceDir -> switch (sourceDir) {
                case UP -> List.of(Reflection.UP);
                case DOWN -> List.of(Reflection.DOWN);
                case LEFT, RIGHT -> List.of(Reflection.UP, Reflection.DOWN);
            });
            case '-' -> new Cell('-', sourceDir -> switch (sourceDir) {
                case RIGHT -> List.of(Reflection.RIGHT);
                case LEFT -> List.of(Reflection.LEFT);
                case UP, DOWN -> List.of(Reflection.LEFT, Reflection.RIGHT);
            });
            default -> throw new IllegalStateException("Unexpected value: " + space);
        };
    }

    @Override
    public String toString() {
        return (char) symbol + "";
    }

    public void energize() {
        this.energized = true;
    }

    public List<Reflection> nextDirection(Reflection dir) {
        beams.add(dir);
        return reflection.apply(dir);
    }

    public boolean visited(Reflection dir) {
        return energized && beams.contains(dir);
    }

    public void clear() {
        this.energized = false;
        this.beams.clear();
    }


    public int symbol() {
        return symbol;
    }

    public boolean isEnergized() {
        return energized;
    }
}
