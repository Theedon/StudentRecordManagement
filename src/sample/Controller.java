package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;


import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Students currentClickedStudent;

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
        String gender, student_class;

        gender= ((RadioButton) genderGroup.getSelectedToggle()).getText();
        student_class= ((RadioButton) classGroup.getSelectedToggle()).getText();

        PreparedStatement preparedStatement;


        try{
            preparedStatement= connection.prepareStatement("INSERT INTO students (first_name, last_name, email, gender, class, passport)" +
                    "VALUES(?,?,?,?,?,?)");
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, student_class);
            preparedStatement.setString(6, null);




            preparedStatement.execute();
            showStudents();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
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
            preparedStatement.setBlob(6, (InputStream) null);
            preparedStatement.setInt(7, currentClickedStudent.getId());




            preparedStatement.execute();
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
            showStudents();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void onTableRowClicked(MouseEvent event){
        currentClickedStudent= tvPhoneTable.getSelectionModel().getSelectedItem();
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



}
