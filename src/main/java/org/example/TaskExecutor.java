package org.example;

import java.util.Scanner;
import java.util.Stack;

public class TaskExecutor {
    public static int availableCores = Math.max(Runtime.getRuntime().availableProcessors() / 2, 1);
    public static Scanner scanner = new Scanner(System.in);
    public static Stack<Task> tasks = new Stack<>();
    public static Stack<Thread> threads = new Stack<>();

    public static void main(String[] args) {
        showCPUInfo();

        System.out.println("Type the amount of tasks to run: ");
        long amount = scanner.nextLong();

        long startTime = System.currentTimeMillis();
        System.out.println("Creating tasks...");

        for (int i = 0; i < amount; i++) {
            tasks.add(new Task(i));
        }

        System.out.println("Created successfully " + tasks.size() + " tasks.");
        System.out.println("Running tasks...");
        runAllTasks();
        System.out.println("All tasks finished.");

        showElapsedTime(startTime, System.currentTimeMillis());
    }

    public static void runAllTasks() {
        int threadsRunning = 0;

        while (!tasks.isEmpty()) {
            for (Thread thread : threads) {
                if (thread.getState() == Thread.State.TERMINATED) {
                    threads.remove(thread);
                    threadsRunning--;
                    break;
                }
            }

            if (threadsRunning < availableCores) {
                var task = tasks.pop();

                var thread = new Thread(task);
                threads.add(thread);
                threadsRunning++;
                thread.start();
            }
        }
    }

    public static void showCPUInfo() {
        System.out.println("The computer CPU has " + Runtime.getRuntime().availableProcessors() + " cores, half of them or at least 1 will be used to this program.");
    }

    public static void showElapsedTime(long startTime, long endTime) {
        System.out.println("Elapsed time: " + (endTime - startTime) / 1000 + "s");
    }
}
