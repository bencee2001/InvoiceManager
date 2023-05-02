package com.example.invoicemanager.libs.Error;

public class NoSuchBookkeeperException extends Exception{

    NoSuchBookkeeperException(){
        super();
    }

    public NoSuchBookkeeperException(String msg){
        super(msg);
    }
}
