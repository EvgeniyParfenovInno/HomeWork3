package ru.demo;

import java.util.concurrent.atomic.AtomicBoolean;

public class OwnExecutorService {

    private final OwnThread[] threads;
    private final OwnQueue queue;

    private AtomicBoolean isShutDown;

    public OwnExecutorService(int poolSize) {
        if (poolSize < 1)
            throw new IllegalArgumentException("Размер пула потоков должно быть больше нуля");

        queue = new OwnQueue();
        isShutDown = new AtomicBoolean(false);

        threads = new OwnThread[poolSize];
        for (int threadIndex = 0; threadIndex < poolSize; threadIndex++) {
            threads[threadIndex] = new OwnThread(queue);
            threads[threadIndex].start();
        }
    }

    public void execute(Runnable r) {
        if (r == null)
            throw new NullPointerException("Недопустимое значение");

        if (isShutDown.get())
            throw new IllegalArgumentException("Запрет на добавление новых задач");

        queue.put(r);
    }

    public void shutdown() {
        isShutDown = new AtomicBoolean(true);
        Utils.log("Включен запрет на добавление новых заданий");
    }

    public void awaitTermination() {
        Utils.log("Завершение работы пула потоков...");
        if (!isShutDown.get())
            shutdown();

        queue.clear();
        Utils.log("Завершение работы пула потоков выполнено");
    }
}
