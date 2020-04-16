package com.demo.uploader.service;

import com.demo.uploader.model.ImageResponseModel;
import com.demo.uploader.service.impl.FileUploadServiceImpl;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileUploadServiceTest extends BaseUploadServiceTest {

    private FileUploadService fileUploadService;

    public void init() throws IOException  {
        fileUploadService = new FileUploadServiceImpl(getPath());
    }

    @Test
    public void testUploadFile() throws IOException {
        List<MultipartFile> mockMultipartFileList = Arrays.asList(
                getMockMultipartFile("Test1", "src/test/resources/test.jpg", "image/jpeg"),
                getMockMultipartFile("Test2", "src/test/resources/test1.jpg", "image/jpeg"),
                getMockMultipartFile("Test3", "src/test/resources/test2.jpg", "image/jpeg")
        );

        List<ImageResponseModel> result = fileUploadService.uploadFile(mockMultipartFileList);
        for (ImageResponseModel model : result) {
            Assertions.assertTrue(model.isSuccess());
        }
        checkCountFiles(3, fileUploadService.getFolderName());
    }

    @Test
    public void testFailedUploadFile() throws IOException {
        List<MultipartFile> mockMultipartFileList = Arrays.asList(
                getMockMultipartFile("Test1", "src/test/resources/test.jpg", "text/plain"),
                getMockMultipartFile("Test2", "src/test/resources/test1.jpg", "text/plain"),
                getMockMultipartFile("Test3", "src/test/resources/test2.jpg", "text/plain")
        );

        List<ImageResponseModel> result = fileUploadService.uploadFile(mockMultipartFileList);
        for (ImageResponseModel model : result) {
            Assertions.assertFalse(model.isSuccess());
            Assertions.assertEquals("File is not an image: " + model.getImage() + "; contentType: text/plain", model.getError());
        }
        checkCountFiles(0, "");
    }

    private MultipartFile getMockMultipartFile(String name, String path, String contentType) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        return new MockMultipartFile("user-file", name, contentType, IOUtils.toByteArray(fis));
    }
}
