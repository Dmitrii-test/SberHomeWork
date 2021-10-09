package ru.dmitrii.jdbc.calc;

public class Fibonacy {
    private int id;
    private int result;

    public Fibonacy(int id, int result) {
        this.id = id;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
