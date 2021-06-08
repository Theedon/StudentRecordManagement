package sample;

import javafx.scene.image.Image;

public class Students {
    private int serialNumber;
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String student_class;
    private Image image;


    public Students(int serialNumber, int id, String firstName, String lastName, String email, String gender, String student_class, Image image) {
        this.serialNumber = serialNumber;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email= email;
        this.gender = gender;
        this.student_class=  student_class;
        this.image = image;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email= email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
