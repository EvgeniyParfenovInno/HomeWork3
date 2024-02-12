package ru.demo;

public class Main {

    public static void main(String[] args) {
        Runnable r = () -> {
            Utils.log(Thread.currentThread().getName() + " processing");
            Utils.sleep(1000);
            Utils.log(Thread.currentThread().getName() + " processied");
        };

        var executorService = new OwnExecutorService(4);

        for (int taskId = 0; taskId < 10; taskId++) {
            executorService.execute(r);
        }

        executorService.shutdown();

        try {
            Utils.log("Проверка ошибки добавления задачи");
            executorService.execute(() -> Utils.log("A'm started"));
        } catch (IllegalArgumentException e) {
            Utils.log("Ошибка добавления задачи перехвачена");
        }

        Utils.sleep(2000);

        executorService.awaitTermination();
    }
}
