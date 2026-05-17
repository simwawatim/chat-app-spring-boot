package com.example.chat.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private String status;  
    private String message;
    private T data;
    private int statusCode;
}