package org.example;

import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private static final Properties PROPS = new Properties();

    static {
        try {
            InputStream input = AppConfig.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties");
            if (input == null) {
                throw new RuntimeException("Application properties file not found");
            }
            PROPS.load(input);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String get(String key) {
        return PROPS.getProperty(key);
    }
    public static Double getDouble(String key) {
        return Double.parseDouble(PROPS.getProperty(key));
    }
    public static Integer getInteger(String key) {
        return Integer.parseInt(PROPS.getProperty(key));
    }
}
