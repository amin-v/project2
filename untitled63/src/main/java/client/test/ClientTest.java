package client.test;

import client.thread.ClientThread;

import java.io.IOException;

public class ClientTest {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new ClientThread("src\\main\\resources\\terminal.xml").start();
                    new ClientThread("src\\main\\resources\\terminal.xml").start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
