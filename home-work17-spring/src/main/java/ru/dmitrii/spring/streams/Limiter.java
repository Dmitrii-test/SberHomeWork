package ru.dmitrii.spring.streams;

/**
 * Класс ограничитель скачивания
 */
public class Limiter {
    private static final Long KB = 1024L;
    private static final Long CHUNK_LENGTH = 1024 * 1024L;
    private int bytesBeSent = 0;
    private long lastPiece = System.nanoTime();
    private int maxRate = 1024;
    private long timeCostChunk = (1000000000L * CHUNK_LENGTH) / (this.maxRate * KB);

    public Limiter(int maxRate) {
        this.setMaxRate(maxRate);
    }

    /**
     * Задать ограничение
     * @param maxCapacity int
     */
    public void setMaxRate(int maxCapacity) {
        if (maxCapacity < 0) {
            throw new IllegalArgumentException("maxCapacity can not less than 0");
        }
        this.maxRate = maxCapacity;
        if (maxCapacity == 0) {
            this.timeCostChunk = 0;
        } else {
            this.timeCostChunk = (1000000000L * CHUNK_LENGTH) / (this.maxRate * KB);
        }
    }

    public synchronized void limitNextBytes() {
        this.limitNextBytes(1);
    }

    public synchronized void limitNextBytes(int len) {
        this.bytesBeSent += len;
        while (this.bytesBeSent > CHUNK_LENGTH) {
            long nowTick = System.nanoTime();
            long passTime = nowTick - this.lastPiece;
            long missedTime = this.timeCostChunk - passTime;
            if (missedTime > 0) {
                try {
                    Thread.sleep(missedTime / 1000000, (int) (missedTime % 1000000));
                } catch (InterruptedException e) {
                    System.out.println("Ошибка ожидания");
                }
            }
            this.bytesBeSent -= CHUNK_LENGTH;
            this.lastPiece = nowTick + (missedTime > 0 ? missedTime : 0);
        }
    }
}

