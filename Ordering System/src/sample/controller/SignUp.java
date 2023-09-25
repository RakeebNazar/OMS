package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.dao.DAO;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUp implements Initializable {
    @FXML
    private AnchorPane Ap;

    @FXML
    private JFXButton login;

    @FXML
    private Label Error;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXTextField type;

    @FXML
    private JFXTextField card;

    @FXML
    private JFXTextField creditLimit;
    @FXML
    private CheckBox employee;

    @FXML
    private ChoiceBox<Employee> employeeDetail;

    @FXML
    private Label success;



    @FXML
    void handleClose(MouseEvent event) {
        System.exit(0);
    }







    Boolean emptick=false;          //to know whether the customer is a employee or not


    ObservableList<Employee> listForEmp = FXCollections.observableArrayList();
    DAO dao = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setStyle(""
                + "-fx-prompt-text-fill: #cc8686;"
                + "-fx-text-fill: #dacccc;");

        password.setStyle(""
                + "-fx-text-fill: #dacccc;"
                + "-fx-prompt-text-fill: #cc8686");

        address.setStyle(""
                + "-fx-text-fill: #dacccc;"
                + "-fx-prompt-text-fill: #cc8686");
        type.setStyle(""
                + "-fx-text-fill: #dacccc;"
                + "-fx-prompt-text-fill: #cc8686");

        card.setStyle(""
                + "-fx-text-fill: #dacccc;"
                + "-fx-prompt-text-fill: #cc8686");

        creditLimit.setStyle(""
                + "-fx-text-fill: #dacccc;"
                + "-fx-prompt-text-fill: #cc8686");
        try {
            dao = new DAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        employee.setOnAction(e -> {
            try {
                tick();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });


        login.setOnAction(e -> {
            try {
                addCustomer();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

    }


    private void tick() throws SQLException {
        if (employee.isSelected()) {
            employeeDetail.setDisable(false);


            ResultSet rs = dao.employee();
            while (rs.next()) {

                Employee emp = new Employee();
                emp.id = rs.getInt(1);
                emp.name = rs.getString(2);

                listForEmp.add(emp);

            }
            employeeDetail.setItems(listForEmp);
            employeeDetail.setValue(listForEmp.get(0));

            emptick = true;


        } else {
            employeeDetail.setDisable(true);
            listForEmp.clear();                 //clearing the choice box if check is box is unticked
            emptick = false;
        }

    }


    private void addCustomer() throws SQLException {
        boolean result=false;
        if (emptick) {
            if (card.getText().isEmpty()) {          //Add Personal Customer with employee
                result = dao.insertCustomer(username.getText().toUpperCase(), address.getText(), password.getText(), type.getText(), Integer.parseInt(creditLimit.getText()), employeeDetail.getSelectionModel().getSelectedItem().id);
                if(result)success.setVisible(true);
            } else {                                     //add Corporate Customer with employee
                result = dao.insertCustomer(username.getText().toUpperCase(), address.getText(), password.getText(), type.getText(), Long.parseLong(card.getText()), employeeDetail.getSelectionModel().getSelectedItem().id);
                if(result)success.setVisible(true);
            }
        } else {
            if (card.getText().isEmpty()) {          //Add Personal Customer withou employee
                result = dao.insertCustomer(username.getText().toUpperCase(), address.getText(), password.getText(), type.getText(), Integer.parseInt(creditLimit.getText()));
                if(result)success.setVisible(true);
            }
             else{                                     //add Corporate Customer without employee
                result = dao.insertCustomer(username.getText().toUpperCase(), address.getText(), password.getText(), type.getText(), Long.parseLong(card.getText()));
                if(result)success.setVisible(true);
             }

            }
        }
    }

