package com.example.Shopping_Website.Exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private LocalDateTime timeStamp;
    private String message;
    private String details;
    public ExceptionResponse(LocalDateTime timeStamp, String message, String details){
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }
}
