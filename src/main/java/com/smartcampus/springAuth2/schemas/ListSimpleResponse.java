package com.smartcampus.springAuth2.schemas;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListSimpleResponse<T> {
    private Integer code;
    private Boolean ok;
    private String message;
    private String error;
    private List<T> value;
}
