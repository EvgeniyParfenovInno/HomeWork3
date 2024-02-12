package ru.demo;

public class CustomThread extends Thread {

    private final CustomExecutorService service;

    public CustomThread(CustomExecutorService service) {
        this.service = service;
    }

    @Override
    public void run() {
        Utils.log(Thread.currentThread().getName() + ": поток запущен");
        while (true) {
            var r = service.take();
            if (r == null) {
                Utils.log(Thread.currentThread().getName() + ": поток завершился");
                break;
            }
            r.run();
        }
    }
}
