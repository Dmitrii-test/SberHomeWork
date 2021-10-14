package ru.dmitrii.spring.streams;

import org.springframework.stereotype.Component;

public class Limiter {
    private static final Long KB = 1024L;
    private static final Long CHUNK_LENGTH = 1024 * 1024L;
    private int bytesWillBeSentOrReceive = 0;
    private long lastPieceSentOrReceiveTick = System.nanoTime();
    private int maxRate = 1024;
    private long timeCostPerChunk = (1000000000L * CHUNK_LENGTH) / (this.maxRate * KB);

    public Limiter(int maxRate) {
        this.setMaxRate(maxRate);
    }


    public void setMaxRate(int maxRate) {
        if (maxRate < 0) {
            throw new IllegalArgumentException("maxRate can not less than 0");
        }
        this.maxRate = maxRate;
        if (maxRate == 0) {
            this.timeCostPerChunk = 0;
        } else {
            this.timeCostPerChunk = (1000000000L * CHUNK_LENGTH) / (this.maxRate * KB);
        }
    }

    public synchronized void limitNextBytes() {
        this.limitNextBytes(1);
    }

    public synchronized void limitNextBytes(int len) {
        this.bytesWillBeSentOrReceive += len;
        while (this.bytesWillBeSentOrReceive > CHUNK_LENGTH) {
            long nowTick = System.nanoTime();
            long passTime = nowTick - this.lastPieceSentOrReceiveTick;
            long missedTime = this.timeCostPerChunk - passTime;
            if (missedTime > 0) {
                try {
                    Thread.sleep(missedTime / 1000000, (int) (missedTime % 1000000));
                } catch (InterruptedException e) {
                    System.out.println("Ошибка ожыдания");
                }
            }
            this.bytesWillBeSentOrReceive -= CHUNK_LENGTH;
            this.lastPieceSentOrReceiveTick = nowTick + (missedTime > 0 ? missedTime : 0);
        }
    }
}

