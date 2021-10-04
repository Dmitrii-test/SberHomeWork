package ru.dmitrii.concurrent.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    CacheType cacheType() default CacheType.IN_MEMORY;
    String fileNamePrefix() default "./map";
    int listList() default  0;
    boolean zip() default false;
    Class<?>[] identityBy() default String.class;
}
