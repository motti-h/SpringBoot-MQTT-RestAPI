package com.example.simplerestapis.models;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.URI;
import java.net.URISyntaxException;

//mqtt subscriber class holds the ability to write to azure blob
public class Subscriber implements MqttCallback {

    private final int qos = 1;                  //mqtt "quality of service" massage
    private String topic = "mottihome";         //topic name to subscribe
    private MqttClient client;
    private static Integer instancenum = 0;     //class instance counter
    private Integer id;                         //class instance id
    public static MyBlob myBlob;                //class myBlob containment for blob communication
    private static boolean compile = false;
    /*-----------------------------------------------------------------------------------------------------------*/
    //uri is for the mqtt broker directive
    public Subscriber(String brokerUri ,String blobname,String containername) throws MqttException, URISyntaxException {

        this(new URI(brokerUri));
        if (compile) {
            myBlob = new MyBlob(blobname, containername);
            myBlob.init();
        }
    }
    public Subscriber(String brokerUri ) throws MqttException, URISyntaxException {

            this(new URI(brokerUri));
        }

    public Subscriber(URI brokerUri) throws MqttException,URISyntaxException {

        instancenum=instancenum+1;
        id=instancenum;
        this.init(brokerUri);
    }

    private void init(URI brokerUri)throws MqttException
    {
        String host = String.format("tcp://%s:%d", brokerUri.getHost(), brokerUri.getPort());
        String username = "mottih";
        String password = "mottiadmin";
        String clientId = "MQTT-client" + instancenum ;
        if (!brokerUri.getPath().isEmpty()) {
            this.topic = brokerUri.getPath().substring(1);
        }

        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(username);
        conOpt.setPassword(password.toCharArray());

        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.setCallback(this);
        this.client.connect(conOpt);

        this.client.subscribe(this.topic, qos);
        System.out.println("listening to " + this.topic + "\n");
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
        byte[] barr = msg.getBytes();

        System.out.println(String.format("[%s] %s", topic, msg));
        //System.out.println(String.format("[%s] %d", topic, ByteBuffer.wrap(barr).getInt()));
        System.out.println(id);
        if(compile)
            myBlob.writeStuffToBlob(msg);

    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}

