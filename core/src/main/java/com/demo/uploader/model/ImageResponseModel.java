package com.demo.uploader.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageResponseModel{
    private boolean success;
    private String image;
    private String error;
}
