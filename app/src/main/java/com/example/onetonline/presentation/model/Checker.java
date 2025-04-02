package com.example.onetonline.presentation.model;

import com.sanctionco.jmail.JMail;

public class Checker {
    public static boolean checkEmail(String email){
        if(JMail.isValid(email)){
            return true;
        }
        return false;
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
