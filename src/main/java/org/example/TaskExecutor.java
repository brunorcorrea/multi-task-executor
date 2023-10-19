package org.example;

import java.util.Scanner;
import java.util.Stack;

public class TaskExecutor {
    public static int availableCores = Math.max(Runtime.getRuntime().availableProcessors() / 2, 1);
    public static Scanner scanner = new Scanner(System.in);
    public static int tasksCreated = 0;
    public static long tasksRunning = 0;
    public static long tasksFinished = 0;
    public static long startTime = System.currentTimeMillis();
    public static Stack<Thread> threads = new Stack<>();

    public static void main(String[] args) {
        showCPUInfo();

        System.out.println("Type the amount of tasks to run: ");
        tasksCreated = scanner.nextInt();

        System.out.println("Creating and running tasks...");
        createThreads(tasksCreated);
        runThreads();

        System.out.println("All tasks finished.");
    }

    public static void createThreads(int amount) {
        new Thread(() -> {
            for (int taskId = 1; taskId <= amount; taskId++) {
                threads.add(new Thread(new Task(taskId)));
            }
        }).start();
    }

    public static void runThreads() {
        while (tasksFinished != tasksCreated) {
            if (!threads.isEmpty() && tasksRunning < availableCores) {
                var thread = threads.pop();
                thread.start();
            }
        }
        showElapsedTime(startTime, System.currentTimeMillis());
    }

    public static void showCPUInfo() {
        System.out.println("The computer CPU has " + Runtime.getRuntime().availableProcessors() + " cores, half of them or at least 1 will be used to this program.");
    }

    public static void showElapsedTime(long startTime, long endTime) {
        long minutes = (endTime - startTime) / 1000 / 60;
        long seconds = (endTime - startTime) / 1000 % 60;
        long milliseconds = (endTime - startTime) % 1000;
        System.out.println("Elapsed time: " + minutes + "m " + seconds + "s " + milliseconds + "ms.");
    }
}
