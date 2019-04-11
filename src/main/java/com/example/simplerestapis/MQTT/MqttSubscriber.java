package com.example.simplerestapis.MQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// FIXED:
// FIXME: Subscriber is a poor name for a class since it's not very descriptive - try right click on the name, rename, and rename to MqttSubscriber
// Now it's clear what it should do and what it shouldn't do. Usually we want our classes to do one thing only (single responsibility principle).
// So for example, having blob name and containername in constructor (not very useful for mqtt) should look suspicious to you


@Component
public class MqttSubscriber implements MqttCallback {

    private final int qos = 1;                  //mqtt "quality of service" massage

    private MqttClient client;
    private static Integer instance = 0;        //class instance counter
    private Integer id;                         //class instance id
    @Autowired
    private SubscriberProp subscriberProp;
    @Autowired
    public MyBlob myBlob;                       //class myBlob containment for blob communication

    @PostConstruct
    private void init()throws MqttException
    {
        instance = instance +1;
        id= instance;
        String clientId = subscriberProp.getClientId() + instance;
        String host = subscriberProp.getBrokerUri().toString();
        // FIXED:
        // FIXME: These belong to configuration in application.properties


        if (!subscriberProp.getBrokerUri().getPath().isEmpty()) {
            subscriberProp.setTopic(subscriberProp.getBrokerUri().getPath().substring(1)) ;
        }

        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(subscriberProp.getUsername());
        conOpt.setPassword(subscriberProp.getPassword().toCharArray());

        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.setCallback(this);
        this.client.connect(conOpt);

        this.client.subscribe(subscriberProp.getTopic(), subscriberProp.getQos());
        System.out.println("listening to " + subscriberProp.getTopic() + "\n");
    }

    private String[] getAuth(URI uri) {
        String a = uri.getAuthority();
        String[] first = a.split("@");
        return first[0].split(":");
    }

    public void sendMessage(String topic, String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        this.client.publish(topic, message); // Blocking publish
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        System.exit(1);
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    public void messageArrived(String topic, MqttMessage message) throws MqttException {

        String msg = new String(message.getPayload());
        //byte[] barr = msg.getBytes();
        //System.out.println(String.format("[%s] %d", topic, ByteBuffer.wrap(barr).getInt()));
        //System.out.println(id);
        System.out.println(String.format("[%s] %s", topic, msg));
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//+ " " +sdf.format(cal.getTime())
        msg = msg + " " +sdf.format(cal.getTime());
           myBlob.writeStuffToBlob("topic:" + " " + topic + " " + "message:" + " " + msg);

    }

    public void setTopic(String topic) {
        subscriberProp.setTopic(topic);
    }
}

