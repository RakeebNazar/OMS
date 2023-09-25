package sample.controller;

import sample.dao.DAO;

import java.sql.SQLException;

public class Product {

    private int productId;
    private String name;
    private int priceOfAUnit;


    public void setProductId(int productID) {
        this.productId = productID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.priceOfAUnit = price;
    }


    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return priceOfAUnit;
    }

    public int getQtyPrice(int id) throws SQLException {
        priceOfAUnit = new DAO().getPrice(id);
        return priceOfAUnit;
    }
}
