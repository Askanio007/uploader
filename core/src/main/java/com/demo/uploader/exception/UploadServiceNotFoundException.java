package com.demo.uploader.exception;

import com.demo.uploader.model.ImageSourceType;

public class UploadServiceNotFoundException extends RuntimeException {

    public UploadServiceNotFoundException(ImageSourceType type) {
        super("Service not found for type: " + type);
    }
}
