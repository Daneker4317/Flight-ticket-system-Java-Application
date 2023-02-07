package danekers.code.DANEKER;

import danekers.code.DB.data.DAO.TicketDAO;
import danekers.code.DB.data.DAO.systemDAO;
import danekers.code.DB.data.DAO.userDAO;
import danekers.code.models.Ticket;

import javax.security.sasl.SaslClient;
import java.sql.SQLException;
import java.util.Scanner;

public class Application implements App{
    private systemDAO systemDAO = new systemDAO();
    private TicketDAO ticketDAO = new TicketDAO();
    private userDAO userDAO = new userDAO();
    private Scanner in = new Scanner(System.in);

    @Override
    public void start() throws SQLException {
        System.out.println("Welcome to DANEKER flight ticket system");
        System.out.println("[1] --> control users");
        System.out.println("[2] --> control tickets");
        System.out.println("[3] --> control payments");
        System.out.println("[4] --> close app");

        while (true){
            System.err.println("enter key number");
            int n = in.nextInt();
            switch (n){
                case 1 -> controlUsers();
                case 2 -> controlTickets();
                case 3 -> controlPayments();
                case 4 -> exit();
                default -> System.out.println("invalid number");
            }
        }
    }

    private void controlUsers() throws SQLException {

        System.out.println("welcome to users control system");
        System.out.println("[1] -> add user");
        System.out.println("[2] -> delete user");
        System.out.println("[3] -> update user");
        System.out.println("[4] -> show all users");
        System.out.println("[5] -> go to main page ");
        System.out.println("[6] -> close app");

        while (true){

            System.err.println("enter key number");
            int n = in.nextInt();
            switch (n){
                case 1 -> userDAO.add();
                case 2 -> userDAO.delete();
                case 3 -> userDAO.update();
                case 4 -> userDAO.getAll().forEach(System.out::println);
                case 5 -> start();
                case 6 -> exit();
                default -> System.out.println("in valid number talpaep");

            }
        }

    }
    private void controlTickets() throws SQLException{
        System.out.println("welcome to ticket control system");
        System.out.println("[1] -> add ticket");
        System.out.println("[2] -> delete ticket");
        System.out.println("[3] -> show all tickets");
        System.out.println("[4] -> go to main page ");
        System.out.println("[5] -> close app");

        while (true){
            System.err.println("enter key number");

            int n = in.nextInt();

            switch (n){
                case 1 -> ticketDAO.add();
                case 2 -> ticketDAO.delete();
                case 3 -> ticketDAO.getAll().forEach(System.out::println);
                case 4 -> start();
                case 5 -> exit();
                default -> System.out.println("in valid number talpaep");

            }
        }
    }

    private void controlPayments() throws SQLException{

        System.out.println("welcome to payment control system");
        System.out.println("[1] -> buy ticket ");
        System.out.println("[2] -> return  ticket ");
        System.out.println("[3] -> show all purchased tickets");
        System.out.println("[4] -> go to main page ");
        System.out.println("[5] -> close app ");


        while (true){
            System.err.println("enter key number");
            int n = in.nextInt();

            switch (n){
                case 1 -> systemDAO.buyTicket();
                case 2 -> systemDAO.returnTicket();
                case 3 -> userDAO.getAll().stream().filter(user -> !user.getTickets().isEmpty()).forEach(System.out::println);
                case 4 -> start();
                case 5 -> exit();
                default -> System.out.println("in valid number");
            }
        }
    }

}
