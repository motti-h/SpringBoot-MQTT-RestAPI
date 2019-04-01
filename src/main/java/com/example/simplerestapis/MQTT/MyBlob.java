package com.example.simplerestapis.MQTT;

import com.example.simplerestapis.controller.WebController;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;

import java.io.*;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class MyBlob {

    public File tempfile = null;
    CloudStorageAccount storageAccount;
    CloudBlobClient blobClient;
    CloudBlobContainer container;
    String storageConnectionString;
    String blobFileName;
    String containerName;
    String filePath;
    boolean emulator=false;
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
        Properties prop=new Properties();
        InputStream inputProp = WebController.class.getClassLoader().getResourceAsStream("azureBlob.properties");
        try {
            prop.load(inputProp);
            storageConnectionString=prop.getProperty("storageConnectionString");
            filePath=prop.getProperty("filePath");
            if(emulator)
                {
                    storageConnectionString=prop.getProperty("emulatorstorageConnectionString");
                    storageAccount = CloudStorageAccount.parse(storageConnectionString);
                }
            else
                {
                    storageConnectionString=prop.getProperty("storageConnectionString");
                    storageAccount = CloudStorageAccount.parse(storageConnectionString);
                }
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(containerName);
            System.out.println("Creating container: " + container.getName());
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
            //tempfile = this.createFile("AppendBlob.txt");
            //CloudAppendBlob blob = container.getAppendBlobReference(tempfile.getName());
            //blob.uploadFromFile(tempfile.getAbsolutePath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (java.security.InvalidKeyException |java.io.IOException e) {
            e.printStackTrace();
        }
    }

    void writeStuffToBlob(String upLoadString) {
        try {
            CloudAppendBlob blob = container.getAppendBlobReference(blobFileName);
            OperationContext operationContext = new OperationContext();
            operationContext.setLoggingEnabled(true);
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            //System.out.println( sdf.format(cal.getTime()) );
            blob.appendText(upLoadString + " " +sdf.format(cal.getTime())+System.lineSeparator());
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
            CloudAppendBlob blob = container.getAppendBlobReference(blobFileName);
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
