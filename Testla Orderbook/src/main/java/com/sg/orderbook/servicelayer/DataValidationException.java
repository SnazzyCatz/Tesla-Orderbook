package com.sg.orderbook.servicelayer;

public class DataValidationException extends Exception {
    
    public DataValidationException(String message){
        super(message);
    }
    
    public DataValidationException(String message, Throwable cause){
        super(message, cause);
    }
    
}
