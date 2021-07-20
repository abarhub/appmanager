package org.appmanager.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(Manager.class);

    public void run() {
        Menu menu = new Menu();
        while (true) {
            int choice = menu.menu();
            if (choice == Menu.CODE_EXIT) {
                return;
            } else if (choice == Menu.CODE_APP_ONE) {
                LOGGER.info("run app one");
                RunApp runApp=new RunApp();
                try {
                    runApp.run(List.of("echo","aaa"));
                } catch (IOException | InterruptedException e) {
                    LOGGER.error("Erreur", e);
                }
            }
        }
    }
}
