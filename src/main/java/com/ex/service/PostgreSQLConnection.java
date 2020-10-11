package com.ex.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Vendor specific to PostgreSQL JDBC driver - loads driver on instantiation
 * and sets up default schema
 */
public class PostgreSQLConnection extends ConnectionService {
    static {
        try{
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public PostgreSQLConnection() {
        this.defaultSchema = "public";
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
