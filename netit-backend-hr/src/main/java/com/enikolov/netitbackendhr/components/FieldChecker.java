package com.enikolov.netitbackendhr.components;

import com.enikolov.netitbackendhr.services.data.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FieldChecker {
    @Autowired
    private UserDataService userDataService;

    public boolean isEmailValid(String email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()) return true;
        return false;
    }
    public boolean isPasswordValid(String password){
        //Password setup
        int minLength = 6;
        int maxLength = 10;

        if (password == null) return false;
        if (password.length() < minLength || password.length() > maxLength) return false;

        boolean containsUpperCase   = false;
        boolean containsLowerCase   = false;
        boolean containsDigit       = false;

        for(char ch: password.toCharArray()){
            if(Character.isUpperCase(ch)) containsUpperCase     = true;
            if(Character.isLowerCase(ch)) containsLowerCase     = true;
            if(Character.isDigit(ch)) containsDigit             = true;
        }
        return containsUpperCase && containsLowerCase && containsDigit;
    }
    public boolean isFullnameValid(String fullname){
        Pattern pattern = Pattern.compile("^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$");
        Matcher matcher = pattern.matcher(fullname);
        return matcher.matches();

    }
    public boolean isEmailFree(String email){
        return this.userDataService.isEmailRegistered(email);
    }
}
