package com.example.simplerestapis.models;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyBlob {

    public File tempfile = null;
    CloudStorageAccount storageAccount;
    CloudBlobClient blobClient;
    CloudBlobContainer container;
    String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=javaiotdiag;AccountKey=mk/Yx1ve7RIi2jjrOPzAzcTZq14hhXY3EOtlllTSJQtlf6w3f5QqD8V1F9VHecMkWX4ePH5cVLDJVpDIsm8Aww==;EndpointSuffix=core.windows.net";
    String blobFileName;
    String containerName;
    String filePath ="C:\\Users\\CodeValue\\source\\java\\this is my mqtt\\java-mqtt-example\\src\\main\\java\\com\\cloudmqtt\\example\\testFile1.txt";
    public MyBlob(String blobfilename,String containername)
    {
        blobFileName = blobfilename;
        containerName = containername;
    }

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

    public void init()
    {
        try {
            storageAccount = CloudStorageAccount.parse(storageConnectionString);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(containerName);
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
            CloudAppendBlob blob = container.getAppendBlobReference(blobFileName);
            OperationContext operationContext = new OperationContext();
            operationContext.setLoggingEnabled(true);
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            //System.out.println( sdf.format(cal.getTime()) );
            blob.appendText(upLoadString + " " + sdf.format(cal.getTime())+"\n");
            //blob.downloadToFile("C:\\Users\\CodeValue\\Desktop\\tst.txt");
            //Creating blob and uploading file to it
            //System.out.println("Uploading the sample file ");
            //blob.uploadFromFile(tempfile.getAbsolutePath());


        } catch (URISyntaxException | com.microsoft.azure.storage.StorageException | IOException e) {
            e.printStackTrace();
        }
    }
}
