package sample;

public class ValidateInput {


        public static boolean ValidateEmail(String email){
            boolean isValidatedEmail= false;
            String regex= "^(.+)@(.+)$";
            if(email.matches(regex)){
                isValidatedEmail= true;
            }
            return isValidatedEmail;
        }



}
