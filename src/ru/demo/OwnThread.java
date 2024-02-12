package ru.demo;

public class OwnThread extends Thread {

    private boolean isbreak;
    private final OwnQueue queue;

    public OwnThread(OwnQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Utils.log(Thread.currentThread().getName() + " running");
        /*
        try {
            while (true) {
                if (currentThread().isInterrupted() || isbreak) {
                    break;
                }


            }
        } catch (InterruptedException e) {
            isbreak = true;
        }
         */
    }
}
