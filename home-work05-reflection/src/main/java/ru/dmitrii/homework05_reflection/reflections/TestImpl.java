package ru.dmitrii.homework05_reflection.reflections;

public class TestImpl implements Test{
    private int anInt;
    public String string = "What";
    public static final String MONDAY = "MONDAY";
    public static final String TUESDAY  = "TUESDAY";
    public static final String WEDNESDAY = "MONDAY";

    private void test (int i) {}
    public void test2 (String s) {}

    public int getAnInt() {
        return anInt;
    }

    public String getString() {
        return string;
    }

    @Override
    public int parent(int i) {
        return 0;
    }
}
