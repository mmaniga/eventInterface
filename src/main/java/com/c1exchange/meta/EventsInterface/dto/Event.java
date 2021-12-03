package com.c1exchange.meta.EventsInterface.dto;

import org.springframework.util.StringUtils;

import java.util.Map;

public class Event {
    private String type;
    private String message;
    private Map<String, Object> source;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getSource() {
        return source;
    }

    public void setSource(Map<String, Object> source) {
        this.source = source;
    }
}
