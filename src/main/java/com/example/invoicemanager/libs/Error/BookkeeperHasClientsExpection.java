package com.example.invoicemanager.libs.Error;

public class BookkeeperHasClientsExpection extends Exception{
    public BookkeeperHasClientsExpection(){
        super();
    }

    public BookkeeperHasClientsExpection(String msg){
        super(msg);
    }
}
