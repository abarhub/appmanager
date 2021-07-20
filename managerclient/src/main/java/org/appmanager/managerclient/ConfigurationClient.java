package org.appmanager.managerclient;

import org.appmanager.managerclient.domain.Config;
import org.appmanager.managerclient.domain.ConfigException;
import org.appmanager.managerclient.service.ConfigFactory;

public class ConfigurationClient {

    public static Config getConfiguration() throws ConfigException {
        return ConfigFactory.get();
    }
}
