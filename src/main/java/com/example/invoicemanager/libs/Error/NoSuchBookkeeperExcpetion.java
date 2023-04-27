package com.example.invoicemanager.libs.Error;

public class NoSuchBookkeeperExcpetion extends Exception{

    NoSuchBookkeeperExcpetion(){
        super();
    }

    public NoSuchBookkeeperExcpetion(String msg){
        super(msg);
    }
}
