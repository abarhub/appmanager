package org.appmanager.managerclient.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Config {

    private final Map<String,Object> configuration;

    public Config(Map<String,Object> map){
        configuration=new  ConcurrentHashMap<>();
        configuration.putAll(map);
    }

    public String getString(String key){
        return (String) configuration.get(key);
    }

    public <T> T getString(String key, Class<T> clazz){
        Object value= configuration.get(key);
        if(value==null){
            return null;
        } else {
            return (T)value;
        }
    }

    public Object getObject(String key){
        return configuration.get(key);
    }

    public boolean containsKey(String key){
        return configuration.containsKey(key);
    }

    public List<String> getKeys(){
        Set<String> set= configuration.keySet();
        List<String> liste=new ArrayList<>();
        liste.addAll(set);
        return liste;
    }

}
