package com.chiikawa.demo.Exception.model;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String massage){
        super(massage);
    }
}
