package fr.aoc;

import java.util.Objects;

public class Cell {
    int symbol;
    int value;

    public Cell(int symbol, int value) {
        this.symbol = symbol;
        this.value = value;
    }

    public int symbol() {
        return symbol;
    }

    public int value() {
        return value;
    }

    public void inc() {
        if (symbol == 'O') {
            this.value += 1;
        }
    }

    public void dec() {
        if (symbol == 'O') {
            this.value -= 1;
            if (this.value < 0) {
                throw new IllegalStateException("inferior");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return symbol == cell.symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}