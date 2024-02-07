package com.gerson.springboot.error.springbooterror.exceptions;

//Para tratar las Exceptions
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){
        super(message); //Con super se la pasamos el message al padre y el padre con supper se lo pasa al exception
    }

}
