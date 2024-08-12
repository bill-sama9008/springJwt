package com.smartcampus.springAuth2.utils;

import com.smartcampus.springAuth2.schemas.ListSimpleResponse;
import com.smartcampus.springAuth2.schemas.SimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class Common {

    public static <T>ResponseEntity<ListSimpleResponse<T>> createListResponse(List<T> value, String message, HttpStatus status) {
        return new ResponseEntity<>(ListSimpleResponse.<T>builder()
                .ok(true)
                .code(status.value())
                .message(message)
                .value(value)
                .build(), status);
    }

    public static <T>ResponseEntity<SimpleResponse<T>> createSimpleResponse(T value, String message, HttpStatus status) {
        return new ResponseEntity<>(SimpleResponse.<T>builder()
                .ok(true)
                .code(status.value())
                .message(message)
                .value(value)
                .build(), status);
    }
}
