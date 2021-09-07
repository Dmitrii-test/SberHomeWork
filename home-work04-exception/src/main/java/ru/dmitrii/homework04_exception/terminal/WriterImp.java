package ru.dmitrii.homework04_exception.terminal;

public class WriterImp implements Writer {

    @Override
    public void write(String message) {
        System.out.println(message);
    }

}
