package com.chiikawa.demo.exception.model;

public class UnprocessableEntityException extends RuntimeException{
    public UnprocessableEntityException(String message){
        super(message);
    }
}
