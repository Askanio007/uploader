package com.demo.uploader.service;

import com.demo.uploader.model.ImageResponseModel;
import com.demo.uploader.service.impl.UrlUploadServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UrlUploadServiceTest extends BaseUploadServiceTest {
    private final static String GOOD_URL = "https://miro.medium.com/max/2496/1*ITdpQIpyOs-_1LwQ4cnlag.png";
    private final static String BAD_URL = "asdaasdro.medium.com/max/2496/1*ITdpQIpyOs-_1LwQ4cnlag.png";

    private StringUploadService urlUploadService;

    @Override
    protected void init() throws IOException {
        urlUploadService = new UrlUploadServiceImpl(getPath());
    }

    @Test
    public void testUploadFromUrl() throws IOException {
        List<ImageResponseModel> results = urlUploadService.uploadFile(Arrays.asList(GOOD_URL));
        for (ImageResponseModel model : results) {
            Assertions.assertTrue(model.isSuccess());
        }
        checkCountFiles(1, urlUploadService.getFolderName());
    }

    @Test
    public void testFailedUploadFromUrl() throws IOException {
        List<ImageResponseModel> results = urlUploadService.uploadFile(Arrays.asList(BAD_URL));
        for (ImageResponseModel model : results) {
            Assertions.assertFalse(model.isSuccess());
            Assertions.assertEquals("URI is not absolute: " + BAD_URL, model.getError());
        }
        checkCountFiles(0, "");
    }
}
