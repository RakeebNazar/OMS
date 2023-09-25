package sample.controller;

import java.sql.SQLException;

public class OrderLine {
    private int id;
    private String name;
    private int price;
    private int qty;


    public void calSubTotal(int id,int qty) throws SQLException {
        this.qty=qty;
        price = new Product().getQtyPrice(id)*qty;
    }

    public int getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}
