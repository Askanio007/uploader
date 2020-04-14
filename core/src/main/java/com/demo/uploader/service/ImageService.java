package com.demo.uploader.service;

import com.demo.uploader.model.ImageModel;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void upload(MultipartFile[] files);
    void upload(ImageModel model);
}
