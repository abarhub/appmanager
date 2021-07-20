package org.appmanager.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class RunApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunApp.class);

    public int run(List<String> app) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");
        List<String> liste=new ArrayList<>();
        if (isWindows) {
            liste.add("cmd.exe");
            liste.add("/c");
        } else {
            liste.add("sh");
            liste.add("-c");
        }
        liste.addAll(app);
        builder.command(liste);
        //builder.directory(new File(System.getProperty("user.home")));
        Process process = builder.start();
        var tmp=Executors.newCachedThreadPool();
        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), x->LOGGER.info("out: {}",x));
        tmp.submit(streamGobbler);
        StreamGobbler streamGobblerError = new StreamGobbler(process.getErrorStream(), x->LOGGER.info("err: {}",x));
        tmp.submit(streamGobblerError);
        int exitCode = process.waitFor();
        return exitCode;
    }

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }

}
