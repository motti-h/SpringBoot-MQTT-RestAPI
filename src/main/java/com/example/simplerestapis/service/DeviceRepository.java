package com.example.simplerestapis.service;

import com.example.simplerestapis.MQTT.MqttSubscriber;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceRepository {
    @Autowired
    public MqttSubscriber mqttsubscriber;

    public String quaryInfoById(String id)
    {

        String lines[]=this.splitBlobToLines(mqttsubscriber.myBlob.DownloadFromBlob());
        List<String> list = new ArrayList<String>();

        // FIXED: for-each
        // FIXME: For the logic below, for-each loop would result in a shorter and more readable code
        //FIXED:
        //FIXME: indexOf(id) can have all kinds of odd behavior since it's not specifically looking where id is located, but at the whole line.
        for(String line:lines)
        {
            if(line.contains(id))
            {
                list.add(line);
            }
        }
        return list.toString();
    }

    public String quaryInfoByIdAndDate(String id, String date)
    {
        String lines[]=this.splitBlobToLines(mqttsubscriber.myBlob.DownloadFromBlob());
        List<String> list = new ArrayList<String>();
        //FIXED: for-each
        for(String line:lines)
        {
            if(line.contains(date)&&line.contains(id))
            {
                list.add(line);
            }
        }

        return list.toString();
    }

    public void sendMqttMessage(String topic,String payload)
    {
        try {
            mqttsubscriber.sendMessage(topic, payload);
        }
        catch (org.eclipse.paho.client.mqttv3.MqttException e)
        {
            e.printStackTrace();
        }
    }

    public String createAppendBlob(String fileName)
    {
       return mqttsubscriber.myBlob.createAppendBlob(fileName);
    }

    private String[] splitBlobToLines(String jsonText)
    {
        return jsonText.split("\\r?\\n");
    }
}
