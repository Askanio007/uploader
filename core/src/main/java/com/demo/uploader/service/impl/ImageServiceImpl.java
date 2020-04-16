package com.demo.uploader.service.impl;

import com.demo.uploader.exception.UploadServiceNotFoundException;
import com.demo.uploader.model.ImageModel;
import com.demo.uploader.model.ImageResponseModel;
import com.demo.uploader.model.ImageSourceType;
import com.demo.uploader.service.FileUploadService;
import com.demo.uploader.service.ImageService;
import com.demo.uploader.service.StringUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final StringUploadService urlUploadService;
    private final StringUploadService base64UploadService;
    private final FileUploadService fileUploadService;

    public ImageServiceImpl(StringUploadService urlUploadService,
                            StringUploadService base64UploadService,
                            FileUploadService fileUploadService) {
        this.urlUploadService = urlUploadService;
        this.base64UploadService = base64UploadService;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public List<ImageResponseModel> upload(MultipartFile[] files) {
        return fileUploadService.uploadFile(Arrays.asList(files));
    }

    @Override
    public List<ImageResponseModel> upload(ImageModel model) {
        StringUploadService service = getUploadService(model.getType());
        return service.uploadFile(model.getCodes());
    }

    private StringUploadService getUploadService(ImageSourceType type) {
        if (type == null) {
            throw new UploadServiceNotFoundException(type);
        }

        switch (type) {
            case BASE64: return base64UploadService;
            case URL: return urlUploadService;
            default: throw new UploadServiceNotFoundException(type);
        }
    }
}
