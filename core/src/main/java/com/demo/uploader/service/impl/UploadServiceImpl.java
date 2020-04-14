package com.demo.uploader.service.impl;

import com.demo.uploader.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public abstract class UploadServiceImpl<T> implements UploadService<T> {

    @Override
    public void uploadFile(List<T> source) {
        for (T image : source) {
            proceedUpload(image);
        }
    }

    protected void writeFile(byte[] file) {
        try {
            if (file == null) {
                log.error("File is null");
                return;
            }
            String fileName = RandomStringUtils.randomAlphanumeric(15);
            Files.write(getWritePath(fileName), file);
        } catch (IOException e) {
            log.error("Failed saving image", e);
        }
    }

    protected Path getWritePath(String fileName) throws IOException {
        String folderPath = getPath() + "/" +  getFolderName();
        Files.createDirectories(Paths.get(folderPath));
        return Paths.get(folderPath, fileName);
    }

    protected abstract String getPath();
    protected abstract void proceedUpload(T image);
}
