package com.example.miracles_store.util;

import org.springframework.http.MediaType;

public class ContentTypeValidator {

    public static boolean isImageContentType(String contentType) {
        return MediaType.IMAGE_PNG_VALUE.equals(contentType) || MediaType.IMAGE_JPEG_VALUE.equals(contentType);
    }
}
