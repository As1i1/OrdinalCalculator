package ru.dubinin.calculator.KNF;

import java.util.List;

public class AtomKNF {

    public final static KNFWrapper ONE = new KNFWrapper(List.of(new AtomKNF(1)));

    public final static AtomKNF ZERO = new AtomKNF(0);

    public AtomKNF(KNFWrapper deg, Integer mul) {
        this.deg = deg;
        this.mul = mul;
    }

    public AtomKNF(Integer mul) {
        this.deg = null;
        this.mul = mul;
    }

    @Override
    public String toString() {
        if (deg == null) {
            return mul.toString();
        }
        String d = deg.toString();

        if (mul != 1) {
            return "(w ^ " + d + " * " + mul + ")";
        } else {
            return "(w ^ " + d + ")";
        }
    }

    public KNFWrapper getDeg() {
        return deg;
    }

    public Integer getMul() {
        return mul;
    }

    public Integer compare(AtomKNF o) {
        Integer res = onlyDegCompare(o);
        return res == 0 ? Integer.compare(mul, o.mul) : res;
    }

    public static boolean isZero(AtomKNF a) {
        return a.compare(ZERO) == 0;
    }

    public static boolean isNonZeroInteger(AtomKNF a) {
        return !isZero(a) && a.deg == null;
    }

    public Integer onlyDegCompare(AtomKNF o) {
        if (o.deg == null && deg == null) {
            return 0;
        } else if (o.deg == null) {
            return 1;
        } else if (deg == null) {
            return -1;
        } else {
            return deg.compare(o.deg);
        }
    }

    private final KNFWrapper deg;
    private final Integer mul;

}
