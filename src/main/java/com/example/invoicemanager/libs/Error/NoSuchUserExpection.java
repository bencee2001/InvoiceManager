package com.example.invoicemanager.libs.Error;

public class NoSuchUserExpection extends Exception{
    NoSuchUserExpection(){
        super();
    }

    public NoSuchUserExpection(String msg){
        super(msg);
    }
}
