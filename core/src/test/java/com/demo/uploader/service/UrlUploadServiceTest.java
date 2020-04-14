package com.demo.uploader.service;

import com.demo.uploader.service.impl.UrlUploadServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class UrlUploadServiceTest extends BaseUploadServiceTest {
    private final static String GOOD_URL = "https://miro.medium.com/max/2496/1*ITdpQIpyOs-_1LwQ4cnlag.png";
    private final static String BAD_URL = "asdaasdro.medium.com/max/2496/1*ITdpQIpyOs-_1LwQ4cnlag.png";

    private StringUploadService urlUploadService;

    @Override
    protected void init() {
        urlUploadService = new UrlUploadServiceImpl(getPath());
    }

    @Test
    public void testUploadFromUrl() throws IOException {
        urlUploadService.uploadFile(Arrays.asList(GOOD_URL));
        checkCountFiles(1, urlUploadService.getFolderName());
    }

    @Test
    public void testFailedUploadFromUrl() throws IOException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            urlUploadService.uploadFile(Arrays.asList(BAD_URL));
        });
        Assertions.assertEquals(exception.getMessage(), "URI is not absolute: " + BAD_URL);
        checkCountFiles(0, "");
    }
}
