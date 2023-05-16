package com.example.invoicemanager.libs.Error;

public class CantBeChangedException extends Exception{

    public CantBeChangedException(){
        super();
    }

    public CantBeChangedException(String msg){
        super(msg);
    }
}
