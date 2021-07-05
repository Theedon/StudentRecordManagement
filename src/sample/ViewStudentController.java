package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewStudentController implements Initializable {

    int id;
    String firstName, lastName, middleName, emailString, facultyString, departmentString, matricNo, genderString;
    Image passport= null;
    Students students;


    @FXML
    private Label id_view;

    @FXML
    private Label matric;

    @FXML
    private Label first_name;

    @FXML
    private Label last_name;

    @FXML
    private Label middle_name;

    @FXML
    private Label gender;

    @FXML
    private Label faculty;

    @FXML
    private Label department;

    @FXML
    private Label email;

    @FXML
    private ImageView passport_view;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passport_view.setImage(null);

    }


    public void sendData(Students currentClickedStudent){
        students= currentClickedStudent;
        showInfo();
    }

    public void getInformation() {
        int id = students.getId();
        System.out.println(students);
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();


        String query = "SELECT first_name, middle_name, last_name, matric_no, email, passport, faculty, department, gender FROM students WHERE id= " + id + "";
        Statement statement;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("middle_name");
                middleName = resultSet.getString("last_name");
                matricNo = resultSet.getString("matric_no");
                emailString = resultSet.getString("email");
                InputStream passportInputStream = resultSet.getBinaryStream("passport");
                facultyString = resultSet.getString("faculty");
                departmentString = resultSet.getString("department");
                genderString = resultSet.getString("gender");


                if (passportInputStream != null) {
                    passport = new Image(passportInputStream);
                }

            }









        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void showInfo(){
        getInformation();
        System.out.println(firstName);

        id_view.setText(Integer.toString(students.getId()));
        first_name.setText(firstName);
        last_name.setText(lastName);
        matric.setText(matricNo);
        email.setText(emailString);
        faculty.setText(facultyString);
        department.setText(departmentString);
        gender.setText(genderString);
        middle_name.setText(middleName);


        passport_view.setImage(passport);
    }
}
