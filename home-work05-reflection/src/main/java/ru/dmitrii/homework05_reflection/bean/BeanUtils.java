package ru.dmitrii.homework05_reflection.bean;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanUtils {
    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */
    public static void assign(Object to, Object from) {
        Method[] methodsFrom = from.getClass().getMethods();
        Method[] methodsTo = to.getClass().getMethods();
        for (Method f : methodsFrom) {
            if (f.getName().startsWith("get")) {
                Method t = null;
                try {
                    t = checkTo(methodsTo, f);
                    t.invoke(to, f.invoke(from));
                    System.out.println("Метод отработал");
                } catch (IOException ignored) {
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.out.println("Ошибка вызова метода " + e.getMessage());
                }
            }
        }
    }

    private static Method checkTo(Method[] methodsTo, Method f) throws IOException {

        for (Method t : methodsTo) {
            if (t.getParameterTypes().length != 1) continue;
            if (t.getName().startsWith("set") && t.getName().substring(3).equals(f.getName().substring(3)) &&
                    t.getParameterTypes()[0].isAssignableFrom(f.getReturnType())) {
                return t;
            }
        }
        throw new IOException("Метод не найден");
    }
}
