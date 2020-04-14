package com.demo.uploader.service;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseUploadServiceTest {

    private final String path ="/home/user/ing/test";

    @BeforeEach
    public void setUp() throws IOException {
        Files.createDirectories(Paths.get(path));
        init();
    }

    @AfterEach
    public void after() throws IOException {
        FileUtils.deleteDirectory(new File(path));
    }

    protected void checkCountFiles(int count, String folderName) throws IOException {
        List<File> files = Files.list(Paths.get(path + "/" + folderName))
                .map(Path::toFile)
                .collect(Collectors.toList());
        Assertions.assertEquals(count, files.size());
    }

    public String getPath() {
        return path;
    }

    protected abstract void init() throws IOException;


}
