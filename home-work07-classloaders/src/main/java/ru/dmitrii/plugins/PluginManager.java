package ru.dmitrii.plugins;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class PluginManager {
    private final String pluginRootDirectory;
    private final List<Plugin> pluginList = new ArrayList<>();

    public PluginManager(String pluginRootDirectory) {
        if (pluginRootDirectory == null) throw new NullPointerException("Пустрой путь");
        this.pluginRootDirectory = pluginRootDirectory;
    }


    /**
     * Метод загружающий классы из рут папки
     * @param pluginDir String
     * @return Plugin
     */
    public Plugin load(File pluginDir) {
        Class<?> aClass = null;
        Constructor<?> constructor = null;
        Plugin plugin = null;
        MyLoader loader = null;
        try {
            loader = new MyLoader(new URL[]{pluginDir.getParentFile().toURI().toURL()});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            String name = pluginDir.getName().substring(0, pluginDir.getName().length() - 6);
            aClass = Objects.requireNonNull(loader).loadClass(name);
        } catch (ClassNotFoundException e) {
            System.out.println("Класс не найден " + e.getMessage());
        }
        try {
            constructor = Objects.requireNonNull(aClass).getConstructor();
        } catch (NoSuchMethodException e) {
            System.out.println("Не обнаружен метод" + e.getMessage());
        }
        try {
            plugin = (Plugin) Objects.requireNonNull(constructor).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Ошибка создания обьекта класса" + e.getMessage());
        }
        if (plugin != null) {
            System.out.println(plugin + " Создан " + plugin.getClass().getClassLoader());
            return plugin;
        } else throw new NullPointerException("Плугин пустой");
    }

    /**
     * Метод заполнения листа плугинами
     *
     * @param dirs List<File>
     */
    private void getPlugins(List<File> dirs) {
        dirs.forEach(n ->
                pluginList.add(load(n)));
    }

    /**
     * Метод поиска файлов плагинов
     *
     * @return List<File>
     */
    private List<File> getDirs() {
        File path = new File(pluginRootDirectory);
        List<File> result = new ArrayList<>();
        File[] elements = new File(pluginRootDirectory).listFiles();
        if (path.isDirectory() && elements != null) {
            Queue<File> fileTree = new PriorityQueue<>();
            Collections.addAll(fileTree, elements);
            while (!fileTree.isEmpty()) {
                File currentFile = fileTree.remove();
                if (currentFile.isDirectory()) {
                    Collections.addAll(fileTree, Objects.requireNonNull(currentFile.listFiles()));
                } else if (currentFile.getName().endsWith(".class"))
                    result.add(currentFile);
            }
        }
        return result;
    }

    /**
     * Метод запуска менеджера плагинов
     */
    public void start() {
        getPlugins(getDirs());
        pluginList.forEach(Plugin::doUsefull);
        System.out.println("Загрузка плагинов выполнена");
    }
}
