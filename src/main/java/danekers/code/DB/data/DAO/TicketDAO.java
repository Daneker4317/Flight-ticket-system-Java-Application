package danekers.code.DB.data.DAO;

import com.sun.nio.sctp.AbstractNotificationHandler;
import danekers.code.DB.connection.PSQL;
import danekers.code.DB.data.service.Input;
import danekers.code.models.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketDAO extends Input {
    private PSQL psql = new PSQL();

    public void add() throws SQLException {
        Connection connection = psql.getConnection();


        System.out.println("enter departure point");
        String from = in.next();
        System.out.println("enter place of arrival");
        String to = in.next();
        System.out.println("enter the date of ticket  [yyyy-mm-dd]");
        String date = in.next();
        System.out.println("enter price of ticket");
        int price = in.nextInt();
        System.out.println("enter quantity");
        int quantity = in.nextInt();


        String add = "insert into ticket (fr_om , t_o , date , price ,quantity) values (?,?,?,? , ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(add);

        preparedStatement.setString(1, from);
        preparedStatement.setString(2, to);
        preparedStatement.setString(3, date);
        preparedStatement.setInt(4, price);
        preparedStatement.setInt(5, quantity);


        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        System.out.println("Ticket added");
    }
    public ResultSet getCertain(String from , String to) throws SQLException {
        Connection connection = psql.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from ticket where fr_om ='" + from + "'" + " and t_o  ='" + to + "'");
        return resultSet;
    }
    public ResultSet getCertain(int id) throws SQLException {
        Connection connection = psql.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from ticket where id = " + id );
        return resultSet;
    }

    public void delete() throws SQLException {
        Connection connection = psql.getConnection();

        System.out.println("enter departure point for removing");
        String from = in.next();
        System.out.println("enter place of arrival removing");
        String to = in.next();

        Statement statement = connection.createStatement();
        ResultSet resultSet = getCertain(from , to);
        if (!resultSet.next()) {
            System.out.println("ticket does not founded");
            return;
        }
        int ticketId = resultSet.getInt("id");
        System.out.println(resultSet.getString("fr_om") + " " + resultSet.getString("t_o")
                + resultSet.getString("date") + " " + resultSet.getInt("price") + " " + resultSet.getInt("quantity"));

        System.out.println("enter 1 for deleting");
        String one = in.next();
        if (!one.equals("1")) {
            System.out.println("action cancelled");
            return;
        }


        String deleteFromTickets = "delete from ticket where id = " + ticketId;
        String deleteFromOrders = "delete from orders where ticket_id = " + ticketId;

        Statement statementDelete = connection.createStatement();

        statementDelete.executeUpdate(deleteFromOrders);
        statementDelete.executeUpdate(deleteFromTickets);

        resultSet.close();
        statement.close();
        statementDelete.close();
        connection.close();

        System.out.println("ticket was removed");

    }


    public List<Object> getAll() throws SQLException {

        Connection connection = psql.getConnection();
        List<Ticket> tickets = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from ticket");
        while (resultSet.next()) {
            tickets.add(new Ticket(
                    resultSet.getString("fr_om"),
                    resultSet.getString("t_o"),
                    resultSet.getInt("price"),
                    resultSet.getString("date"),
                    resultSet.getInt("quantity")

            ));
        }
        return Collections.singletonList(tickets);
    }
}
