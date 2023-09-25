package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.customer.CorporateCustomer;
import sample.customer.Customer;
import sample.customer.PersonalCustomer;
import sample.dao.DAO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Order implements Initializable {

    @FXML
    private TableView<Product> orderTable;



    @FXML
    private Button addCart;

    @FXML
    private Button order;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField qty;


    @FXML
    private Label userName;

    @FXML
    private TableView<OrderLine> cartTable;

    @FXML
    private TextField total;


    @FXML
    private Label error;

    @FXML
    private Label limit;

    @FXML
    private Button delete;

    @FXML
    private Label creditLabel;

    @FXML
    void handleClose(MouseEvent event) {
        System.exit(0);
    }

    int ttl;  //input for text field total..order total


    CorporateCustomer cc;
    PersonalCustomer pc;

    DAO dao = null;

    private ObservableList<Product> ProductList = FXCollections.observableArrayList();
    private ObservableList<OrderLine> OrderLineList = FXCollections.observableArrayList();

    OrderLine ol; //to store the order lines




    TableColumn<Product,Integer> ProductID = null;                  //Creating columns for product table product
    TableColumn<Product,String> Name = null;
    TableColumn<Product, Integer> Price=null;

    TableColumn<OrderLine,String> product = null;                  //Creating columns for product table cart
    TableColumn<OrderLine,Integer> Qty = null;
    TableColumn<OrderLine, Integer> amount=null;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {                                                        //database connection
            dao = new DAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        id.setStyle(""
                + "-fx-text-fill: #dacccc;"
                + "-fx-prompt-text-fill: #cc8686");
        qty.setStyle(""
                + "-fx-text-fill: #dacccc;"
                + "-fx-prompt-text-fill: #cc8686");
        total.setStyle(""
                + "-fx-text-fill: #dacccc;"
                + "-fx-prompt-text-fill: #cc8686");


        //order(Product) Table column
        ProductID = new TableColumn<>("ProductID");             //Creating Table Columns
        ProductID.setMinWidth(50);

        Name = new TableColumn<>("Name");
        Name.setMinWidth(190);

        Price = new TableColumn<>("Price");
        Price.setMinWidth(70);

        ProductID.setStyle( "-fx-alignment: CENTER;");               //centering the text in the columns
        Name.setStyle( "-fx-alignment: CENTER;");
        Price.setStyle( "-fx-alignment: CENTER;");

        //Cart Table column
        product = new TableColumn<>("Product");                //Creating Table Columns
        product.setMinWidth(160);

        Qty = new TableColumn<>("Quantity");
        Qty.setMinWidth(85);

        amount = new TableColumn<>("SubTotal");
        amount.setMinWidth(85);

        product.setStyle( "-fx-alignment: CENTER;");                 //centering the text in the columns
        Qty.setStyle( "-fx-alignment: CENTER;");
        amount.setStyle( "-fx-alignment: CENTER;");



        orderTable.getColumns().addAll(ProductID,Name,Price);       //adding the columns to table
        cartTable.getColumns().addAll(product,Qty,amount);          //adding the columns to table


        //method calls
        try {                                                       //Filling table with product details
            showMenu();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        addCart.setOnAction(e-> {
            try {
                addToCart();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        delete.setOnAction(e->deleteItem());



        order.setOnAction(e-> {
            try {
                placeOrder();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });



        order.setDisable(true);             //Preventing cutomers from placing order without adding items to cart




    }




    //METHOD DEFINITION//

    public void setCustomerC(CorporateCustomer obj){
        cc=obj;


        String name;
        name = cc.getName();
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        userName.setMaxWidth(Double.MAX_VALUE);
        userName.setAlignment(Pos.CENTER);
        userName.setText(name);
    }




    public void setCustomerP(PersonalCustomer obj){
        pc=obj;

        String name;
        name = pc.getContactName();
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();     //Capitalizing the first letter only
        userName.setMaxWidth(Double.MAX_VALUE);
        userName.setAlignment(Pos.CENTER);
        userName.setText(name);

    }





    private void showMenu() throws SQLException {                              //loads the data in product table

        orderTable.getItems().clear();
        DAO dao = new DAO();
        ResultSet rs=null;

        rs=dao.getProduct();     //getting course liot from database


        while(rs.next()){
            Product pro = new Product();
            pro.setProductId(rs.getInt(1));
            pro.setName(rs.getString(2));
            pro.setPrice(rs.getInt(3));

            ProductList.add(pro);
        }


        orderTable.setItems(ProductList);

        ProductID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    private void addToCart() throws SQLException {

        int credit = 0;
        ttl = 0;

        OrderLineBuilder olb = new OrderLineBuilder().setId(Integer.parseInt(id.getText())).setName(dao.getItemName(Integer.parseInt(id.getText()))).setQty(Integer.parseInt(qty.getText()));           //Using Builder pattern to create order line
        ol = olb.getOrderLine();




        if(pc==null){                           //showing the credit limit to corporate customers
            credit = cc.getCrediLimit();
            creditLabel.setVisible(true);
            limit.setVisible(true);
        }

        OrderLineList.add(ol);


        cartTable.setItems(OrderLineList);


        product.setCellValueFactory(new PropertyValueFactory<>("name"));
        Qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        amount.setCellValueFactory(new PropertyValueFactory<>("price"));


        for(OrderLine o:OrderLineList){
            ttl+=o.getPrice();

        }
        this.total.setText(Integer.toString(ttl));

        if(pc==null){
            ResultSet rs = dao.getCustomer(cc.getId());
            while(rs.next()) {
                limit.setText(Integer.toString(rs.getInt(7)));    //Could have loaded the credit limit from cc.getCreditLimit() but when we make a order we should update the credit limit...so credit limit has to be change if customer whishes to order again(if i loaed the limit from cc... again the old limit will be displayed thats why loading it from database)
                cc.setCreditLimit(rs.getInt(7));
            }
            if(credit<ttl){                                                   //checking the credit limit for corporate customer
                error.setVisible(true);
                order.setDisable(true);
            }else {
                error.setVisible(false);
                order.setDisable(false);

            }
        }else order.setDisable(false);


    }




    private void deleteItem() {
        ttl = 0;
        int credit=0;

        OrderLineList.remove(cartTable.getSelectionModel().getSelectedItem());

        if(pc==null){
            credit = cc.getCrediLimit();
        }

        if(OrderLineList.isEmpty()){
            order.setDisable(true);
        }

        for(OrderLine o:OrderLineList){
            ttl+=o.getPrice();


        }

        this.total.setText(Integer.toString(ttl));

        if(pc==null){           //checking the credit limit for corporate customer
            limit.setText(Integer.toString(credit));
            if(credit<ttl){
                error.setVisible(true);
                order.setDisable(true);
            }else {
                error.setVisible(false);
                if(!(OrderLineList.isEmpty()))order.setDisable(false);

            }
        }

    }

    private void placeOrder() throws SQLException {

        OrderLineList.clear();

        if(pc==null) {                                                  //updating credit limit after a order
            dao.updateCreditLimit(cc.getId(),cc.getCrediLimit(),ttl);
            ResultSet rs = dao.getCustomer(cc.getId());
            while(rs.next()){

                limit.setText(Integer.toString(rs.getInt(7)));
            }
        }

        total.setText("");
        order.setDisable(true);
        Stage stage= new Stage();
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/sample/view/OrderSuccess.fxml");
        loader.setLocation(xmlUrl);

        try {
            loader.load();


        }catch(IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        stage.initStyle(StageStyle.UNDECORATED);


        stage.setScene(new Scene(root));

        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                stage.setMaximized(false);
        });


        stage.showAndWait();

    }








}
