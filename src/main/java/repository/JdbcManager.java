package repository;

import lombok.SneakyThrows;
import java.sql.*;

public class JdbcManager  {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres" ;
    private static final String USER = "postgres";
    private static final String PASS = "3757";
    private JdbcManager() {
    }
    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}