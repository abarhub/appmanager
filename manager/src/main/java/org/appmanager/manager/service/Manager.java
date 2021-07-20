package org.appmanager.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(Manager.class);

    // cf org.appmanager.managerclient.service.ConfigFactory.CONFIG_ENV
    public static String CONFIG_ENV = "CONFIG_ENV";
    public static final String HTTP = "http://";

    private ServerConfig serverConfig = new ServerConfig();

    public void run() {
        Menu menu = new Menu();
        while (true) {
            int choice = menu.menu();
            if (choice == Menu.CODE_EXIT) {
                return;
            } else if (choice == Menu.CODE_APP_ONE) {
                runApp();
            }
        }
    }

    private void runApp() {
        LOGGER.info("run app one");
        RunApp runApp = new RunApp();
        try {
            String param = runServer();
            //runApp.run(List.of("echo","aaa"));
            runApp.run(List.of("java", "-D" + CONFIG_ENV + "=" + HTTP + param,
                    "-jar", "./appone/target/appone-1.0.0-SNAPSHOT.jar"));
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Erreur", e);
        }
    }

    private String runServer() {
        return serverConfig.getServer();
    }
}
