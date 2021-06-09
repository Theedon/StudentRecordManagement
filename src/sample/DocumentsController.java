package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DocumentsController implements Initializable {

    Image admission = null;
    Image olevel = null;
    Image guarantor = null;
    Image jamb = null;
    ArrayList<Image> images = new ArrayList<>();
    Students student;
    Stage stage;

    FileInputStream admissionFileInputStream;
    int lenAdmission;

    FileInputStream OlevelFileInputStream;
    int lenOlevel;

    FileInputStream GuarantorFileInputStream;
    int lenGuarantor;

    FileInputStream JambFileInputStream;
    int lenJamb;

    @FXML
    private ImageView admission_view;

    @FXML
    private ImageView olevel_view;

    @FXML
    private ImageView guarantor_view;

    @FXML
    private ImageView jamb_view;

    @FXML
    private Button btnAdmission;

    @FXML
    private Button btnOlevel;

    @FXML
    private Button btnGuarantor;

    @FXML
    private Button btnJamb;

    @FXML
    private Button btnSubmit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        admission_view.setImage(null);
        olevel_view.setImage(null);
        guarantor_view.setImage(null);
        jamb_view.setImage(null);
    }


    public void sendData(Students currentClickedStudent, String intent) {
        student = currentClickedStudent;


        if (intent.equals("view")) {
            getImages();
            admission_view.setImage(admission);
            olevel_view.setImage(olevel);
            guarantor_view.setImage(guarantor);
            jamb_view.setImage(jamb);
            btnAdmission.setVisible(false);
            btnOlevel.setVisible(false);
            btnGuarantor.setVisible(false);
            btnJamb.setVisible(false);
            btnSubmit.setVisible(false);


        }

        if (intent.equals("upload")) {

            if (admission != null && olevel != null && guarantor != null &&
                    jamb != null) {
                admission_view.setImage(admission);
                olevel_view.setImage(olevel);
                guarantor_view.setImage(guarantor);
                jamb_view.setImage(jamb);
            }


        }


    }

    public void onClickAdmission(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file = fileChooser.showOpenDialog(btnJamb.getScene().getWindow());


        BufferedImage bufferedImage;
        try {
            if (file != null) {

                bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                admission_view.setImage(image);
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

    public void onClickOlevel(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file = fileChooser.showOpenDialog(btnJamb.getScene().getWindow());


        BufferedImage bufferedImage;
        try {
            if (file != null) {
                bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                olevel_view.setImage(image);
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

    public void onClickGuarantor(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file = fileChooser.showOpenDialog(btnJamb.getScene().getWindow());


        BufferedImage bufferedImage;
        try {
            if (file != null) {
                bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                guarantor_view.setImage(image);
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

    public void onClickJamb(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file = fileChooser.showOpenDialog(btnJamb.getScene().getWindow());


        BufferedImage bufferedImage;
        try {
            if (file != null) {
                bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                jamb_view.setImage(image);
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

    public void onClickSubmit(ActionEvent event) {

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        PreparedStatement preparedStatement;


        if (admissionFileInputStream == null || OlevelFileInputStream == null ||
                GuarantorFileInputStream == null || JambFileInputStream == null) {
            System.out.println("suspect");
            System.out.println(admissionFileInputStream);
            System.out.println(OlevelFileInputStream);
            System.out.println(GuarantorFileInputStream);
            System.out.println(JambFileInputStream);
        } else {
            try {
                preparedStatement = connection.prepareStatement("UPDATE students SET admission_img= ?, olevel_img=?, guarantor_img=?, jamb_img=? WHERE id= ?");
                preparedStatement.setBinaryStream(1, admissionFileInputStream, lenAdmission);
                preparedStatement.setBinaryStream(2, OlevelFileInputStream, lenOlevel);
                preparedStatement.setBinaryStream(3, GuarantorFileInputStream, lenGuarantor);
                preparedStatement.setBinaryStream(4, JambFileInputStream, lenJamb);
                preparedStatement.setInt(5, student.getId());
                preparedStatement.execute();
                Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("successful");
                alert.show();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }


    public void getImages() {
        int id = student.getId();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();


        String query = "SELECT admission_img, olevel_img, guarantor_img, jamb_img FROM students WHERE id= " + id + "";
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

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
