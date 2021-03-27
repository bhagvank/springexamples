package org.springexamples.streaming.simulator;
import java.io.*; 
import java.net.*;
import java.util.Random;
import java.util.concurrent.*;

public class EventCreationSimulator {
    private static final Executor SERVER_EXECUTOR = Executors.newSingleThreadExecutor();
    private static final int PORT = 8888;
    private static final String DELIMITER = "-";
    private static final long EVENT_PERIOD_SECONDS = 1;
    private static final Random random = new Random();

    public static void main(String[] args) throws IOException, InterruptedException {
        BlockingQueue<String> eventQueue = new ArrayBlockingQueue<>(100);
        SERVER_EXECUTOR.execute(new EventStreamingServer(eventQueue));
        while (true) {
            eventQueue.put(createEvent());
            Thread.sleep(TimeUnit.SECONDS.toMillis(EVENT_PERIOD_SECONDS));
        }
    }

    private static String createEvent() {
        int customerNumber = random.nextInt(20);
        String event = random.nextBoolean() ? "mobile" : "laptop";
        return String.format("customer-%s", customerNumber) + DELIMITER + event;
    }

    private static class EventStreamingServer implements Runnable {
        private final BlockingQueue<String> eventQueue;

        public EventStreamingServer(BlockingQueue<String> eventQueue) {
            this.eventQueue = eventQueue;
        }

        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(PORT);
                 Socket clientSocket = serverSocket.accept();
                 PrintWriter outWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            ) {
                while (true) {
                    String event = eventQueue.take();
                    System.out.println(String.format("outputing \"%s\" to the socket.", event));
                    outWriter.println(event);
                }
            } catch (IOException|InterruptedException exception) {
                throw new RuntimeException("Run Time error", exception);
            }
        }
    }
}