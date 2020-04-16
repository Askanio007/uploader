package com.demo.uploader.controller;

import com.demo.uploader.http.Response;
import com.demo.uploader.model.ImageModel;
import com.demo.uploader.model.ImageResponseModel;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ImageController.class)
public class ImageControllerTest {

    private final static List<ImageResponseModel> EXPECTED_LIST = Arrays.asList(
            new ImageResponseModel(true, "test1", null),
            new ImageResponseModel(true, "test2", null)
    );
    private final static Response EXPECTED_RESPONSE = new Response(true, EXPECTED_LIST, null);
    private final static String API_URL = "/api/v1/images";

    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected ImageService imageService;

    @Test
    public void testStringUpload() throws Exception {
        ImageModel model = new ImageModel();
        model.setType(ImageSourceType.BASE64);
        model.setCodes(Arrays.asList("test1", "test2"));
        when(imageService.upload(model)).thenReturn(EXPECTED_LIST);
        checkRequest(post(API_URL), model, APPLICATION_JSON_VALUE);
    }

    @Test
    public void testFileUpload() throws Exception {
        MultipartFile[] files = new MultipartFile[0];
        when(imageService.upload(files)).thenReturn(EXPECTED_LIST);
        checkRequest(post(API_URL), files, MULTIPART_FORM_DATA_VALUE);
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
                .andExpect(content().json(toJson(EXPECTED_RESPONSE)));
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
