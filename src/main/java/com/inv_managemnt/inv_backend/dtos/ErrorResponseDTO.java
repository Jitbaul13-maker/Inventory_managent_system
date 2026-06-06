package com.inv_managemnt.inv_backend.dtos;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ErrorResponseDTO {
    private LocalDateTime time;
    private Integer statusCode;
    private String statusMessage;
    private String message;
}
