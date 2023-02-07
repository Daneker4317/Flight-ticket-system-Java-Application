package danekers.code.DB.data.service;

import java.sql.SQLException;
import java.util.List;

public interface Repository {
    void add() throws SQLException;
    void delete();
    void update();
    List<Object> getAll();
}
