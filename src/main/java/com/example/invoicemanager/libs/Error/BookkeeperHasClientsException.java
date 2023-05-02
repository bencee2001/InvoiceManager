package com.example.invoicemanager.libs.Error;

public class BookkeeperHasClientsException extends Exception{
    public BookkeeperHasClientsException(){
        super();
    }

    public BookkeeperHasClientsException(String msg){
        super(msg);
    }
}
