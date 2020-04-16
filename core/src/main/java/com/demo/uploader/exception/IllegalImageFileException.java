package com.demo.uploader.exception;

public class IllegalImageFileException extends RuntimeException {

    public IllegalImageFileException(String fileName, String contentType) {
        super("File is not an image: " + fileName + "; contentType: " + contentType);
    }
}
