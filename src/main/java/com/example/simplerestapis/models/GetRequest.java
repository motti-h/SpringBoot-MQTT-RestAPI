package com.example.simplerestapis.models;

public class GetRequest {
    String topic;
    String message;

    public String getTopic() {
        return topic;
    }

    public String getMessage() {
        return message;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
