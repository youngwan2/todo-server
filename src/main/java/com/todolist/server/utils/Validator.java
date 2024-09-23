package com.todolist.server.utils;

import com.todolist.server.exception.InvalidException;

import java.util.regex.Pattern;

public class Validator {

    public Pattern pattern(String regex) {
        return Pattern.compile(regex);
    }

    public Boolean EmailValidator(String email) {
        final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        boolean isValidEmail = pattern(EMAIL_REGEX).matcher(email).matches();
        System.out.println(isValidEmail);
        if (!isValidEmail) {
            throw new InvalidException("Invalid Email: " + email);
        }
        return true;
    }

    public Boolean UsernameValidator(String username) {
        final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,16}$";
        boolean isValidUsername = pattern(USERNAME_REGEX).matcher(username).matches();
        if (!isValidUsername) {
            throw new InvalidException("Invalid Username: " + username);
        }
        return true;

    }

    public Boolean PasswordValidator(String password) {
        final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

        boolean isValidPassword = pattern(PASSWORD_REGEX).matcher(password).matches();
        if (!isValidPassword) {
            throw new InvalidException("Invalid password: " + password);
        }
        return true;
    }
}
