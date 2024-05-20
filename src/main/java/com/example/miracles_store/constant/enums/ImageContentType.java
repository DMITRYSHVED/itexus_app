package com.example.miracles_store.constant.enums;

import java.util.Arrays;

public enum ImageContentType {

    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif");

    private final String contentType;

    ImageContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public static boolean isValidContentType(String contentType) {
        return Arrays.stream(values())
                .anyMatch(type -> type.getContentType().equalsIgnoreCase(contentType));
    }
}
