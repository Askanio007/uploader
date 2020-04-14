package com.demo.uploader.util;

import com.demo.uploader.exception.IllegalBase64Exception;

public class Base64Util {

    public static String getImageCode(String base64) {
        try {
            String[] parts = base64.split(",");
            checkParts(parts, base64);

            String[] partsType = parts[0].split(":");
            checkParts(partsType, base64);

            if (!partsType[1].contains("image")) {
                throw new IllegalBase64Exception(base64);
            }
            return parts[1];
        } catch (IllegalBase64Exception ibe) {
            throw ibe;
        } catch (Exception e) {
            throw new IllegalBase64Exception(base64);
        }
    }

    private static void checkParts(String[] parts, String base64) throws IllegalBase64Exception {
        if (parts.length != 2) {
            throw new IllegalBase64Exception(base64);
        }
    }

}
