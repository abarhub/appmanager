package org.appmanager.managerclient.service;

import org.appmanager.managerclient.domain.Config;
import org.appmanager.managerclient.domain.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFactory.class);

    public static final String HTTP = "http://";
    public static String CONFIG_ENV = "CONFIG_ENV";

    private static Config config;

    private static Config getFactory() throws ConfigException {
        String configEnv = System.getProperty(CONFIG_ENV);
        LOGGER.info("configEnv={}",configEnv);
        if (configEnv != null && configEnv.length() > 0) {
            if(configEnv.startsWith(HTTP)) {
                Map<String, Object> map = readServer(configEnv);
                return new Config(map);
            } else {
                throw new ConfigException("ENV "+CONFIG_ENV+" invalid");
            }
        } else {
            return new Config(new HashMap<>());
        }
    }

    private static Map<String, Object> readServer(String configEnv) throws ConfigException {
        LOGGER.info("readServer ...");
        var hostPort=configEnv.substring(HTTP.length());
        if(hostPort.contains("/")){
            throw new ConfigException("ENV "+CONFIG_ENV+" invalid");
        }
        String host;
        int port;
        int index=hostPort.indexOf(':');
        if(index>=0){
            host=hostPort.substring(0,index);
            port=Integer.parseInt(hostPort.substring(index+1));
        } else {
            throw new ConfigException("ENV "+CONFIG_ENV+" invalid");
        }
        LOGGER.info("connect to {}:{}",host,port);
        try(ClientHttp clientSocket = new ClientHttp()) {
            LOGGER.info("start connect ...");
            clientSocket.startConnection(host,port);
            LOGGER.info("send hello ...");
            List<String> listResponses=clientSocket.sendMessage("hello");
            LOGGER.info("reponses: "+listResponses);

            return new HashMap<>();

        } catch (IOException e) {
            throw new ConfigException("Error for read serveur "+host+":"+port,e);
        }
    }

    public static synchronized Config get() throws ConfigException {
        if (config == null) {
            config = getFactory();
        }
        return config;
    }

}
