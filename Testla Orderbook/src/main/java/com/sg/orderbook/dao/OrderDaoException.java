/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.orderbook.dao;

public class OrderDaoException extends Exception {
    
        public OrderDaoException(String message){
        super(message);
    }
    
    public OrderDaoException(String message, Throwable cause){
        super(message, cause);
    }
    
}
