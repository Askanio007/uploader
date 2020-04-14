package com.demo.uploader.service;

import com.demo.uploader.exception.IllegalBase64Exception;
import com.demo.uploader.service.impl.Base64UploadServiceImpl;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Base64UploadServiceTest extends BaseUploadServiceTest {

    private final static String BAD_TEST_CODE = "asdasdasd";
    private String goodBase64;
    private StringUploadService base64UploadService;

    public void init() throws IOException {
        FileInputStream fis = new FileInputStream("src/test/resources/imgBase64.txt");
        goodBase64 = IOUtils.toString(fis, StandardCharsets.UTF_8);
        base64UploadService = new Base64UploadServiceImpl(getPath());
    }

    @Test
    public void testUploadBase64Image() throws IOException {
        List<String> codes = new ArrayList<>();
        codes.add(goodBase64);
        codes.add(goodBase64);
        codes.add(goodBase64);
        base64UploadService.uploadFile(codes);
        checkCountFiles(3, base64UploadService.getFolderName());
    }

    @Test
    public void testIncorrectBase64Image() throws IOException {
        List<String> codes = new ArrayList<>();
        codes.add(BAD_TEST_CODE);

        Exception exception = Assertions.assertThrows(IllegalBase64Exception.class, () -> {
            base64UploadService.uploadFile(codes);
        });
        Assertions.assertEquals(exception.getMessage(), "Incorrect base64: " + BAD_TEST_CODE);
        checkCountFiles(0, "");
    }
}
