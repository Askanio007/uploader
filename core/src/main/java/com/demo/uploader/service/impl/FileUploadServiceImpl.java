package com.demo.uploader.service.impl;

import com.demo.uploader.exception.IllegalImageFileException;
import com.demo.uploader.service.FileUploadService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileUploadServiceImpl extends UploadServiceImpl<MultipartFile> implements FileUploadService {

    @Getter(AccessLevel.PROTECTED)
    private final String path;

    public FileUploadServiceImpl(@Value("${image.from.file.path}") String path) throws IOException {
        Files.createDirectories(Paths.get(path));
        this.path = path;
    }

    @Override
    protected void proceedUpload(MultipartFile image) {
        try {
            validateContentType(image);
            image.transferTo(getWritePath(RandomStringUtils.randomAlphanumeric(15)));
        } catch (IOException e) {
            log.error("Failed decode or saving file", e);
        }
    }

    private void validateContentType(MultipartFile image) {
        String contentType = image.getContentType();
        if (contentType == null || !contentType.contains("image")) {
            throw new IllegalImageFileException(image.getOriginalFilename(), contentType);
        }
    }

    @Override
    protected String getSource(MultipartFile image) {
        return image.getOriginalFilename();
    }
}
