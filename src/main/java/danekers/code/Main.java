package danekers.code;

import danekers.code.DANEKER.Application;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Application application = new Application();
        application.start();
    }
}