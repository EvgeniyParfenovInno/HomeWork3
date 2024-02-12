package ru.demo;

import java.util.LinkedList;
import java.util.Optional;

public class OwnQueue {

    private final LinkedList<Runnable> tasks = new LinkedList<>();
    private final Object monitor = new Object();

    public void put(Runnable r) {
        synchronized (monitor) {
            tasks.add(r);
            Utils.log("Добавлено задание");
            monitor.notifyAll();
            Utils.log("Пробуждение потоков");
        }
    }

    void clear() {
        Utils.log("Очистка очереди заданий...");
        synchronized (monitor) {
            tasks.clear();
            Utils.log("Очередь очищена");
            monitor.notifyAll();
            Utils.log("Пробуждение потоков");
        }
    }

    public Optional<Runnable> get() {
        return Optional.ofNullable(null);
    }
}
