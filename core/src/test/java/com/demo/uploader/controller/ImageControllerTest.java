package com.demo.uploader.controller;

import com.demo.uploader.http.Response;
import com.demo.uploader.model.ImageModel;
import com.demo.uploader.model.ImageSourceType;
import com.demo.uploader.service.ImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ImageController.class)
public class ImageControllerTest {

    private final static Response CORRECT_RESPONSE = new Response(true, "upload successfully", null);
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected ImageService imageService;

    @Test
    public void testStringUpload() throws Exception {
        ImageModel model = new ImageModel();
        model.setType(ImageSourceType.BASE64);
        model.setCodes(Arrays.asList("test", "test"));
        checkRequest(post("/images"), model, APPLICATION_JSON_VALUE);
    }

    @Test
    public void testFileUpload() throws Exception {
        checkRequest(post("/images"), new MultipartFile[1], MULTIPART_FORM_DATA_VALUE);
    }

    private void checkRequest(MockHttpServletRequestBuilder builder, Object body, String contentType) throws Exception {
        builder = builder.contentType(contentType);
        String bodyString = toJson(body);
        if (bodyString != null) {
            builder = builder.content(bodyString);
        }
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(CORRECT_RESPONSE)));
    }

    public static String toJson(Object ob) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(ob);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

}
