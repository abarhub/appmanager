package org.appmanager.manager;

import org.appmanager.manager.service.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainManager.class);

    public static void main(String[] args) {
        LOGGER.info("manager");
        Manager manager=new Manager();
        manager.run();

    }

}