package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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



}
