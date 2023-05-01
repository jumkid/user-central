package com.jumkid.usercentral.controller;

import com.jumkid.usercentral.exception.DataNotFoundException;
import com.jumkid.usercentral.exception.DuplicateValueException;
import com.jumkid.share.controller.response.CustomErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Calendar;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class ExceptionAdviceController {

    @ExceptionHandler(DuplicateValueException.class)
    @ResponseStatus(CONFLICT)
    public CustomErrorResponse handleDuplicateValueException(DuplicateValueException ex) {
        return CustomErrorResponse.builder()
                .timestamp(Calendar.getInstance().getTime())
                .message("Duplicate data value found.")
                .build();
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public CustomErrorResponse handleDataNotFoundException(DataNotFoundException ex) {
        return CustomErrorResponse.builder()
                .timestamp(Calendar.getInstance().getTime())
                .message("Data value is not found.")
                .build();
    }

}
