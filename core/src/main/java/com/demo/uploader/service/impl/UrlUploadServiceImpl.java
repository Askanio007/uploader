package com.demo.uploader.service.impl;

import com.demo.uploader.service.StringUploadService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service("urlUploadService")
public class UrlUploadServiceImpl extends UploadServiceImpl<String> implements StringUploadService {

    private WebClient client;

    @Getter(AccessLevel.PROTECTED)
    private final String path;

    public UrlUploadServiceImpl(@Value("${image.from.url.path}") String path) throws IOException {
        Files.createDirectories(Paths.get(path));
        this.path = path;
    }

    @Override
    protected void proceedUpload(String image) {
        System.out.println("TEST");
        client = WebClient.builder()
                .baseUrl(image)
                .build();
        Mono<byte[]> bytes =  client
                .get()
                .exchange()
                .flatMap(res -> res.bodyToMono(ByteArrayResource.class))
                .map(ByteArrayResource::getByteArray);
        writeFile(bytes.block());
    }

    @Override
    protected String getSource(String image) {
        return image;
    }
}
