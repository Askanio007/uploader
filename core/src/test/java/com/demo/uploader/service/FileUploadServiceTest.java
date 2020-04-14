package com.demo.uploader.service;

import com.demo.uploader.service.impl.FileUploadServiceImpl;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUploadServiceTest extends BaseUploadServiceTest {

    private FileUploadService fileUploadService;
    private final List<MultipartFile> mockMultipartFileList = new ArrayList<>();

    public void init() throws IOException {
        mockMultipartFileList.add(getMockMultipartFile("Test1", "src/test/resources/test.jpg"));
        mockMultipartFileList.add(getMockMultipartFile("Test2", "src/test/resources/test1.jpg"));
        mockMultipartFileList.add(getMockMultipartFile("Test3", "src/test/resources/test2.jpg"));
        fileUploadService = new FileUploadServiceImpl(getPath());
    }

    @Test
    public void testUploadFile() throws IOException {
        fileUploadService.uploadFile(mockMultipartFileList);
        checkCountFiles(3, fileUploadService.getFolderName());

    }

    private MultipartFile getMockMultipartFile(String name, String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        return new MockMultipartFile("user-file", name, "image/jpeg", IOUtils.toByteArray(fis));
    }
}
