package com.example.demo.view;

import com.example.demo.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class ResponseBuilder {
    private ResponseBuilder() {//jeno kew new diya kono object make korte na pare;
    };

    private static List<ErrorResponseDto> getCustomError(BindingResult result) {//for validation error.
        List<ErrorResponseDto> dtoList = new ArrayList<>();
        result.getFieldErrors().forEach(fieldError -> {//ami list of error gula ke for each loop er maddhome alada korechi.
            ErrorResponseDto dto = ErrorResponseDto.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage()).build();
            dtoList.add(dto);
        });
        return dtoList;
    }

    public static Response getFailureResponce(BindingResult result, String message) {//for getFailure response

        /*
        ekhane builder ki setter er kaj korlo????????????????????
         */
        return Response.builder()
                .message(message)
                .errors(getCustomError(result))//this paramiter my own making method i mean above getCustomError(BindingResult result); method
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .statusCode(HttpStatus.BAD_REQUEST.value())//like 404 error
                .timestamp(new Date().getTime())//kokhon url hit hosse sei current time ta
                .build();
    }

    public static Response getFailureResponce(HttpStatus status, String message) {//for getFailure response
        return Response.builder()
                .message(message)
                .status(status.getReasonPhrase())
                .statusCode(status.value())//like 404 error
                .timestamp(new Date().getTime())//kokhon url hit hosse sei current time ta
                .build();
    }

    public static Response getSuccessResponce(HttpStatus status, String message, Object content) {//for getSuccess response
        return Response.builder()
                .message(message)
                .status(status.getReasonPhrase())
                .content(content)
                .statusCode(status.value())//like 404 error
                .timestamp(new Date().getTime())//kokhon url hit hosse sei current time ta
                .build();
    }

    public static Response getSuccessResponce(HttpStatus status, String message, Object content, int numberOfElement) {//for getSuccess response
        return Response.builder()
                .message(message)
                .status(status.getReasonPhrase())
                .content(content)
                .numberOfElement(numberOfElement)
                .statusCode(status.value())//like 404 error
                .timestamp(new Date().getTime())//kokhon url hit hosse sei current time ta
                .build();
    }

    public static Response getSuccessResponce(HttpStatus status, String message, Object content, int numberOfElement, long rowCount) {//for getSuccess response
        return Response.builder()
                .message(message)
                .status(status.getReasonPhrase())
                .content(content)
                .numberOfElement(numberOfElement)
                .statusCode(status.value())//like 404 error
                .rowCount(rowCount)
                .timestamp(new Date().getTime())//kokhon url hit hosse sei current time ta
                .build();
    }

}
