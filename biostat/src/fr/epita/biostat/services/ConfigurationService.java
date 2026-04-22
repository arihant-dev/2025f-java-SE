package fr.epita.biostat.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationService {


    private final Properties properties;

    private static ConfigurationService instance;

    public static ConfigurationService getInstance() {
        if (instance == null){
            try {
                instance = new ConfigurationService();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }


    private ConfigurationService() throws IOException {
        this.properties = new Properties();
        properties.load(new FileInputStream(System.getProperty("conf.file.path","conf.properties")));

    }

    public String getConfValue(String key){
        return this.properties.getProperty(key);
    }


}
