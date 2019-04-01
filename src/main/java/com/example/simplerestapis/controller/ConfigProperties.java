package com.example.simplerestapis.controller;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;



//@PropertySource("classpath:application.properties")
//@Component
//@ConfigurationProperties("webcontroller")
public class ConfigProperties {


   private String 			brokerURI;

   private String 			mosquittoExecutable;

   private String 			mosquittoExecutableFilePath;

   private String			blobFileName;

   private String			containerName;

   public String getMosquittoExecutableFilePath() {
      return mosquittoExecutableFilePath;
   }

   public String getMosquittoExecutable() {
      return mosquittoExecutable;
   }

   public String getContainerName() {
      return containerName;
   }

   public String getBrokerURI() {
      return brokerURI;
   }

   public String getBlobFileName() {
      return blobFileName;
   }

   public void setMosquittoExecutableFilePath(String mosquittoExecutableFilePath) {
      this.mosquittoExecutableFilePath = mosquittoExecutableFilePath;
   }

   public void setMosquittoExecutable(String mosquittoExecutable) {
      this.mosquittoExecutable = mosquittoExecutable;
   }

   public void setContainerName(String containerName) {
      this.containerName = containerName;
   }

   public void setBrokerURI(String brokerURI) {
      this.brokerURI = brokerURI;
   }

   public void setBlobFileName(String blobFileName) {
      this.blobFileName = blobFileName;
   }
}
