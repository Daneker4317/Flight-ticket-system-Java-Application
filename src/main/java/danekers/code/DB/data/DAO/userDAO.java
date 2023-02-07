package danekers.code.DB.data.DAO;

import danekers.code.DB.connection.PSQL;
import danekers.code.DB.data.service.CRUD;
import danekers.code.DB.data.service.Input;
import danekers.code.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class userDAO extends Input implements CRUD {

    private PSQL psql = new PSQL();

    @Override
    public List<User> getAll() throws SQLException {
        Connection connection = psql.getConnection();
        List<User> users = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users");
        while (resultSet.next()){
            users.add(new User(
                    resultSet.getString("name"),
                    resultSet.getString("gender"),
                    resultSet.getInt("balance"),
                    systemDAO.getUserTickets(resultSet.getString("name"))
            ));
        }

        statement.close();
        resultSet.close();
        connection.close();

        return users;
    }

    @Override
    public void add() throws SQLException {
        Connection connection = psql.getConnection();
        System.out.println("enter name");
        String name = in.next();
        System.out.println("enter age");
        int age = in.nextInt();
        System.out.println("enter gender(Male || Female)");
        String gender = in.next();
        System.out.println("enter balance");
        int balance = in.nextInt();

        PreparedStatement preparedStatement = connection.prepareStatement("insert into users (name , age , gender , balance) values (?,?,?,?)");
        preparedStatement.setString(1,name);
        preparedStatement.setInt(2,age);
        preparedStatement.setString(3,gender);
        preparedStatement.setInt(4,balance);

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();


        System.out.println("user added");
    }

    @Override
    public void delete() throws SQLException {
        Connection connection = psql.getConnection();
        System.out.println("enter user name for deleting");
        String name = in.next();
        Statement statement = connection.createStatement();
        ResultSet s = getCertain(name);
        if(!s.next()){
            System.out.println("user does not founded");
            return;
        }
        int userId = s.getInt("id");

        System.out.println("Enter 1 for confirm action");
        String one = in.next();
        if(!one.equals("1")){
            System.out.println("Action cancelled");
            return;
        }
        statement.executeUpdate("delete from users where id = " + userId);
        statement.executeUpdate("delete from orders where user_id = " + userId);

        s.close();
        statement.close();
        connection.close();

        System.out.println("user was deleted");

    }

    @Override
    public void update() throws SQLException {
        Connection connection  = psql.getConnection();
        System.out.println("enter user name how will be updated");
        String nameToUpdate = in.next();
        Statement statement = connection.createStatement();
        ResultSet s = getCertain(nameToUpdate);
        if(!s.next()){
            System.out.println("user does not founded");
            return;
        }
        int userId = s.getInt("id");
        System.out.println("enter new name");
        String name = in.next();
        System.out.println("enter new age");
        int age = in.nextInt();
        System.out.println("enter new balance");
        int balance = in.nextInt();
        System.out.println("enter new gender");
        String gender = in.next();

        Statement update = connection.createStatement();


        update.executeUpdate("update users set name = '" + name + "'" + " where id = " + userId);
        update.executeUpdate("update users set age = " + age + " where id = " + userId);
        update.executeUpdate("update users set balance = " + balance + " where id = " + userId);
        update.executeUpdate("update users set gender = '" + gender+ "'" + " where id = " + userId);

        update.close();
        statement.close();
        s.close();
        connection.close();

        System.out.println("user updated");

    }

    public ResultSet getCertain(String name) throws SQLException {
        Connection connection = psql.getConnection();
        String searchUser = "select * from users where name = '" +name + "'";
        Statement statement = connection.createStatement();
        ResultSet s = statement.executeQuery(searchUser);
        return s;
    }
}
