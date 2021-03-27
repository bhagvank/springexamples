package org.springexamples.streaming.app;
import org.apache.log4j.*;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.*;

public class BasicStreamingSparkApplication {
    private static final String HOST = "localhost";
    private static final int PORT = 8888;

    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf()
                .setAppName("BasicStreamingSparkApp");
        JavaStreamingContext streamingContext =
                new JavaStreamingContext(conf, Durations.seconds(10));
        Logger.getRootLogger().setLevel(Level.ERROR);

        JavaReceiverInputDStream<String> lines = streamingContext.socketTextStream(HOST, PORT);
        lines.print();

        streamingContext.start();
        streamingContext.awaitTermination();
    }
}
