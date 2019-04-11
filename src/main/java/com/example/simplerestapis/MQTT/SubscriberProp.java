package com.example.simplerestapis.MQTT;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URI;

@Configuration
@ConfigurationProperties(prefix = "mqttsubscriber")
public class SubscriberProp {
    private String topic = "mottihome";         //topic name to subscribe
    private URI brokerUri;
    private String username;
    private String password;
    private int    qos;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    private String clientId;

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public URI getBrokerUri() {
        return brokerUri;
    }

    public void setBrokerUri(URI brokerUri) {
        this.brokerUri = brokerUri;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
