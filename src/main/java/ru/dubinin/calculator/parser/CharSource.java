package ru.dubinin.calculator.parser;

interface CharSource {
    boolean hasNext();

    char next();

    void setPos(int pos);

    int getPos();

    IllegalArgumentException error(String message);
}
