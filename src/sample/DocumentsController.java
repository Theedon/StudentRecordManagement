package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DocumentsController implements Initializable {
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

    }

    public void sendData(Students currentClickedStudent, String intent){
        student= currentClickedStudent;

        if(intent.equals("view")){
            admission_view.setImage(student.getAdmission_img());
            olevel_view.setImage(student.getOlevel_img());
            guarantor_view.setImage(student.getGuarantor_img());
            jamb_view.setImage(student.getJamb_img());
            btnAdmission.setVisible(false);
            btnOlevel.setVisible(false);
            btnGuarantor.setVisible(false);
            btnJamb.setVisible(false);
            btnSubmit.setVisible(false);




        }

        if(intent.equals("upload")){

            if(student.getAdmission_img()!=null && student.getOlevel_img()!=null && student.getGuarantor_img()!=null &&
            student.getJamb_img() != null){
                admission_view.setImage(student.getAdmission_img());
                olevel_view.setImage(student.getOlevel_img());
                guarantor_view.setImage(student.getGuarantor_img());
                jamb_view.setImage(student.getJamb_img());
            }


        }



    }

    public void onClickAdmission(ActionEvent event){
        FileChooser fileChooser= new FileChooser();
        FileChooser.ExtensionFilter ext1= new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
        FileChooser.ExtensionFilter ext2= new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file= fileChooser.showOpenDialog(btnJamb.getScene().getWindow());


        BufferedImage bufferedImage;
        try {
            bufferedImage= ImageIO.read(file);
            Image image= SwingFXUtils.toFXImage(bufferedImage, null);
            admission_view.setImage(image);
            FileInputStream fileInputStream= new FileInputStream(file);
            int len= (int) file.length();

            admissionFileInputStream= fileInputStream;
            lenAdmission= len;


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void onClickOlevel(ActionEvent event){

        FileChooser fileChooser= new FileChooser();
        FileChooser.ExtensionFilter ext1= new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
        FileChooser.ExtensionFilter ext2= new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file= fileChooser.showOpenDialog(btnJamb.getScene().getWindow());


        BufferedImage bufferedImage;
        try {
            bufferedImage= ImageIO.read(file);
            Image image= SwingFXUtils.toFXImage(bufferedImage, null);
            olevel_view.setImage(image);
            FileInputStream fileInputStream= new FileInputStream(file);
            int len= (int) file.length();

            OlevelFileInputStream= fileInputStream;
            lenOlevel= len;


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onClickGuarantor(ActionEvent event){

        FileChooser fileChooser= new FileChooser();
        FileChooser.ExtensionFilter ext1= new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
        FileChooser.ExtensionFilter ext2= new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file= fileChooser.showOpenDialog(btnJamb.getScene().getWindow());


        BufferedImage bufferedImage;
        try {
            bufferedImage= ImageIO.read(file);
            Image image= SwingFXUtils.toFXImage(bufferedImage, null);
            guarantor_view.setImage(image);
            FileInputStream fileInputStream= new FileInputStream(file);
            int len= (int) file.length();

            GuarantorFileInputStream= fileInputStream;
            lenGuarantor= len;


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onClickJamb(ActionEvent event){

        FileChooser fileChooser= new FileChooser();
        FileChooser.ExtensionFilter ext1= new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
        FileChooser.ExtensionFilter ext2= new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file= fileChooser.showOpenDialog(btnJamb.getScene().getWindow());


        BufferedImage bufferedImage;
        try {
            bufferedImage= ImageIO.read(file);
            Image image= SwingFXUtils.toFXImage(bufferedImage, null);
            jamb_view.setImage(image);
            FileInputStream fileInputStream= new FileInputStream(file);
            int len= (int) file.length();

            JambFileInputStream= fileInputStream;
            lenJamb= len;


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public  void onClickSubmit(ActionEvent event){

        DatabaseConnection databaseConnection= new DatabaseConnection();
        Connection connection= databaseConnection.getConnection();
        PreparedStatement preparedStatement;

        if(student.getGuarantor_img()==null || student.getOlevel_img()==null ||
                student.getGuarantor_img()==null || student.getJamb_img()==null){

        }
        else {
            try {
                preparedStatement= connection.prepareStatement("UPDATE students SET admission_img= ?, olevel_img=?, guarantor_img=?, jamb_img=? WHERE id= ?");
                preparedStatement.setBinaryStream(1, admissionFileInputStream, lenAdmission);
                preparedStatement.setBinaryStream(2, OlevelFileInputStream, lenOlevel);
                preparedStatement.setBinaryStream(3, GuarantorFileInputStream, lenGuarantor);
                preparedStatement.setBinaryStream(4, JambFileInputStream, lenJamb);
                preparedStatement.setInt(5, student.getId());
                preparedStatement.execute();
                System.out.println("successful");
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    }


}
