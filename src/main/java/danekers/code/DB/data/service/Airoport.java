package danekers.code.DB.data.service;

import java.sql.SQLException;

public interface Airoport {
    void buyTicket() throws SQLException;
    void returnTicket() throws SQLException;
}
