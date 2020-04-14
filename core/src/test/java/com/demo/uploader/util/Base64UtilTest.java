package com.demo.uploader.util;

import com.demo.uploader.exception.IllegalBase64Exception;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Base64UtilTest {

    private final static String IMAGE_CODE = "asdasdasdas";
    private final static String CODE_BEFORE_IMAGE = "data:image/jpeg;base64," + IMAGE_CODE;

    @Test
    public void testGettingImageCode() {
        String code = Base64Util.getImageCode(CODE_BEFORE_IMAGE);
        Assertions.assertEquals(code, IMAGE_CODE);
    }

    @Test
    public void testFailedGettingImageCode() {
        Exception exception = Assertions.assertThrows(IllegalBase64Exception.class, () -> {
            Base64Util.getImageCode(IMAGE_CODE);
        });
        Assertions.assertEquals(exception.getMessage(), "Incorrect base64: " + IMAGE_CODE);
    }
}
