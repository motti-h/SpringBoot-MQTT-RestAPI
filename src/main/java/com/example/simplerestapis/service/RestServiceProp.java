package com.example.simplerestapis.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "restservice")
public class RestServiceProp {



    private String 			mosquittoExecutable;
    private String 			mosquittoExecutableFilePath;

    public String getMosquittoExecutable() {
        return mosquittoExecutable;
    }

    public void setMosquittoExecutable(String mosquittoExecutable) {
        this.mosquittoExecutable = mosquittoExecutable;
    }

    public String getMosquittoExecutableFilePath() {
        return mosquittoExecutableFilePath;
    }

    public void setMosquittoExecutableFilePath(String mosquittoExecutableFilePath) {
        this.mosquittoExecutableFilePath = mosquittoExecutableFilePath;
    }




}
