package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
    ToggleGroup classGroup;

    @FXML
    private RadioButton Gold;

    @FXML
    private RadioButton Silver;

    @FXML
    private RadioButton Diamond;

    @FXML
    private ImageView passport_image;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        classGroup= new ToggleGroup();
        Gold.setToggleGroup(classGroup);
        Silver.setToggleGroup(classGroup);
        Diamond.setToggleGroup(classGroup);


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
            Image image= null;
            while (resultSet.next()){

                InputStream inputStream= resultSet.getBinaryStream("passport");
                if(inputStream!=null){
                    image= new Image(inputStream);
                }




                students= new Students(serialNumber,
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("gender"),
                        resultSet.getString("class"),
                        image

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
        col_class.setCellValueFactory(new PropertyValueFactory<Students, String>("student_class"));




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
        RadioButton selectedStudentClass;

        selectedGender= ((RadioButton) genderGroup.getSelectedToggle());
        selectedStudentClass= ((RadioButton) classGroup.getSelectedToggle());


        PreparedStatement preparedStatement;


        if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || selectedGender == null ||
        selectedStudentClass==null) {
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
            String student_class= selectedStudentClass.getText();

            try{
                preparedStatement= connection.prepareStatement("INSERT INTO students (first_name, last_name, email, gender, class, passport)" +
                        "VALUES(?,?,?,?,?,?)");
                preparedStatement.setString(1, first_name);
                preparedStatement.setString(2, last_name);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, gender);
                preparedStatement.setString(5, student_class);
                preparedStatement.setBinaryStream(6, passportFileInputStream, passportLength);




                preparedStatement.execute();
                clearInputFields();
                showStudents();
                connection.close();

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
        String gender, student_class;

        gender= ((RadioButton) genderGroup.getSelectedToggle()).getText();
        student_class= ((RadioButton) classGroup.getSelectedToggle()).getText();

        PreparedStatement preparedStatement;


        try{
            preparedStatement= connection.prepareStatement("UPDATE students SET first_name= ?, last_name=?, email=?, gender=?, class=?, passport= ? WHERE id= ?");
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, student_class);
            preparedStatement.setBinaryStream(6, passportFileInputStream, passportLength);
            preparedStatement.setInt(7, currentClickedStudent.getId());




            preparedStatement.execute();
            clearInputFields();
            showStudents();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void onClickDelete(ActionEvent event){
        DatabaseConnection databaseConnection= new DatabaseConnection();
        Connection connection= databaseConnection.getConnection();

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

    @FXML
    private void onTableRowClicked(MouseEvent event){
        currentClickedStudent= tvPhoneTable.getSelectionModel().getSelectedItem();
        if (currentClickedStudent.getImage()!=null){
            passport_image.setImage(currentClickedStudent.getImage());
        }
        loadData();
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

        if(currentClickedStudent.getStudent_class().equals("Gold")){
            classGroup.selectToggle(Gold);
        }
        else if(currentClickedStudent.getStudent_class().equals("Silver")){
            classGroup.selectToggle(Silver);
        }
        else{
            classGroup.selectToggle(Diamond);
        }
    }

    public void clearInputFields(){
        text_first_name.setText(null);
        text_last_name.setText(null);
        text_email.setText(null);
        genderGroup.selectToggle(null);
        classGroup.selectToggle(null);
        currentClickedStudent= null;
    }

    public void onClickUploadImage(ActionEvent event){
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





}
