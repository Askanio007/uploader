package com.demo.uploader.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private final static DateTimeFormatter folderPattern = DateTimeFormatter.ofPattern("yyyy_MM_dd");

    public static String getCurrentDateWithoutTime() {
        return folderPattern.format(LocalDateTime.now());
    }
}
