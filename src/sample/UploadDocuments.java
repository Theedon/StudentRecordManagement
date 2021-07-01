package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class UploadDocuments implements Initializable {
    Students students;

    FileInputStream admissionFileInputStream;
    int lenAdmission;

    FileInputStream OlevelFileInputStream;
    int lenOlevel;

    FileInputStream GuarantorFileInputStream;
    int lenGuarantor;

    FileInputStream JambFileInputStream;
    int lenJamb;

    FileInputStream BiodataFileInputStream;
    int lenBiodata;

    FileInputStream CourseFileInputStream;
    int lenCourse;

    FileInputStream LGAFileInputStream;
    int lenLGA;

    FileInputStream MedicalFileInputStream;
    int lenMedical;

    FileInputStream BirthFileInputStream;
    int lenBirth;

    FileInputStream SchoolFileInputStream;
    int lenSchool;



    Image admission= null;
    Image olevel= null;
    Image guarantor= null;
    Image jamb= null;

    Image biodata= null;
    Image course= null;
    Image lga= null;
    Image medical= null;
    Image birth_certificate= null;
    Image school_fees= null;

    String classIntent;

    @FXML
    private ImageView imageDocument;

    @FXML
    private Label text_label;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageDocument.setImage(null);
    }


    public void sendData(Students currentClickedStudent, String intent){
        classIntent= intent;
        students= currentClickedStudent;
        getImages();
        if (intent.equals("admission")){
            imageDocument.setImage(admission);
        }

        if (intent.equals("olevel")){
            text_label.setText("O'LEVEL RESULT");
            imageDocument.setImage(olevel);
        }

        if (intent.equals("guarantor")){
            text_label.setText("GUARANTOR LETTER");
            imageDocument.setImage(guarantor);
        }

        if (intent.equals("jamb")){
            text_label.setText("JAMB LETTER");
            imageDocument.setImage(jamb);
        }

        if (intent.equals("biodata")){
            text_label.setText("BIODATA FORM");
            imageDocument.setImage(biodata);
        }


        if (intent.equals("course")){
            text_label.setText("COURSE REGISTRATION FORM");
            imageDocument.setImage(course);
        }

        if (intent.equals("lga")){
            text_label.setText("LGA form");
            imageDocument.setImage(lga);
        }

        if (intent.equals("medical")){
            text_label.setText("MEDICAL CERTIFICATE");
            imageDocument.setImage(medical);
        }

        if (intent.equals("school_fees")){
            text_label.setText("SCHOOL FEES RECEIPT");
            imageDocument.setImage(school_fees);
        }

        if (intent.equals("birth_certificate")){
            text_label.setText("BIRTH CERTIFICATE");
            imageDocument.setImage(birth_certificate);
        }
    }

    public void getImages() {
        int id = students.getId();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();


        String query = "SELECT admission_img, olevel_img, guarantor_img, jamb_img, biodata_form, course_form, lga_certificate, medical_certificate, birth_certificate, school_fees FROM students WHERE id= " + id + "";
        Statement statement;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {


                InputStream admissionInputStream = resultSet.getBinaryStream("admission_img");


                InputStream olevelInputStream = resultSet.getBinaryStream("olevel_img");


                InputStream guarantorInputStream = resultSet.getBinaryStream("guarantor_img");


                InputStream jambInputStream = resultSet.getBinaryStream("jamb_img");


                InputStream biodataInputStream = resultSet.getBinaryStream("biodata_form");


                InputStream courseInputStream = resultSet.getBinaryStream("course_form");

                InputStream lgaInputStream = resultSet.getBinaryStream("lga_certificate");

                InputStream medicalInputStream = resultSet.getBinaryStream("medical_certificate");


                InputStream birthInputStream = resultSet.getBinaryStream("birth_certificate");

                InputStream schoolInputStream = resultSet.getBinaryStream("school_fees");




                if (admissionInputStream != null) {
                    admission = new Image(admissionInputStream);
                }

                if (olevelInputStream != null) {
                    olevel = new Image(olevelInputStream);
                }

                if (guarantorInputStream != null) {
                    guarantor = new Image(guarantorInputStream);
                }

                if (jambInputStream != null) {
                    jamb = new Image(jambInputStream);
                }


                if (biodataInputStream != null) {
                    biodata = new Image(biodataInputStream);
                }


                if (courseInputStream != null) {
                    course = new Image(courseInputStream);
                }

                if (medicalInputStream != null) {
                    medical = new Image(medicalInputStream);
                }

                if (lgaInputStream != null) {
                    lga = new Image(lgaInputStream);
                }

                if (birthInputStream != null) {
                    birth_certificate = new Image(birthInputStream);
                }

                if (schoolInputStream != null) {
                    school_fees = new Image(schoolInputStream);
                }

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void onClickSubmitDocument(ActionEvent event) {
        if (classIntent.equals("admission")) {

            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement;


            if (admissionFileInputStream == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("make sure you have uploaded the required document");
                alert.show();
            } else {
                try {
                    preparedStatement = connection.prepareStatement("UPDATE students SET admission_img= ? WHERE id= ?");
                    preparedStatement.setBinaryStream(1, admissionFileInputStream, lenAdmission);
                    preparedStatement.setInt(2, students.getId());
                    preparedStatement.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("successful");
                    alert.show();
                    Stage stage = (Stage) imageDocument.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if (classIntent.equals("olevel")) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement;


            if (OlevelFileInputStream == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("make sure you have uploaded the required document");
                alert.show();
            } else {
                try {
                    preparedStatement = connection.prepareStatement("UPDATE students SET olevel_img= ? WHERE id= ?");
                    preparedStatement.setBinaryStream(1, OlevelFileInputStream, lenOlevel);
                    preparedStatement.setInt(2, students.getId());
                    preparedStatement.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("successful");
                    alert.show();
                    Stage stage = (Stage) imageDocument.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if (classIntent.equals("guarantor")) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement;


            if (GuarantorFileInputStream == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("make sure you have uploaded the required document");
                alert.show();
            } else {
                try {
                    preparedStatement = connection.prepareStatement("UPDATE students SET guarantor_img= ? WHERE id= ?");
                    preparedStatement.setBinaryStream(1, GuarantorFileInputStream, lenGuarantor);
                    preparedStatement.setInt(2, students.getId());
                    preparedStatement.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("successful");
                    alert.show();
                    Stage stage = (Stage) imageDocument.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if (classIntent.equals("jamb")) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement;


            if (JambFileInputStream == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("make sure you have uploaded the required document");
                alert.show();
            } else {
                try {
                    preparedStatement = connection.prepareStatement("UPDATE students SET jamb_img= ? WHERE id= ?");
                    preparedStatement.setBinaryStream(1, JambFileInputStream, lenJamb);
                    preparedStatement.setInt(2, students.getId());
                    preparedStatement.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("successful");
                    alert.show();
                    Stage stage = (Stage) imageDocument.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }

        if (classIntent.equals("biodata")) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement;


            if (BiodataFileInputStream == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("make sure you have uploaded the required document");
                alert.show();
            } else {
                try {
                    preparedStatement = connection.prepareStatement("UPDATE students SET biodata_form= ? WHERE id= ?");
                    preparedStatement.setBinaryStream(1, BiodataFileInputStream, lenBiodata);
                    preparedStatement.setInt(2, students.getId());
                    preparedStatement.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("successful");
                    alert.show();
                    Stage stage = (Stage) imageDocument.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }

        if (classIntent.equals("course")) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement;


            if (CourseFileInputStream == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("make sure you have uploaded the required document");
                alert.show();
            } else {
                try {
                    preparedStatement = connection.prepareStatement("UPDATE students SET course_form= ? WHERE id= ?");
                    preparedStatement.setBinaryStream(1, CourseFileInputStream, lenCourse);
                    preparedStatement.setInt(2, students.getId());
                    preparedStatement.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("successful");
                    alert.show();
                    Stage stage = (Stage) imageDocument.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }

        if (classIntent.equals("lga")) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement;


            if (LGAFileInputStream == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("make sure you have uploaded the required document");
                alert.show();
            } else {
                try {
                    preparedStatement = connection.prepareStatement("UPDATE students SET lga_certificate= ? WHERE id= ?");
                    preparedStatement.setBinaryStream(1, LGAFileInputStream, lenLGA);
                    preparedStatement.setInt(2, students.getId());
                    preparedStatement.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("successful");
                    alert.show();
                    Stage stage = (Stage) imageDocument.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }

        if (classIntent.equals("medical")) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement;


            if (MedicalFileInputStream == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("make sure you have uploaded the required document");
                alert.show();
            } else {
                try {
                    preparedStatement = connection.prepareStatement("UPDATE students SET medical_certificate= ? WHERE id= ?");
                    preparedStatement.setBinaryStream(1, MedicalFileInputStream, lenMedical);
                    preparedStatement.setInt(2, students.getId());
                    preparedStatement.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("successful");
                    alert.show();
                    Stage stage = (Stage) imageDocument.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }

        if (classIntent.equals("birth_certificate")) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement;


            if (BirthFileInputStream == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("make sure you have uploaded the required document");
                alert.show();
            } else {
                try {
                    preparedStatement = connection.prepareStatement("UPDATE students SET birth_certificate= ? WHERE id= ?");
                    preparedStatement.setBinaryStream(1, BirthFileInputStream, lenBirth);
                    preparedStatement.setInt(2, students.getId());
                    preparedStatement.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("successful");
                    alert.show();
                    Stage stage = (Stage) imageDocument.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }

        if (classIntent.equals("school_fees")) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement;


            if (SchoolFileInputStream == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("make sure you have uploaded the required document");
                alert.show();
            } else {
                try {
                    preparedStatement = connection.prepareStatement("UPDATE students SET school_fees= ? WHERE id= ?");
                    preparedStatement.setBinaryStream(1, SchoolFileInputStream, lenSchool);
                    preparedStatement.setInt(2, students.getId());
                    preparedStatement.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("successful");
                    alert.show();
                    Stage stage = (Stage) imageDocument.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }



    }

    public void onClickOpenDocument(ActionEvent event){
        System.out.println(classIntent);

        if(classIntent.equals("admission")){

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(ext1, ext2);
            File file = fileChooser.showOpenDialog(imageDocument.getScene().getWindow());


            BufferedImage bufferedImage;
            try {
                if (file != null) {

                    bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageDocument.setImage(image);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int len = (int) file.length();

                    admissionFileInputStream = fileInputStream;
                    lenAdmission = len;
                } else {
                    //do nothing;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else if(classIntent.equals("olevel")){
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(ext1, ext2);
            File file = fileChooser.showOpenDialog(imageDocument.getScene().getWindow());


            BufferedImage bufferedImage;
            try {
                if (file != null) {
                    bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageDocument.setImage(image);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int len = (int) file.length();

                    OlevelFileInputStream = fileInputStream;
                    lenOlevel = len;
                } else {
                    //do nothing
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(classIntent.equals("guarantor")){
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(ext1, ext2);
            File file = fileChooser.showOpenDialog(imageDocument.getScene().getWindow());


            BufferedImage bufferedImage;
            try {
                if (file != null) {
                    bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageDocument.setImage(image);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int len = (int) file.length();

                    GuarantorFileInputStream = fileInputStream;
                    lenGuarantor = len;

                } else {
                    //do nothing
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(classIntent.equals("jamb")){

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(ext1, ext2);
            File file = fileChooser.showOpenDialog(imageDocument.getScene().getWindow());


            BufferedImage bufferedImage;
            try {
                if (file != null) {
                    bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageDocument.setImage(image);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int len = (int) file.length();

                    JambFileInputStream = fileInputStream;
                    lenJamb = len;
                } else {
                    //do nothing
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        else if(classIntent.equals("biodata")){

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(ext1, ext2);
            File file = fileChooser.showOpenDialog(imageDocument.getScene().getWindow());


            BufferedImage bufferedImage;
            try {
                if (file != null) {
                    bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageDocument.setImage(image);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int len = (int) file.length();

                    BiodataFileInputStream = fileInputStream;
                    lenBiodata = len;
                } else {
                    //do nothing
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        else if(classIntent.equals("course")){

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(ext1, ext2);
            File file = fileChooser.showOpenDialog(imageDocument.getScene().getWindow());


            BufferedImage bufferedImage;
            try {
                if (file != null) {
                    bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageDocument.setImage(image);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int len = (int) file.length();

                    CourseFileInputStream = fileInputStream;
                    lenCourse = len;
                } else {
                    //do nothing
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        else if(classIntent.equals("lga")){

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(ext1, ext2);
            File file = fileChooser.showOpenDialog(imageDocument.getScene().getWindow());


            BufferedImage bufferedImage;
            try {
                if (file != null) {
                    bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageDocument.setImage(image);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int len = (int) file.length();

                    LGAFileInputStream = fileInputStream;
                    lenLGA = len;
                } else {
                    //do nothing
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        else if(classIntent.equals("medical")){

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(ext1, ext2);
            File file = fileChooser.showOpenDialog(imageDocument.getScene().getWindow());


            BufferedImage bufferedImage;
            try {
                if (file != null) {
                    bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageDocument.setImage(image);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int len = (int) file.length();

                    MedicalFileInputStream = fileInputStream;
                    lenMedical = len;
                } else {
                    //do nothing
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        else if(classIntent.equals("school_fees")){

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(ext1, ext2);
            File file = fileChooser.showOpenDialog(imageDocument.getScene().getWindow());


            BufferedImage bufferedImage;
            try {
                if (file != null) {
                    bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageDocument.setImage(image);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int len = (int) file.length();

                    SchoolFileInputStream = fileInputStream;
                    lenSchool = len;
                } else {
                    //do nothing
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        else if(classIntent.equals("birth_certificate")){

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(ext1, ext2);
            File file = fileChooser.showOpenDialog(imageDocument.getScene().getWindow());


            BufferedImage bufferedImage;
            try {
                if (file != null) {
                    bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageDocument.setImage(image);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    int len = (int) file.length();

                    BirthFileInputStream = fileInputStream;
                    lenBirth = len;
                } else {
                    //do nothing
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }








}