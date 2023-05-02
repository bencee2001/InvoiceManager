package com.example.invoicemanager.libs.Error;

public class NoSuchUserException extends Exception{
    NoSuchUserException(){
        super();
    }

    public NoSuchUserException(String msg){
        super(msg);
    }
}
