package org.appmanager.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Menu {

    private static final Logger LOGGER = LoggerFactory.getLogger(Menu.class);

    public static int CODE_EXIT = -1;
    public static int CODE_APP_ONE = 1;

    public int menu() {
        System.out.println("Menu:");
        System.out.println("1) exit");
        System.out.println("2) run app 1");
        Scanner sc = new Scanner(System.in);
        boolean end = false;
        int result = 0;
        while (!end) {
            try {
                int i = sc.nextInt();
                if (i == 1) {
                    end = true;
                    result = CODE_EXIT;
                } else if (i == 2) {
                    end = true;
                    result = CODE_APP_ONE;
                }
            } catch (Exception e) {
                LOGGER.error("Error pour data: {}", e.getMessage());
            }
        }
        return result;
    }

}
