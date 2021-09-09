package ru.dmitrii.plugins;

public class Main {
    public static void main(String[] args) {
        PluginManager manager = new PluginManager("./home-work07-classloaders/Plugins");
        manager.start();
    }
}
