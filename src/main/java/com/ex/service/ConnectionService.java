package com.ex.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * This class is an abstraction for multiple vendor types to establish the connection
 * properties for JDBC connectivity to vendor-specific implementations.  This class
 * when instantiated will establish the connection parameters for the JDBC - pulled from
 * Environment Variables established in the user's computer.
 ***************   SEE EXTERNAL DOCS FOR CONFIGURATION SETUP OF INSTALLATION   *********
 */
public abstract class ConnectionService {
    protected String url;
    protected String username;
    protected String password;
    protected String defaultSchema;

    public ConnectionService() {
        getDatabaseEnvVars();
    }

    /* RETURN CONNECTION FOR USE IN EACH SCREEN TO HOUSE ISOLATED TRANSACTIONS */
    public abstract Connection getConnection() throws SQLException;

    public String getDefaultSchema() {
        return defaultSchema;
    }

    //Environment variables for connection
    public void getDatabaseEnvVars() {
        Map<String, String> env = System.getenv();
//        for (String envName : env.keySet()) {
//            System.out.format("%s=%s%n \n",
//                    envName,
//                    env.get(envName));
//        }
        username = env.get("Project1_UName");
        password = env.get("Project1_UPassword");
        url = env.get("Project1_URL");
//        System.out.printf("URL: %s \n USER: %s \n PASS: %s\n", url, username, password );
    }

}
