package com.example.invoicemanager.libs.Error;

public class UserAlreadyExistsException extends Exception{

    UserAlreadyExistsException(){
        super();
    }

    public UserAlreadyExistsException(String msg){
        super(msg);
    }
}
