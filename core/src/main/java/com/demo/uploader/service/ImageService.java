package com.demo.uploader.service;

import com.demo.uploader.model.ImageModel;
import com.demo.uploader.model.ImageResponseModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<ImageResponseModel> upload(MultipartFile[] files);
    List<ImageResponseModel> upload(ImageModel model);
}
