package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)////jodi data ti NON_NULL na hoi tahole dekhabe.
    private String field;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
}
