package com.demo.uploader.service;

import com.demo.uploader.exception.UploadServiceNotFoundException;
import com.demo.uploader.model.ImageModel;
import com.demo.uploader.model.ImageSourceType;
import com.demo.uploader.service.impl.Base64UploadServiceImpl;
import com.demo.uploader.service.impl.FileUploadServiceImpl;
import com.demo.uploader.service.impl.ImageServiceImpl;
import com.demo.uploader.service.impl.UrlUploadServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class ImageServiceTest {
    private final static String TEST_FILE = "TEST";

    private ImageService imageService;
    private final StringUploadService urlUploadService = Mockito.mock(UrlUploadServiceImpl.class);
    private final StringUploadService base64UploadService = Mockito.mock(Base64UploadServiceImpl.class);
    private final FileUploadService fileUploadService = Mockito.mock(FileUploadServiceImpl.class);

    @BeforeEach
    public void setUp() {
        imageService = new ImageServiceImpl(urlUploadService, base64UploadService, fileUploadService);
    }

    @Test
    public void testUploadFromFile() {
        MultipartFile[] files = new MultipartFile[1];
        imageService.upload(files);
        ArgumentCaptor<List<MultipartFile>> fileCaptor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(fileUploadService).uploadFile(fileCaptor.capture());
        for (MultipartFile f : fileCaptor.getValue()) {
            Assertions.assertNull(f);
        }
    }

    @Test
    public void testUploadFromBase64() {
        testUploadFromImageModel(ImageSourceType.BASE64, base64UploadService);
    }

    @Test
    public void testUploadFromUrl() {
        testUploadFromImageModel(ImageSourceType.URL, urlUploadService);
    }

    @Test
    public void testFailedUploadFromString() {
        Exception exception = Assertions.assertThrows(UploadServiceNotFoundException.class, () -> {
            testUploadFromImageModel(null, urlUploadService);
        });
        Assertions.assertEquals(exception.getMessage(), "Service not found for type: " + null);
    }

    public void testUploadFromImageModel(ImageSourceType type, StringUploadService service) {
        List<String> files = new ArrayList<>();
        files.add(TEST_FILE);
        files.add(TEST_FILE);
        files.add(TEST_FILE);
        files.add(TEST_FILE);
        ImageModel model = new ImageModel();
        model.setType(type);
        model.setCodes(files);
        imageService.upload(model);
        ArgumentCaptor<List<String>> fileCaptor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(service).uploadFile(fileCaptor.capture());
        Assertions.assertEquals(fileCaptor.getValue().size(), files.size());
        for (String file : fileCaptor.getValue()) {
            Assertions.assertEquals(file, TEST_FILE);
        }
    }
}
