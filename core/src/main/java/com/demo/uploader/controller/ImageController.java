package com.demo.uploader.controller;

import com.demo.uploader.http.Response;
import com.demo.uploader.model.ImageModel;
import com.demo.uploader.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Response> processFile(@RequestBody MultipartFile[] files) {
        return Response.ok(imageService.upload(files));
    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<Response> processJson(@RequestBody ImageModel imageModel) {
        return Response.ok(imageService.upload(imageModel));
    }
}
