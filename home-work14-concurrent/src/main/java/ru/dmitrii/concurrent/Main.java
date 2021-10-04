package ru.dmitrii.concurrent;

import ru.dmitrii.concurrent.proxy.CacheProxy;
import ru.dmitrii.concurrent.service.Service;
import ru.dmitrii.concurrent.service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        CacheProxy cacheProxy = new CacheProxy();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Service service = (Service) cacheProxy.cache(new ServiceImpl());
        try {
            List<Future<Long>> futures = new ArrayList<>();
            for (int i = 1; i <15 ; i++) {
                int finalI = i;
                futures.add(executorService.submit(() -> service.factorial("work", finalI)));
                futures.add(executorService.submit(() -> service.factorial("work", finalI)));
                futures.add(executorService.submit(() -> service.factorial("work", finalI)));
                futures.add(executorService.submit(() -> service.factorial("work", finalI)));
                futures.add(executorService.submit(() -> service.factorial("work", finalI)));
            }
            Thread.sleep(50);
            futures.stream().filter(Future::isDone).forEach(n -> {
                try {
                    Long aLong = n.get();
                    System.out.println("Результат " + aLong);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------------------------");
        executorService.shutdown();
    }
}
