package ru.dmitrii.spring.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * Класс обертка стрима
 */
public class LimitInputStream extends InputStream {

    private final InputStream inputStream;
    private final Limiter limiter;

    public LimitInputStream(InputStream inputStream, Limiter limiter) {
        this.inputStream = inputStream;
        this.limiter = limiter;
    }

    @Override
    public int read() throws IOException {
        if (limiter != null) {
            limiter.limitNextBytes();
        }
        return inputStream.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (limiter != null) {
            limiter.limitNextBytes(len);
        }
        return inputStream.read(b, off, len);
    }

    @Override
    public int read(byte[] b) throws IOException {
        if (limiter != null && b.length > 0) {
            limiter.limitNextBytes(b.length);
        }
        return inputStream.read(b);
    }
}