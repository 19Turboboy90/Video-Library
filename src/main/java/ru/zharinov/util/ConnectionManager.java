package ru.zharinov.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;

@UtilityClass
public class ConnectionManager {
    private static final String URL_KEY = "bd.url";
    private static final String USERNAME_KEY = "bd.username";
    private static final String PASSWORD_KEY = "bd.password";
    private static final String DATABASE_DRIVER = "bd.driver";

    static {
        loadDriver();
    }

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(
                PropertiesUtil.getProperties(URL_KEY),
                PropertiesUtil.getProperties(USERNAME_KEY),
                PropertiesUtil.getProperties(PASSWORD_KEY));
    }

    private void loadDriver() {
        try {
            Class.forName(PropertiesUtil.getProperties(DATABASE_DRIVER));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
