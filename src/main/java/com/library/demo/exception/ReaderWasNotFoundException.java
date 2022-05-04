package com.library.demo.exception;

public class ReaderWasNotFoundException extends RuntimeException{

    public ReaderWasNotFoundException(String message){
        super(message);
    }
}
