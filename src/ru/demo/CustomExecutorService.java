package ru.demo;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomExecutorService {

    private final LinkedList<Runnable> tasks = new LinkedList<>();
    private final Object taskMonitor = new Object();

    private final CustomThread[] threads;
    private AtomicBoolean isShutDown;
    private AtomicBoolean isTerimanated;

    public CustomExecutorService(int poolSize) {
        if (poolSize < 1)
            throw new IllegalArgumentException("Размер пула потоков должно быть больше нуля");

        isShutDown = new AtomicBoolean(false);
        isTerimanated = new AtomicBoolean(false);

        threads = new CustomThread[poolSize];
        for (int threadIndex = 0; threadIndex < poolSize; threadIndex++) {
            threads[threadIndex] = new CustomThread(this);
            threads[threadIndex].start();
        }
    }

    public void execute(Runnable r) {
        if (r == null)
            throw new NullPointerException("Недопустимое значение");

        if (isShutDown.get())
            throw new IllegalArgumentException("Запрет на добавление новых задач");

        synchronized (taskMonitor) {
            tasks.add(r);
            Utils.log("Добавлено задание");
            taskMonitor.notifyAll();
        }
    }

    public Runnable take() {
        if (isTerimanated.get()) return null;

        synchronized (taskMonitor) {
            while (tasks.isEmpty()) {
                try {
                    if (isShutDown.get()) return null;

                    Utils.log(Thread.currentThread().getName() + ": сплю...");
                    taskMonitor.wait();
                    Utils.log(Thread.currentThread().getName() + ": проснулся");
                } catch (InterruptedException e) {
                    Utils.log(Thread.currentThread().getName() + ": interrupted");
                    Thread.currentThread().interrupt();
                }
            }
            return tasks.removeFirst();
        }
    }

    public void shutdown() {
        isShutDown = new AtomicBoolean(true);
        Utils.log("******************************************");
        Utils.log("Включен запрет на добавление новых заданий");
    }

    public void awaitTermination() {
        if (!isShutDown.get()) shutdown();
        isTerimanated = new AtomicBoolean(true);

        Utils.log("******************************************");
        Utils.log("Завершение работы пула потоков...");
        if (!isShutDown.get())
            shutdown();

        Utils.log("Очистка очереди заданий...");
        synchronized (taskMonitor) {
            Utils.log("В очереди заданий: " + tasks.size());
            tasks.clear();
            Utils.log("Очередь очищена");
            taskMonitor.notifyAll();
        }

        Utils.log("Завершение работы пула потоков выполнено");
    }
}
