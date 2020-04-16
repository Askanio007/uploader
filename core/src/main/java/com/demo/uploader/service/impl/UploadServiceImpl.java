package com.demo.uploader.service.impl;

import com.demo.uploader.model.ImageResponseModel;
import com.demo.uploader.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class UploadServiceImpl<T> implements UploadService<T> {

    private final static int CORRECT_FILENAME_LENGTH = 40;

    @Override
    public List<ImageResponseModel> uploadFile(List<T> source) {
        List<ImageResponseModel> items = new ArrayList<>();
        for (T image : source) {
            try {
                proceedUpload(image);
                items.add(new ImageResponseModel(true, getShortImageName(getSource(image)), null));
            } catch (Exception e) {
                items.add(new ImageResponseModel(false, getShortImageName(getSource(image)), e.getMessage()));
            }
        }
        return items;
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

    protected String getShortImageName(String name) {
        if (StringUtils.isBlank(name) || name.length() < CORRECT_FILENAME_LENGTH) {
            return name;
        }
        int countViewSymbols = CORRECT_FILENAME_LENGTH / 2;
        StringBuilder builder = new StringBuilder();
        builder.append(name, 0, countViewSymbols)
                .append("...")
                .append(name, name.length() - countViewSymbols - 1, name.length() - 1);
        return builder.toString();
    }

    protected abstract String getSource(T image);
    protected abstract String getPath();
    protected abstract void proceedUpload(T image);
}
