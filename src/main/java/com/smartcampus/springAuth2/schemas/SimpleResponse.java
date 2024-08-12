package com.smartcampus.springAuth2.schemas;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleResponse<T> {
    private Integer code;
    private Boolean ok;
    private String message;
    private String error;
    private T value;
}
