package ru.dmitrii.plugins;

import java.net.URL;
import java.net.URLClassLoader;

public class MyLoader extends URLClassLoader {
    public MyLoader(URL[] urls) {
        super(urls);
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // First check if we have permission to access the package. This
        // should go away once we've added support for exported packages.
        Class<?> c = findLoadedClass(name);
        try {
            if (c == null) c = findClass(name);
        } catch (ClassNotFoundException ignored) {}
        if (c == null) c = super.findSystemClass(name);
        if (c == null) c = getParent().loadClass(name);
        return c;
    }
}
