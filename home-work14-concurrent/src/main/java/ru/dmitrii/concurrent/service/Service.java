package ru.dmitrii.concurrent.service;

import ru.dmitrii.concurrent.proxy.Cache;
import ru.dmitrii.concurrent.proxy.CacheType;

import java.util.Date;
import java.util.List;

public interface Service {

    @Cache(cacheType = CacheType.IN_MEMORY)
    Long factorial(String string, int i);

    @Cache(cacheType = CacheType.FILE, fileNamePrefix = "./data", zip = false, identityBy = {String.class, double.class})
    List<String> run(String item, double value, Date date);

    @Cache(cacheType = CacheType.IN_MEMORY, listList = 100_000)
    List<String> work(String item);
}
