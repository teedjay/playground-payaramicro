package com.teedjay.payaramicro.config;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomConfigSource implements ConfigSource {

    @Override
    public Map<String, String> getProperties() {
        return getConfigFromSomewhereCustom();
    }

    @Override
    public Set<String> getPropertyNames() {
        return getConfigFromSomewhereCustom().keySet();
    }

    @Override
    public int getOrdinal() {
        return 200;
    }

    @Override
    public String getValue(String key) {
        return getConfigFromSomewhereCustom().get(key);
    }

    @Override
    public String getName() {
        return "CustomConfigSource";
    }

    private Map<String, String> getConfigFromSomewhereCustom() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("cheese.better", "jarlsberg");
        map.put("cheese.worse", "norwegia");
        return map;
    }

}
