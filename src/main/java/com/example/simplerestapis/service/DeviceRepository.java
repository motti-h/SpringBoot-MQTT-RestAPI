package com.example.simplerestapis.service;

import com.example.simplerestapis.MQTT.MqttSubscriber;
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

        String textdata = mqttsubscriber.myBlob.DownloadFromBlob();
        String lines[] = textdata.split("\\r?\\n");
        List<String> list = new ArrayList<String>();

        // FIXME: For the logic below, for-each loop would result in a shorter and more readable code
        // FIXED: for-each
        //FIXME: indexOf(id) can have all kinds of odd behavior since it's not specifically looking where id is located, but at the whole line.
        //FIXED:
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
        String textdata = mqttsubscriber.myBlob.DownloadFromBlob();
        String lines[] = textdata.split("\\r?\\n");
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
}
