package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    String intent;
    Students currentClickedStudent;

    FileInputStream passportFileInputStream= null;
    int passportLength= 0;

    @FXML
    private TableView<Students> tvPhoneTable;

    @FXML
    private TableColumn<Students, Integer> col_sn;

    @FXML
    private TableColumn<Students, Integer> col_id;

    @FXML
    private TableColumn<Students, String> col_first_name;

    @FXML
    private TableColumn<Students, String> col_last_name;

    @FXML
    private TableColumn<Students, String> col_email;

    @FXML
    private TableColumn<Students, String> col_gender;

    @FXML
    private TableColumn<Students, String> col_class;


    @FXML
    private TextField text_first_name;

    @FXML
    private TextField text_last_name;

    @FXML
    private TextField text_email;


    @FXML
    ToggleGroup genderGroup;

    @FXML
    private RadioButton Male;

    @FXML
    private RadioButton Female;




    @FXML
    private ImageView passport_image;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        genderGroup= new ToggleGroup();
        Male.setToggleGroup(genderGroup);
        Female.setToggleGroup(genderGroup);


        showStudents();
    }

    public ObservableList<Students> getStudentsList(){
        ObservableList<Students> studentsList= FXCollections.observableArrayList();
        DatabaseConnection databaseConnection= new DatabaseConnection();
        Connection connection= databaseConnection.getConnection();

        String query= "SELECT * FROM students";
        Statement statement;
        ResultSet resultSet;

        try{
            statement= connection.createStatement();
            resultSet= statement.executeQuery(query);

            Students students;
            int serialNumber=1;
            Image passport= null;
            Image admission= null;
            Image olevel= null;
            Image guarantor= null;
            Image jamb= null;

            while (resultSet.next()){

                InputStream passportInputStream= resultSet.getBinaryStream("passport");

                InputStream admissionInputStream= resultSet.getBinaryStream("admission_img");


                InputStream olevelInputStream= resultSet.getBinaryStream("olevel_img");


                InputStream guarantorInputStream= resultSet.getBinaryStream("guarantor_img");


                InputStream jambInputStream= resultSet.getBinaryStream("jamb_img");


                if(passportInputStream!=null){
                    passport= new Image(passportInputStream);
                }

                if(admissionInputStream!=null){
                    admission= new Image(admissionInputStream);
                }

                if(olevelInputStream!=null){
                    olevel= new Image(olevelInputStream);
                }

                if(guarantorInputStream!=null){
                    guarantor= new Image(guarantorInputStream);
                }

                if(jambInputStream!=null){
                    jamb= new Image(jambInputStream);
                }







                students= new Students(serialNumber,
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("gender"),
                        passport,
                        admission,
                        olevel,
                        guarantor,
                        jamb

                );
                studentsList.add(students);
                serialNumber++;
            }




        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return studentsList;
    }

    public void showStudents(){
        ObservableList<Students> studentsList= getStudentsList();


        col_sn.setCellValueFactory(new PropertyValueFactory<Students, Integer>("serialNumber"));
        col_id.setCellValueFactory(new PropertyValueFactory<Students, Integer>("id"));
        col_first_name.setCellValueFactory(new PropertyValueFactory<Students, String>("firstName"));
        col_last_name.setCellValueFactory(new PropertyValueFactory<Students, String>("lastName"));
        col_email.setCellValueFactory(new PropertyValueFactory<Students, String>("email"));
        col_gender.setCellValueFactory(new PropertyValueFactory<Students, String>("gender"));





        tvPhoneTable.setItems(studentsList);

    }

    public void onClickAdd(ActionEvent event){
        DatabaseConnection databaseConnection= new DatabaseConnection();
        Connection connection= databaseConnection.getConnection();

        String table_name = "phone_table";
        String first_name= text_first_name.getText();
        String last_name= text_last_name.getText();
        String email= text_email.getText();
        RadioButton selectedGender;


        selectedGender= ((RadioButton) genderGroup.getSelectedToggle());


        PreparedStatement preparedStatement;


        if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || selectedGender == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please fill out the fields");
            alert.show();
        }

        else if (ValidateInput.ValidateEmail(email)==false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("make sure your email is correct");
            alert.show();
        }

        else {
            String gender= selectedGender.getText();


            try{
                preparedStatement= connection.prepareStatement("INSERT INTO students (first_name, last_name, email, gender, passport)" +
                        "VALUES(?,?,?,?,?)");
                preparedStatement.setString(1, first_name);
                preparedStatement.setString(2, last_name);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, gender);
                preparedStatement.setBinaryStream(5, passportFileInputStream, passportLength);




                preparedStatement.execute();
                clearInputFields();
                showStudents();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }





    }

    public void onClickUpdate(ActionEvent event){
        DatabaseConnection databaseConnection= new DatabaseConnection();
        Connection connection= databaseConnection.getConnection();


        String table_name = "students";
        String first_name= text_first_name.getText();
        String last_name= text_last_name.getText();
        String email= text_email.getText();
        RadioButton selectedGender;

        selectedGender= ((RadioButton) genderGroup.getSelectedToggle());



        PreparedStatement preparedStatement;


        if(currentClickedStudent==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please pick a row to be updated");
            alert.show();
        }

        else if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || selectedGender == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please fill out the fields");
            alert.show();
        }

        else if (ValidateInput.ValidateEmail(email)==false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("make sure your email is correct");
            alert.show();
        }

        else {
            String gender = selectedGender.getText();


            try{
                preparedStatement= connection.prepareStatement("UPDATE students SET first_name= ?, last_name=?, email=?, gender=?, passport= ? WHERE id= ?");
                preparedStatement.setString(1, first_name);
                preparedStatement.setString(2, last_name);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, gender);
                preparedStatement.setBinaryStream(5, passportFileInputStream, passportLength);
                preparedStatement.setInt(6, currentClickedStudent.getId());




                preparedStatement.execute();
                clearInputFields();
                showStudents();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }





    }

    public void onClickDelete(ActionEvent event){
        DatabaseConnection databaseConnection= new DatabaseConnection();
        Connection connection= databaseConnection.getConnection();

        if(currentClickedStudent==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please pick a row to be deleted");
            alert.show();
        }
        else {
            try {
                PreparedStatement preparedStatement= connection.prepareStatement("DELETE FROM students WHERE id=?");
                preparedStatement.setInt(1, currentClickedStudent.getId());
                preparedStatement.execute();
                clearInputFields();
                showStudents();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    }

    @FXML
    private void onTableRowClicked(MouseEvent event){
        currentClickedStudent= tvPhoneTable.getSelectionModel().getSelectedItem();
        if(currentClickedStudent!=null){
            if (currentClickedStudent.getPassport()!=null){
                passport_image.setImage(currentClickedStudent.getPassport());
            }
            loadData();
        }
    }


    public void loadData(){
        text_first_name.setText(currentClickedStudent.getFirstName());
        text_last_name.setText(currentClickedStudent.getLastName());
        text_email.setText(currentClickedStudent.getEmail());

        if(currentClickedStudent.getGender().equals("Male")){
            genderGroup.selectToggle(Male);
        }
        else {
            genderGroup.selectToggle(Female);
        }


    }

    public void clearInputFields(){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("successful");
        alert.show();
        text_first_name.setText(null);
        text_last_name.setText(null);
        text_email.setText(null);
        genderGroup.selectToggle(null);
        passport_image.setImage(null);
        currentClickedStudent= null;
    }

    public void onClickClear(ActionEvent event){
        text_first_name.setText(null);
        text_last_name.setText(null);
        text_email.setText(null);
        genderGroup.selectToggle(null);
        passport_image.setImage(null);
        currentClickedStudent= null;
    }

    public void onClickUploadPassport(ActionEvent event){
        FileChooser fileChooser= new FileChooser();
        FileChooser.ExtensionFilter ext1= new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
        FileChooser.ExtensionFilter ext2= new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file= fileChooser.showOpenDialog(passport_image.getScene().getWindow());

        BufferedImage bufferedImage;
        try {
            bufferedImage= ImageIO.read(file);
            Image image= SwingFXUtils.toFXImage(bufferedImage, null);
            passport_image.setImage(image);
            FileInputStream fileInputStream= new FileInputStream(file);
            int len= (int) file.length();

            passportFileInputStream= fileInputStream;
            passportLength= len;


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickUploadDocuments(ActionEvent event){
        intent= "upload";


        FXMLLoader loader= new FXMLLoader(getClass().getResource("documents.fxml"));

        try{
            Parent root= (Parent) loader.load();

            DocumentsController documentsController= loader.getController();

            if(currentClickedStudent==null){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Select a row to be updated first");
                alert.show();
            }
            else {
                documentsController.sendData(currentClickedStudent, intent);
                Stage stage= new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Update student documents");
                stage.show();
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void onClickViewDocuments(ActionEvent event){
        intent= "view";

        FXMLLoader loader= new FXMLLoader(getClass().getResource("documents.fxml"));

        try{
            Parent root= (Parent) loader.load();

            DocumentsController documentsController= loader.getController();

            if(currentClickedStudent==null){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Select a row to be viewed first");
                alert.show();
            }
            else {
                documentsController.sendData(currentClickedStudent, intent);
                Stage stage= new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("view student documents");
                stage.show();
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }


    }







}
