package com.demo.uploader.http;

import com.demo.uploader.model.ImageResponseModel;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

@Data
public class Response {
    private boolean success;
    private List<ImageResponseModel> data;
    private String error;

    public Response(boolean success, List<ImageResponseModel> data, String error) {
        this.success = success;
        this.error = error;
        this.data = data;
    }

    public static ResponseEntity<Response> ok(List<ImageResponseModel> data) {
        return ResponseEntity.ok(new Response(true, data, null));
    }

    public static ResponseEntity<Response> error(String error) {
        return ResponseEntity.ok(new Response(false, Collections.emptyList(), error));
    }
}
