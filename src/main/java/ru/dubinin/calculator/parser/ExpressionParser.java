package ru.dubinin.calculator.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

import ru.dubinin.calculator.expression.*;
public class ExpressionParser {

    public Expression parse(String expression) {
        return new Parser(expression).parseStart(BaseParser.END);
    }

    private static class Parser extends BaseParser {

        private static final Map<String, BinaryOperator<Expression>> OPERATORS_CONSTRUCTORS = Map.ofEntries(
                Map.entry("^", Pow::new),
                Map.entry("*", Multiply::new),
                Map.entry("+", Add::new));

        Parser(String source) {
            super(source);
        }

        private Expression parseStart(char endSymbol) {
            return parseAdd(endSymbol);
        }

        private Expression foldRight(List<Expression> expressions, String operator) {
            Expression current = expressions.get(expressions.size() - 1);
            for (int i = expressions.size() - 2; i >= 0; i--) {
                current = union(expressions.get(i), current, operator);
            }
            return current;
        }

        private Expression foldLeft(List<Expression> expressions, String operator) {
            Expression current = expressions.get(0);
            for (int i = 1; i < expressions.size(); i++) {
                current = union(current, expressions.get(i), operator);
            }
            return current;
        }

        private Expression parseAdd(char endSymbol) {
            List<Expression> list = new ArrayList<>();
            list.add(parseMultiply(endSymbol));
            skipWhitespace();
            while (!test(endSymbol)) {
                String operator = getOperator();
                if (!operator.equals("+")) {
                    return foldLeft(list, "+");
                }
                takeOperator(operator);
                list.add(parseMultiply(endSymbol));
                skipWhitespace();
            }
            return foldLeft(list, "+");
        }

        private Expression parseMultiply(char endSymbol) {
            List<Expression> list = new ArrayList<>();
            list.add(parsePow(endSymbol));
            skipWhitespace();
            while (!test(endSymbol)) {
                String operator = getOperator();
                if (!operator.equals("*")) {
                    return foldLeft(list, "*");
                }
                takeOperator(operator);
                list.add(parsePow(endSymbol));
                skipWhitespace();
            }
            return foldLeft(list, "*");
        }

        private Expression parsePow(char endSymbol) {
            List<Expression> list = new ArrayList<>();
            list.add(parseExpression());
            skipWhitespace();
            while (!test(endSymbol)) {
                String operator = getOperator();
                if (!operator.equals("^")) {
                    return foldRight(list, "^");
                }
                takeOperator(operator);
                list.add(parseExpression());
                skipWhitespace();
            }
            return foldRight(list, "^");
        }

        private Expression union(Expression left, Expression right, String operator) {
            return OPERATORS_CONSTRUCTORS.get(operator).apply(left, right);
        }

        private Expression parseExpression() {
            skipWhitespace();
            if (take('(')) {
                skipWhitespace();
                Expression expression = parseStart(')');
                take(')');
                return expression;
            } else {
                return new Constant(getConstant());
            }
        }

        private String getOperator() {
            int position = getPos();
            StringBuilder sb = new StringBuilder();
            while (test(c -> c == '+' || c == '*' || c == '^')) {
                sb.append(take());
            }
            setPos(position);
            return sb.toString();
        }

        private void takeOperator(String operation) {
            expect(operation);
            skipWhitespace();
        }

        private Integer getConstant() {
            StringBuilder sb = new StringBuilder();
            while (test(Character::isDigit) || test('w')) {
                char c = take();
                sb.append(c);
                if (c == 'w') {
                    return -1;
                }
            }
            return Integer.parseInt(sb.toString());
        }

    }
}
