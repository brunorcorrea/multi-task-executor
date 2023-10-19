package org.example;

public class Task implements Runnable {
    public int id;

    public Task(int id) {
        this.id = id;
    }

    public void run() {
        System.out.println("Task " + id + " is running...");

        var randomNumber1 = Math.random() * 100;
        var randomNumber2 = Math.random() * 100;

        System.out.println("Task " + id + " is calculating " + randomNumber1 + " + " + randomNumber2 + "...");
        System.out.println("Task " + id + " result: " + (randomNumber1 + randomNumber2));

        System.out.println("Task " + id + " finished.");
    }
}
