package com.demo.uploader.service.impl;

import com.demo.uploader.service.StringUploadService;
import com.demo.uploader.util.Base64Util;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service("base64UploadService")
public class Base64UploadServiceImpl extends UploadServiceImpl<String> implements StringUploadService {

    @Getter(AccessLevel.PROTECTED)
    private final String path;

    public Base64UploadServiceImpl(@Value("${image.from.base64.path}") String path)  throws IOException {
        Files.createDirectories(Paths.get(path));
        this.path = path;
    }

    @Override
    protected void proceedUpload(String image) {
        String imageCode = Base64Util.getImageCode(image);
        byte[] imageByte = Base64Utils.decodeFromString(imageCode);
        writeFile(imageByte);
    }

    @Override
    protected String getSource(String image) {
        return image;
    }
}
