package ru.demo;

public class Main {

    public static void main(String[] args) {
        var executorService = new CustomExecutorService(4);

        for (int taskId = 0; taskId < 20; taskId++) {
            int i = taskId;
            executorService.execute(() -> {
                Utils.log(Thread.currentThread().getName() + ": выполняется задание #" + i);
                Utils.sleep(2000);
                Utils.log(Thread.currentThread().getName() + ": выполнено задание #" + i);
            });
        }

        Utils.sleep(1500);
        executorService.shutdown();
        Utils.sleep(2500);

        try {
            Utils.log("Проверка ошибки добавления задачи");
            executorService.execute(() -> Utils.log("Новое задание"));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Utils.log("Ошибка добавления задачи перехвачена");
        }

        Utils.sleep(2500);

        executorService.awaitTermination();
    }
}
