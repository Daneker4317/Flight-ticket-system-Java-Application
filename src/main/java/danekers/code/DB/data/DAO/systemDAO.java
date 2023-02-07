package danekers.code.DB.data.DAO;

import danekers.code.DB.connection.PSQL;
import danekers.code.DB.data.service.Airoport;
import danekers.code.DB.data.service.Input;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class systemDAO extends Input implements Airoport {
    private static PSQL psql = new PSQL();
    private userDAO userDAO = new userDAO();
    private TicketDAO ticketDAO = new TicketDAO();

    @Override
    public void buyTicket() throws SQLException {

        Connection connection = psql.getConnection();

        System.out.println("firstly you have to log in to our system by your name");
        String name = in.next();
        ResultSet resultSetUser = userDAO.getCertain(name);


        if (!resultSetUser.next()) {
            System.out.println("incorrect name");
            return;
        }


        int balance = resultSetUser.getInt("balance");
        int userId = resultSetUser.getInt("id");


        System.out.println("enter departure point");
        String from = in.next();
        System.out.println("enter place of arrival");
        String to = in.next();
        ResultSet resultSetTicket = ticketDAO.getCertain(from, to);


        if (!resultSetTicket.next()) {
            System.out.println("ticket does not founded");
            return;
        }


        int ticketId = resultSetTicket.getInt("id");
        int quantity = resultSetTicket.getInt("quantity");
        int price = resultSetTicket.getInt("price");
        String date = resultSetTicket.getString("date");

        if (quantity == 0) {
            System.out.println("this ticket does not in stock");
            return;
        }


        if (price > balance) {
            System.out.println("not enough money");
            return;
        }

        Statement statement = connection.createStatement();


        statement.executeUpdate("update users set balance = " + (balance - price) + " where id = " + userId);
        statement.executeUpdate("update ticket set quantity =" + (quantity - 1) + " where id = " + ticketId);


        String sql = "insert into orders(user_name ,user_id , ticket_id , fr_om , t_o , date , price) values(?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, userId);
        preparedStatement.setInt(3, ticketId);
        preparedStatement.setString(4, from);
        preparedStatement.setString(5, to);
        preparedStatement.setString(6, date);
        preparedStatement.setInt(7, price);


        preparedStatement.executeUpdate();


        resultSetTicket.close();
        resultSetUser.close();
        preparedStatement.close();
        statement.close();
        connection.close();


        System.out.println("have a good trip");

    }

    @Override
    public void returnTicket() throws SQLException {
        Connection connection = psql.getConnection();
        System.out.println("enter user name how wanna return ticker");
        String name = in.next();
        ResultSet resultSetUser = userDAO.getCertain(name);
        if(!resultSetUser.next()){
            System.out.println("user not registered yet");
            return;
        }
        int balance = resultSetUser.getInt("balance");
        List<String> list = getUserTickets(name);
        if(list.isEmpty()){
            System.out.println("user has not tickets");
            return;
        }
        System.out.println(list);
        System.out.println("enter ticket id which will be returned");
        int id = in.nextInt();
        ResultSet resultSet = ticketDAO.getCertain(id);
        if(!resultSet.next()){
            System.out.println("ticket does not founded");
            return;
        }
        int quantity = resultSet.getInt("quantity");
        int price = resultSet.getInt("price");

        Statement statement = connection.createStatement();

        statement.executeUpdate("delete from orders where ticket_id = " + id);
        statement.executeUpdate("update ticket set quantity = " + (quantity + 1 ) + " where id = "  + id);
        statement.executeUpdate("update users set balance = " + (price + balance) + " where name  = '" + name + "'");


        resultSet.close();
        resultSetUser.close();
        statement.close();
        connection.close();

        System.out.println("ticket returned");

    }

    public static List<String> getUserTickets(String userName) throws SQLException {
        Connection connection = psql.getConnection();
        String sql = "select * from orders where user_name = '" + userName + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    "[" + " id: " + resultSet.getInt("ticket_id") + " "
                            + resultSet.getString("fr_om") + " "
                            + resultSet.getString("t_o") + " "
                            + resultSet.getString("date") + " "
                            + resultSet.getInt("price") + "]"
            );
        }


        resultSet.close();
        statement.close();
        connection.close();

        return list;

    }
}
