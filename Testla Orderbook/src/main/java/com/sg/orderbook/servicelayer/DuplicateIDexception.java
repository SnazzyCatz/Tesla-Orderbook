package com.sg.orderbook.servicelayer;

public class DuplicateIDexception extends Exception {
    
    public DuplicateIDexception(String message){
        super(message);
    }
    
    public DuplicateIDexception(String message, Throwable cause){
        super(message, cause);
    }
    
}
