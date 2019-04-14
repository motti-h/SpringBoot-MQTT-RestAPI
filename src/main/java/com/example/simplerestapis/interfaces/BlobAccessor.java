package com.example.simplerestapis.interfaces;

public interface BlobAccessor {

    void writeStuffToBlob(String upLoadString);
    String DownloadFromBlob();
}
