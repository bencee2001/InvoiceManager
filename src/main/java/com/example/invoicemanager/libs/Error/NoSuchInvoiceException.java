package com.example.invoicemanager.libs.Error;

public class NoSuchInvoiceException extends Exception{

    NoSuchInvoiceException(){}

    public NoSuchInvoiceException(String msg){
        super(msg);
    }
}
