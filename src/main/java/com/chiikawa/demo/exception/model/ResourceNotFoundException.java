package com.chiikawa.demo.exception.model;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String massage){
        super(massage);
    }
}
