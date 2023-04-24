package com.example.invoicemanager.libs.Error;

public class NoSelectedRoleException extends Exception{
    NoSelectedRoleException(){
        super();
    }

    public NoSelectedRoleException(String msg){
        super(msg);
    }
}
