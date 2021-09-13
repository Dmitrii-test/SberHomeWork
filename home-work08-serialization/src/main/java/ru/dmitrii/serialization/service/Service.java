package ru.dmitrii.serialization.service;

import ru.dmitrii.serialization.proxy.Cache;
import ru.dmitrii.serialization.proxy.CacheType;

import java.util.Date;
import java.util.List;

public interface Service {

    @Cache(cacheType = CacheType.FILE, fileNamePrefix = "./data", zip = true, identityBy = {String.class, Integer.class})
    double doHardWork(String string, int i);

    @Cache(cacheType = CacheType.FILE, fileNamePrefix = "./data", zip = true, identityBy = {String.class, double.class})
    List<String> run(String item, double value, Date date);

    @Cache(cacheType = CacheType.IN_MEMORY, listList = 100_000)
    List<String> work(String item);
}
