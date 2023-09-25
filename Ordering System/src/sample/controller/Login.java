package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import sample.animation.Animation;
import sample.customer.CorporateCustomer;
import sample.customer.PersonalCustomer;
import sample.dao.DAO;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton signUp;

    @FXML
    private Label Error;



    @FXML
    private void handleClose(MouseEvent event){
        System.exit(0);

    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {

            signUp.setOnAction(e->singUp());


            login.setOnAction(e-> {
                try {
                    login();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            username.setStyle(""
                    + "-fx-prompt-text-fill: #cc8686;"
                    + "-fx-text-fill: #dacccc;"                    );


            password.setStyle(""
                    + "-fx-prompt-text-fill: #cc8686;"
                         + "-fx-text-fill: #dacccc;");

    }



    private void singUp() {

        signUp.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/sample/view/SignUp.fxml");
        loader.setLocation(xmlUrl);

        try {
            loader.load();


        }catch(IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage =new Stage();

        stage.setScene(new Scene(root));

        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                stage.setMaximized(false);
        });

        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }



    private void login() throws SQLException {


        String name,pass;
        DAO dao=new DAO();
        ResultSet rs = null;
        name= username.getText();
        pass=password.getText();


        rs = dao.getCustomer();


        while(rs.next()){

            if((name.toUpperCase().equals(rs.getString(2)))&&pass.equals(rs.getString(4)))
            {

                login.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                URL xmlUrl = getClass().getResource("/sample/view/Order.fxml");
                loader.setLocation(xmlUrl);

                try {
                    loader.load();


                }catch(IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Order ord = loader.getController();


                //Creating Customer Object
                if(rs.getString(5).equals("Personal")){

                    PersonalCustomer pc = new PersonalCustomer();
                    pc.setId(rs.getInt(1));
                    pc.setContactName(rs.getString(2));
                    pc.setAddress(rs.getString(3));
                    pc.setCrediCardNumber(rs.getLong(6));
                    ord.setCustomerP(pc);


                }else{
                    CorporateCustomer cc = new CorporateCustomer();
                    cc.setId(rs.getInt(1));
                    cc.setName(rs.getString(2));
                    cc.setAddress(rs.getString(3));
                    cc.setCreditLimit(rs.getInt(7));

                    if(!(rs.getInt(8)==0)){cc.setEmployeeId(rs.getInt(8));

                    }


                    ord.setCustomerC(cc);



                }


                Stage stage= new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));

                stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue)
                        stage.setMaximized(false);
                });


                stage.show();
                break;


            }else{
                Animation an = new Animation();

                username.clear();
                password.clear();
                Error.setVisible(true);
                an.shaker(username);
                an.shaker(password);



            }
        }
    }


}
