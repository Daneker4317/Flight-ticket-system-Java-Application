package danekers.code.DB.connection;

import danekers.code.DB.connection.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class PSQL implements database {
    @Override
    public Connection getConnection() {
            String connectionUrl = "jdbc:postgresql://localhost:5432/flight";
            try {
                Class.forName("org.postgresql.Driver");

                Connection con = DriverManager.getConnection(connectionUrl, "postgres", "qwerty");

                return con;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
    }
}
