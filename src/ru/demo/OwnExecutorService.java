package ru.demo;

import java.util.LinkedList;

public class OwnExecutorService {

    private final Thread[] threads;
    private final LinkedList<Runnable> tasks = new LinkedList<>();

    private final Object taskMonitor = new Object();
    private boolean isTasksClosed;

    public OwnExecutorService(int poolSize) {
        if (poolSize < 1)
            throw new IllegalArgumentException("Количество потоков должно быть больше 0");

        threads = new Thread[poolSize];
        for(int threadIndex = 0; threadIndex < poolSize; threadIndex++) {
            threads[threadIndex] = new Thread(() -> System.out.printf("Поток %s запущен\n", Thread.currentThread().getName()));
            threads[threadIndex].start();
        }
    }

    public void execute(Runnable r) {
        if (r == null)
            throw new IllegalArgumentException("Задача не должна быть null");

        synchronized (taskMonitor) {
            if (isTasksClosed)
                throw new IllegalArgumentException("Пул закрыт для добавления новых задач");
            System.out.printf("В очередь добавлена задача %s\n", r);
            tasks.add(r);
        }
    }

    public void shutdown() {
        synchronized (taskMonitor) {
            isTasksClosed = true;
        }
    }
}
