package sample.controller;

import java.sql.SQLException;

public class OrderLineBuilder {

    private int id;
    private String name;
    private int price;
    private int qty;

    public OrderLineBuilder setId(int id){
            this.id = id;
        return this;
    }

    public OrderLineBuilder setName(String name){
        this.name = name;
        return this;
    }

    public OrderLineBuilder setQty(int qty){
        this.qty = qty;
        return this;
    }

    public OrderLine getOrderLine() throws SQLException {
        OrderLine ol = new OrderLine();
        ol.setName(name);
        ol.calSubTotal(id,qty);
        return ol;

    }


}
