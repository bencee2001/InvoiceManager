package com.example.invoicemanager.libs.Error;

public class LogedInUserDeleteException extends Exception{

    LogedInUserDeleteException(){
        super();
    }

    public LogedInUserDeleteException(String msg){
        super(msg);
    }
}
