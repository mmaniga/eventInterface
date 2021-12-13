package com.c1exchange.meta.EventsInterface.response;


import com.c1exchange.meta.EventsInterface.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ApiResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String message;
    private Object data;

    public ApiResponse(HttpStatus status, Object data) {
        this.status = status;
        if (status.is2xxSuccessful())
            this.message = Constants.SUCCESS;
        else
            this.message = Constants.ERROR;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put(Constants.RESPONSE, data);
        return dataMap;
    }

    public void setData(Object data) {
        this.data = data;
    }


}