package ru.dubinin.calculator.KNF;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class KNFWrapper {
    public KNFWrapper(List<AtomKNF> sum) {
        this.sum = sum;
    }

    public Integer compare(KNFWrapper o) {
        for (int i = 0; i < Math.min(sum.size(), o.sum.size()); ++i) {
            Integer res = sum.get(i).compare(o.sum.get(i));
            if (res != 0) {
                return res;
            }
        }
        return Integer.compare(sum.size(), o.sum.size());
    }

    public static KNFWrapper pow(KNFWrapper a, KNFWrapper b) {
        if (isZero(b)) {
            return AtomKNF.ONE;
        }
        if (isZero(a)) {
            return new KNFWrapper(List.of(AtomKNF.ZERO));
        }
        if (isNonZeroInteger(a) && a.sum.get(0).getMul() == 1) {
            return AtomKNF.ONE;
        }
        if (isNonZeroInteger(b)) {
            KNFWrapper res = a;
            for (int i = 0; i < b.sum.get(0).getMul() - 1; ++i) {
                res = mul(res, a);
            }
            return res;
        } else {
            Integer last;
            AtomKNF b_last = b.sum.get(b.sum.size() - 1);
            if (AtomKNF.isNonZeroInteger(b_last)) {
                last = b_last.getMul();
            } else {
                b_last = AtomKNF.ZERO;
                last = 0;
            }
            KNFWrapper b_tmp = new KNFWrapper(new ArrayList<>(b.sum));
            if (last != 0) {
                b_tmp.sum.remove(b_tmp.sum.size() - 1);
            }
            if (isNonZeroInteger(a)) {
                return new KNFWrapper(List.of(
                        new AtomKNF(minusOne(b_tmp), BigInteger.valueOf(a.sum.get(0).getMul()).pow(last).intValue()))
                );
            } else {
                KNFWrapper a_f = a.sum.get(0).getDeg();
                KNFWrapper l = new KNFWrapper(List.of(new AtomKNF(mul(a_f, b_tmp), 1)));
                KNFWrapper r = pow(a, new KNFWrapper(List.of(b_last)));
                return mul(l, r);
            }
        }
    }

    public static KNFWrapper mul(KNFWrapper a, KNFWrapper b) {
        if (isZero(a) || isZero(b)) {
            return new KNFWrapper(List.of(AtomKNF.ZERO));
        }
        AtomKNF last = b.sum.get(b.sum.size() - 1);
        List<AtomKNF> result = new ArrayList<>();
        AtomKNF a_f = a.sum.get(0);
        if (AtomKNF.isNonZeroInteger(last)) {
            for (int i = 0; i < b.sum.size() - 1; ++i) {
                AtomKNF b_j = b.sum.get(i);
                result.add(new AtomKNF(KNFWrapper.add(a_f.getDeg(), b_j.getDeg()), b_j.getMul()));
            }
            result.add(new AtomKNF(a_f.getDeg(), a_f.getMul() * last.getMul()));
            for (int i = 1; i < a.sum.size(); ++i) {
                result.add(a.sum.get(i));
            }
        } else {
            for (AtomKNF b_j : b.sum) {
                result.add(new AtomKNF(KNFWrapper.add(a_f.getDeg(), b_j.getDeg()), b_j.getMul()));
            }
        }
        return new KNFWrapper(result);
    }

    public static KNFWrapper add(KNFWrapper a, KNFWrapper b) {
        if (a == null) { // 0 + b = b;
            return b;
        }
        if (b == null) { // a + 0 = a;
            return a;
        }
        List<AtomKNF> result = new ArrayList<>();
        AtomKNF first = b.sum.get(0);
        boolean added = false;
        for (AtomKNF atom : a.sum) {
            Integer resultComparing = atom.onlyDegCompare(first);
            if (resultComparing == 0) {
                result.add(new AtomKNF(atom.getDeg(), atom.getMul() + first.getMul()));
                added = true;
                break;
            } else if (resultComparing < 0) {
                result.add(first);
                added = true;
                break;
            }
            result.add(atom);
        }
        if (!added) {
            result.add(first);
        }
        for (int i = 1; i < b.sum.size(); ++i) {
            result.add(b.sum.get(i));
        }
        while (result.size() != 1 && result.get(result.size() - 1).compare(AtomKNF.ZERO) == 0) {
            result.remove(result.size() - 1);
        }
        return new KNFWrapper(result);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("(");
        for (int i = 0; i < sum.size(); ++i) {
            AtomKNF a = sum.get(i);
            if (i != sum.size() - 1) {
                s.append(a.toString()).append(" + ");
            } else {
                s.append(a.toString());
            }
        }
        return s.append(")").toString();
    }

    private static boolean isZero(KNFWrapper a) {
        return a.sum.size() == 1 && AtomKNF.isZero(a.sum.get(0));
    }

    private static boolean isNonZeroInteger(KNFWrapper a) {
        return a.sum.size() == 1 && AtomKNF.isNonZeroInteger(a.sum.get(0));
    }

    private static KNFWrapper minusOne(KNFWrapper a) {
        List<AtomKNF> result = new ArrayList<>();
        for (AtomKNF cur : a.sum) {
            if (cur.getDeg() != null && isNonZeroInteger(cur.getDeg())) {
                int val = cur.getDeg().sum.get(0).getMul() - 1;
                if (val == 0) {
                    result.add(new AtomKNF(cur.getMul()));
                } else {
                    KNFWrapper tmp = new KNFWrapper(List.of(new AtomKNF(val)));
                    result.add(new AtomKNF(tmp, cur.getMul()));
                }
            } else {
                result.add(cur);
            }
        }
        return new KNFWrapper(result);
    }

    private final List<AtomKNF> sum;
}
