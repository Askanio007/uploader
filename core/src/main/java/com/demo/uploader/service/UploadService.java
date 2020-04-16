package com.demo.uploader.service;

import com.demo.uploader.model.ImageResponseModel;
import com.demo.uploader.util.DateUtil;

import java.util.List;

public interface UploadService<T> {
    List<ImageResponseModel> uploadFile(List<T> source);
    default String getFolderName() {
        return DateUtil.getCurrentDateWithoutTime();
    }
}
