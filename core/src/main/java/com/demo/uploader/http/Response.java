package com.demo.uploader.http;

import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class Response {
    private boolean success;
    private String message;
    private Object data;
    private String error;

    public Response(boolean success, String message, String error) {
        this.success = success;
        this.message = message;
        this.error = error;
    }

    private Response(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public static ResponseEntity<Response> ok(String message) {
        return ResponseEntity.ok(new Response(true, message, null));
    }

    public static ResponseEntity<Response> ok(Object data) {
        return ResponseEntity.ok(new Response(true, data));
    }

    public static ResponseEntity<Response> error(String error) {
        return ResponseEntity.ok(new Response(false, null, error));
    }
}
