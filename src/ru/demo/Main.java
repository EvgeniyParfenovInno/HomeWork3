package ru.demo;

public class Main {

    public static void main(String[] args) {
        var executorService = new OwnExecutorService(4);
        executorService.execute(() -> System.out.println("A'm started"));
        executorService.shutdown();
    }
}
