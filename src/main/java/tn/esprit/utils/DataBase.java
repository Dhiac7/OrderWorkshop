package tn.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static final String URL  = getenv("DB_URL",  "jdbc:mysql://mysql-service:3306/javafxdb");
    private static final String USER = getenv("DB_USER", "javafxuser");
    private static final String PASS = getenv("DB_PASS", "javafxpass");

    private static String getenv(String k, String def) {
        String v = System.getenv(k);
        return (v == null || v.isEmpty()) ? def : v;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // If you want to keep your bootstrap method:
    // public static void initTables() { ... use getConnection() ... }
}

