package ru.dubinin.calculator.expression;

import ru.dubinin.calculator.KNF.KNFWrapper;

public interface Expression {
    KNFWrapper toKNF();
}
