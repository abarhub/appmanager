package org.appmanager.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConfig implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfig.class);

    private String serveurHost = null;
    private int serveurPort = 0;

    @Override
    public void run() {
        try {
            LOGGER.info("start server ...");
            var serverSocket = new ServerSocket(serveurPort);
            var clientSocket = serverSocket.accept();
            new Thread(new ServerReponse(clientSocket)).start();
        } catch (Exception e) {
            LOGGER.error("Erreur", e);
        }
    }

    public synchronized String getServer() {
        if (serveurHost == null) {
            serveurHost = "localhost";
            serveurPort = 8087;
            startServer();
        }
        return serveurHost + ":" + serveurPort;
    }

    private void startServer() {
        new Thread(this).start();
    }

    private class ServerReponse implements Runnable {

        private Socket clientSocket;

        public ServerReponse(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                LOGGER.info("start server ok");
                var out = new PrintWriter(clientSocket.getOutputStream(), true);
                var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                var fin = false;
                while (!fin) {
                    String greeting = in.readLine();
                    LOGGER.info("get {}", greeting);
                    if ("hello".equals(greeting)) {
                        out.println("hello client");
                        out.println("hello client2");
                        LOGGER.info("send response ok");
                    } else if(".".equals(greeting)){
                        out.println(".");
                        fin = true;
                    } else {
                        out.println("unrecognised greeting");
                    }
                }
                LOGGER.info("fin boucle");
            } catch (Exception e) {
                LOGGER.error("Erreur", e);
            }
        }
    }
}
