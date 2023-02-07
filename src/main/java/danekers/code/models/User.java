package danekers.code.models;

import java.util.List;

public class User {
    private int id;
    private String name;
    private int age;
    private String gender;
    private int balance;
    List<String> tickets;

    public User( String name, String gender,int balance, List<String> tickets) {
        this.name = name;
        this.balance = balance;
        this.gender = gender;
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
       StringBuffer sb = new StringBuffer(
                " name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + " "
       + "balance: " + balance);
        if(!tickets.isEmpty()){
            sb.append("  tickets: ");
            tickets.forEach(i -> sb.append(i ));
        }
        return sb.toString();
    }
}
