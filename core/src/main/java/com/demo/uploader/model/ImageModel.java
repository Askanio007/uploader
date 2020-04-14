package com.demo.uploader.model;

import lombok.Data;

import java.util.List;

@Data
public class ImageModel {
    private ImageSourceType type;
    private List<String> codes;
}
