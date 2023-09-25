package sample.dao;

import java.sql.*;

public class DAO {

    Connection con = null;

    public DAO() throws SQLException {

        String url = "jdbc:sqlite:Ordering System.db";
        con = DriverManager.getConnection(url);

    }



    public boolean insertCustomer(String name, String address, String pass, String type, long card, int emp) throws SQLException {


        String Query = "insert into customer (Name,Address,Password,CustomerType,CreditCardNumber,EmployeeID) values(?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(Query);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, pass);
        ps.setString(4, type);
        ps.setLong(5, card);
        ps.setInt(6, emp);


        ps.executeUpdate();
        con.close();
        return true;

    }


    public boolean insertCustomer(String name, String address, String pass, String type, int limit, int emp) throws SQLException {


        String Query = "insert into customer (Name,Address,Password,CustomerType,CreditLimit,EmployeeID) values(?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(Query);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, pass);
        ps.setString(4, type);
        ps.setInt(5, limit);
        ps.setInt(6, emp);


        ps.executeUpdate();
        con.close();
        return true;

    }


    public boolean insertCustomer(String name, String address, String pass, String type, long card) throws SQLException {


        String Query = "insert into customer (Name,Address,Password,CustomerType,CreditCardNumber) values(?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(Query);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, pass);
        ps.setString(4, type);
        ps.setLong(5, card);


        ps.executeUpdate();
        con.close();
        return true;

    }

    public boolean insertCustomer(String name, String address, String pass, String type, int limit) throws SQLException {


        String Query = "insert into customer (Name,Address,Password,CustomerType,CreditLimit) values(?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(Query);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, pass);
        ps.setString(4, type);
        ps.setInt(5, limit);


        ps.executeUpdate();
        con.close();
        return true;

    }


    public ResultSet getProduct() {
        String Query = "select * from product";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(Query);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return rs;


    }

    public int getPrice(int id) throws SQLException {
        int price = 0;
        String Query = "select Price from product where productID=?";


        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(Query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (rs.next()) {
            price = rs.getInt(1);
        }
        return price;
    }


    public String getItemName(int id) throws SQLException {
        String name = null;
        String Query = "select Name from product where productID=?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(Query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (rs.next()) {
            name = rs.getString(1);
        }
        return name;
    }


    public void updateCreditLimit(int id,int creditlimit,int orderAmount) throws SQLException {
        int credit = creditlimit-orderAmount;
        String Query = "UPDATE customer SET CreditLimit = ? WHERE customer.CustomerID = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(Query);
            ps.setInt(1, credit);
            ps.setInt(2,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public ResultSet getCustomer() {
        String Query = "select * from customer";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(Query);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return rs;

    }


    public ResultSet getCustomer(int cutsomerId) throws SQLException {
        String Query = "select * from customer where CustomerID = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(Query);
            ps.setInt(1, cutsomerId);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return rs;
    }


    public ResultSet employee() throws SQLException {
        String Query = "select * from employee";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(Query);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return rs;

    }

}
















