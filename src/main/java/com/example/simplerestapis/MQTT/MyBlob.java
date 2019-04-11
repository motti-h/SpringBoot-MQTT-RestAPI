package com.example.simplerestapis.MQTT;

import com.example.simplerestapis.controller.WebController;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;


@Component
public class MyBlob {

    public File tempfile = null;
    CloudStorageAccount storageAccount;
    CloudBlobClient blobClient;
    CloudBlobContainer container;
    @Autowired
    BlobProp blobProp;

    public void writeToFile(String wordToPrint,String path) {
        tempfile = new File(path);

        //Create the file
        try {
            if (tempfile.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }

            //Write Content
            FileWriter writer = new FileWriter(tempfile);
            writer.write(wordToPrint);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init()
    {
        try {
            storageAccount = CloudStorageAccount.parse(blobProp.getStorageConnectionString());
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(blobProp.getContainerName());
            System.out.println("Creating container: " + container.getName());
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (java.security.InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    void writeStuffToBlob(String upLoadString) {
        try {
            CloudAppendBlob blob = container.getAppendBlobReference( blobProp.getBlobFileName());
            OperationContext operationContext = new OperationContext();
            operationContext.setLoggingEnabled(true);
            //System.out.println( sdf.format(cal.getTime()) );
            blob.appendText(upLoadString +System.lineSeparator());
            //blob.downloadToFile("C:\\Users\\CodeValue\\Desktop\\tst.txt");
            //Creating blob and uploading file to it
            //System.out.println("Uploading the sample file ");
            //blob.uploadFromFile(tempfile.getAbsolutePath());

        } catch (URISyntaxException | com.microsoft.azure.storage.StorageException | IOException e) {
            e.printStackTrace();
        }
    }

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

    File createFile(String fileName){
        File t = new File(fileName);

        //Create the file
        try {
            if (t.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }

            //Write Content

        }catch(IOException e){
            e.printStackTrace();
        }
        finally {
            return t;
        }

    }



}
