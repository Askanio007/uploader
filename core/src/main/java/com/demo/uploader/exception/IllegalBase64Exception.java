package com.demo.uploader.exception;

public class IllegalBase64Exception extends RuntimeException {

    public IllegalBase64Exception(String base64) {
        super("Incorrect base64: " + base64);
    }
}
