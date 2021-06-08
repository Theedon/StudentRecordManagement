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
    private Image passport;
    private Image admission_img;
    private Image olevel_img;
    private Image guarantor_img;
    private Image jamb_img;



    public Students(int serialNumber, int id, String firstName, String lastName, String email, String gender,
                    Image passport, Image admission_img, Image olevel_img,
                    Image guarantor_img, Image jamb_img) {
        this.serialNumber = serialNumber;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email= email;
        this.gender = gender;
        this.student_class=  student_class;
        this.passport = passport;
        this.admission_img= admission_img;
        this.olevel_img= olevel_img;
        this.guarantor_img= guarantor_img;
        this.jamb_img= jamb_img;
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


    public Image getPassport() {
        return passport;
    }

    public void setPassport(Image passport) {
        this.passport = passport;
    }

    public Image getAdmission_img() {
        return admission_img;
    }

    public void setAdmission_img(Image admission_img) {
        this.admission_img = admission_img;
    }

    public Image getOlevel_img() {
        return olevel_img;
    }

    public void setOlevel_img(Image olevel_img) {
        this.olevel_img = olevel_img;
    }

    public Image getGuarantor_img() {
        return guarantor_img;
    }

    public void setGuarantor_img(Image guarantor_img) {
        this.guarantor_img = guarantor_img;
    }

    public Image getJamb_img() {
        return jamb_img;
    }

    public void setJamb_img(Image jamb_img) {
        this.jamb_img = jamb_img;
    }


}
