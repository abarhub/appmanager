package org.appmanager.appone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainAppOne {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainAppOne.class);


    public static void main(String[] args) {
        LOGGER.info("app one");
        for(int i=0;i<10;i++){
            System.out.println("value "+(i+1));
        }
    }

}