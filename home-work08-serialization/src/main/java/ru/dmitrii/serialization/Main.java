package ru.dmitrii.serialization;

import ru.dmitrii.serialization.proxy.CacheProxy;
import ru.dmitrii.serialization.service.Service;
import ru.dmitrii.serialization.service.ServiceImpl;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        CacheProxy cacheProxy = new CacheProxy();
        Service service = (Service) cacheProxy.cache(new ServiceImpl());
        System.out.println(service.run("work1",123, new Date()));
        System.out.println(service.run("work1",123, new Date()));
        System.out.println(service.run("work1",123, new Date()));
        System.out.println(service.doHardWork("work", 10));
        System.out.println(service.doHardWork("work", 10));
        System.out.println(service.doHardWork("work", 10));
        System.out.println(service.work("work"));
        System.out.println(service.work("work"));
        System.out.println(service.work("work"));
        System.out.println(service.work("work"));


    }
}
