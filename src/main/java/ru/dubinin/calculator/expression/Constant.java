package ru.dubinin.calculator.expression;

import java.util.List;
import ru.dubinin.calculator.KNF.*;

public class Constant implements Expression {

    public Constant(Integer c) {
        this.value = c;
    }

    @Override
    public KNFWrapper toKNF() {
        if (value == -1) {
            return new KNFWrapper(List.of(new AtomKNF(AtomKNF.ONE, 1)));
        } else {
            return new KNFWrapper(List.of(new AtomKNF(value)));
        }
    }

    @Override
    public String toString() {
        return value == -1 ? "w" : value.toString();
    }

    private final Integer value;
}
