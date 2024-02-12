package ru.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void log(String msg) {
        System.out.println(new SimpleDateFormat("HH:mm:ss.SSS ").format(new Date()) + msg);
    }

    private Utils() {

    }
}
