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
        Callable<Long> doubleCallable = () -> service.factorial("work", 12);
        try {
            List<Future<Long>> futures = new ArrayList<>();
            for (int i = 0; i <10 ; i++) {
                futures.add(executorService.submit(doubleCallable));
            }
            Thread.sleep(100);
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
