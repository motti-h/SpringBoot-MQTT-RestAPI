package com.example.simplerestapis.MQTT;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
@ConfigurationProperties(prefix = "broker")
public class MqttBroker {


    private String mosquittoExecutable;
    private String mosquittoExecutableFilePath;

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

    @PostConstruct
    void init()
    {
        try{
        Runtime.getRuntime().exec(mosquittoExecutable, null, new File(mosquittoExecutableFilePath));
    }catch (java.io.IOException e)
        {
            System.out.println("quiting program dou to " + e);
            System.exit(1);
        }
    }
}
