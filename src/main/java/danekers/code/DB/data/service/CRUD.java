package danekers.code.DB.data.service;

import danekers.code.models.User;

import java.sql.SQLException;
import java.util.List;

public interface CRUD {
    List<User> getAll() throws SQLException;
    void add() throws SQLException;
    void delete() throws SQLException;
    void update() throws SQLException;
}
