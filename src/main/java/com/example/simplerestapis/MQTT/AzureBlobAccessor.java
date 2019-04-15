package com.example.simplerestapis.MQTT;

import com.example.simplerestapis.interfaces.BlobAccessor;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URISyntaxException;

@Component
public class AzureBlobAccessor implements BlobAccessor {


    CloudStorageAccount storageAccount;
    CloudBlobClient blobClient;
    CloudBlobContainer container;
    @Autowired
    BlobProp blobProp;

    public void createContainerOnCloud(String containerName)throws java.net.URISyntaxException, com.microsoft.azure.storage.StorageException
    {
            container = blobClient.getContainerReference(containerName);
            System.out.println("Creating container: " + container.getName());
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
    }


    @PostConstruct
    public void init()
    {
        try {
            storageAccount = CloudStorageAccount.parse(blobProp.getStorageConnectionString());
            blobClient = storageAccount.createCloudBlobClient();
            this.createContainerOnCloud(blobProp.getContainerName());
        } catch (URISyntaxException |java.security.InvalidKeyException | com.microsoft.azure.storage.StorageException e) {
            System.out.println("unable to connect to cloud container error:" + e);
            e.printStackTrace();
        }
    }
    @Override
    public void writeStuffToBlob(String upLoadString) {
        try {

            CloudAppendBlob blob = container.getAppendBlobReference( blobProp.getBlobFileName());
            OperationContext operationContext = new OperationContext();
            operationContext.setLoggingEnabled(true);
            blob.appendText(upLoadString + System.lineSeparator());

        } catch (URISyntaxException | com.microsoft.azure.storage.StorageException | IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String DownloadFromBlob()
    {   OutputStream outputStream = new ByteArrayOutputStream();
        try {
            CloudAppendBlob blob = container.getAppendBlobReference(blobProp.getBlobFileName());
            blob.download(outputStream);
        }catch (URISyntaxException |com.microsoft.azure.storage.StorageException e)
        {
            e.printStackTrace();
        }
        return outputStream.toString();
    }
    public String createAppendBlob(String appendBlobName)
    {
        try {
            blobProp.setBlobFileName(appendBlobName+".txt");
            System.out.println("\n\tcreate an empty append blob.");
            CloudAppendBlob appendBlob = container.getAppendBlobReference(blobProp.getBlobFileName());
            appendBlob.createOrReplace();

        }catch (java.net.URISyntaxException| com.microsoft.azure.storage.StorageException e)
        {   e.printStackTrace();
            return "\n\t unable to create an empty append blob.";

        }
        return "the blob: " + blobProp.getBlobFileName() + " was created";

    }





}
