package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    String userInput;
    PasswordStore passwordStore;
    String password;


    @FXML
    private TextField id_textfield;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordStore= new PasswordStore();
        password= passwordStore.pass+passwordStore.wo+passwordStore.rd;
    }

    public void onClickLogin(ActionEvent event) throws IOException {

        System.out.println(id_textfield.getText());
        userInput= id_textfield.getText();


        if(userInput.equals(password)){
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("welcome");
            alert.setHeaderText("welcome");
            alert.setTitle("welcome");
            alert.show();

            Stage currentStage= (Stage) id_textfield.getScene().getWindow();
            currentStage.close();



            FXMLLoader loader= new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root= (Parent) loader.load();
            Stage stage= new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Student Record Managent System");
            stage.show();
            alert.close();

        }

        else if(userInput.equals(null)){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("please fill in the field");
            alert.show();
        }
        else {
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("wrong passcode, try again!");
            alert.show();
        }





    }


}
