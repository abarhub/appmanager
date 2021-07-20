package org.appmanager.managerclient.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHttp implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHttp.class);

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public List<String> sendMessage(String msg) throws IOException {
        out.println(msg);
        out.println(".");
        List<String> list = new ArrayList<>();
        String resp = in.readLine();
        LOGGER.info("resp={}", resp);
        while (resp != null && list.size() < 10) {
            list.add(resp);
            resp = in.readLine();
            LOGGER.info("resp={}", resp);
            if (".".equals(resp)) {
                break;
            }
        }
        LOGGER.info("fin de boucle");
        return list;
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
