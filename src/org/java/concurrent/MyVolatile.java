package org.java.concurrent;

public class MyVolatile extends Thread {

    private static volatile boolean flag = true;

    public void run() {
        while (flag) {

        }
    }

    public static void main(String[] args) throws Exception {
        new MyVolatile().start();
        Thread.sleep(1000);
        flag = false;
    }


}
