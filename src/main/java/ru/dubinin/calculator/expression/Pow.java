package ru.dubinin.calculator.expression;
import ru.dubinin.calculator.KNF.KNFWrapper;
public class Pow implements Expression {

    public Pow(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public KNFWrapper toKNF() {
        KNFWrapper a = left.toKNF();
        KNFWrapper b = right.toKNF();
        return KNFWrapper.pow(a, b);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }

    public final Expression left;
    public final Expression right;
}
