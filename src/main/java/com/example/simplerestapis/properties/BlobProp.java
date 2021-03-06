package com.example.simplerestapis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "blob")
public class BlobProp {

    private String storageConnectionString;

    private String blobFileName;

    private String containerName;

    private String filePath;

    public String getStorageConnectionString() {
        return storageConnectionString;
    }

    public void setStorageConnectionString(String storageConnectionString) {
        this.storageConnectionString = storageConnectionString;
    }

    public String getBlobFileName() {
        return blobFileName;
    }

    public void setBlobFileName(String blobFileName) {
        this.blobFileName = blobFileName;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
