package com.example.onetonline.business;

public class Checker {
    public static boolean checkEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
    }

    public static boolean checkPassLen(String password){
        if(password.length()<6){
            return false;
        }
        return true;
    }

    public static boolean checkOTPLen(String otp){
        if(otp.length()<6){
            return false;
        }
        return true;
    }

    public static boolean checkConfirmPassword(String password, String confirmPassword){
        if(password.equals(confirmPassword)){
            return true;
        }
        return false;
    }

    public static boolean checkUserNameLen(String userName){
        if(userName.length()<5){
            return false;
        }
        return true;
    }
}
