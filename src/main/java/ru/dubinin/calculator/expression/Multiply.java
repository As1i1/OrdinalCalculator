package ru.dubinin.calculator.expression;
import ru.dubinin.calculator.KNF.KNFWrapper;
public class Multiply implements Expression {
    public Multiply(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public KNFWrapper toKNF() {
        KNFWrapper a = left.toKNF();
        KNFWrapper b = right.toKNF();
        return KNFWrapper.mul(a, b);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }

    private final Expression left;
    private final Expression right;
}
