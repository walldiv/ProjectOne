package com.ex.service;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * This class is responsible for taking in the config variables setup in config.cfg
 * and returning them to useable settings anywhere within code through getters via this
 * class.
 *
 * @param configFile - the array of properties read from Config.cfg
 */
public class ConfigVarsService {
    private Properties configFile;

    public ConfigVarsService() {
        configFile = new Properties();
        /* This is using a filereader to read config.cfg (root level of project) for
        variables that configure the application externally.  This is moved to dockerized
        environment variables because I couldnt figure out how to get the file
        to package with the deployment throuhv Maven::Package lifecycle */
//        try {
            //build a relativefilepath to FileName
//            String root = System.getProperty("user.dir");
//            String FileName="config.cfg";
//            String filePath = root+ File.separator+File.separator+FileName;
//
//            FileInputStream inStream = new FileInputStream(filePath);
//            configFile.load(inStream);

            try {
                FileInputStream inStream = new FileInputStream("config.cfg");
                configFile.load(inStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("ERROR LOADING CONFIG FILE");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERROR LOADING CONFIG FILE");
            }
    }

    /**
     * A getter function to get any key from this service on demand.  Requires a valid
     * ConfigVarsService object to invoke this.
     * @param key - String literal of the keyname found in Config.cfg
     * @return - the value of the key read from file.
     */
    public String getProperty(String key) {
        if(this.configFile != null) {
            String value = this.configFile.getProperty(key);
            if (value != null) {
                System.out.println("GETTING CONFIG FILE VAR");
                return value;
            }
        }
        try {
            Map<String, String> env = System.getenv();
            System.out.println("GETTING SYSTEM ENVIRONMENT VAR");
            return env.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRORORED OUT GETTIN VARTYPE");
            return null;
        }
    }
}
