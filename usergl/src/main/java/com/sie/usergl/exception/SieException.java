package com.sie.usergl.exception;

import lombok.Data;

public class SieException extends RuntimeException{
    public  SieException(){
        super();
    }
    public SieException(String message){
        super(message);
    }
}
