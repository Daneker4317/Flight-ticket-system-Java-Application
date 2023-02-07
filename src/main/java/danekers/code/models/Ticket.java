package danekers.code.models;

public class Ticket {
    private int id;
    private String from;
    private String to;
    private int price;
    private String date;
    private int quantity;

    public Ticket(String from, String to, int  price, String date , int quantity) {
        this.from = from;
        this.quantity = quantity;
        this.to = to;
        this.date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int  price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return

                " from='" + from + '\'' +
                " to='" + to + '\'' +
                " price=" + price +
                "date='" + date + '\'' +
                " quantity=" + quantity + " \n"
                ;
    }
}
