package org.appmanager.appone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainAppOne {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainAppOne.class);

    public static void main(String[] args) {
        LOGGER.info("app one");
        int max = 10;
        if (args != null && args.length > 0) {
            if (args[0] != null && args[0].length() > 0) {
                max = Integer.parseInt(args[0]);
            }
        }
        for (int i = 0; i < max; i++) {
            System.out.println("value " + (i + 1));
        }
    }

}