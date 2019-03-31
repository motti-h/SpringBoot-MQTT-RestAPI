package com.example.simplerestapis.controller;
import com.example.simplerestapis.MQTT.Subscriber;
import com.example.simplerestapis.models.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


@RestController
public class WebController {
	String 			brokerURI;
	Subscriber 		subscriber;
	String 			mosquittoExecutable;
	String 			mosquittoExecutableFilePath;
	String			blobFileName;
	String			containerName;
	Properties 		prop;
	InputStream 	inputProp = null;
	public WebController()
	{
		try {
				this.SetProperties();
				Runtime.getRuntime().exec(mosquittoExecutable, null, new File(mosquittoExecutableFilePath));
				subscriber = new Subscriber(brokerURI,blobFileName,containerName);
			}
		catch (org.eclipse.paho.client.mqttv3.MqttException | java.net.URISyntaxException |java.io.IOException e)
			{
				e.printStackTrace();
			}


	}


	@CrossOrigin(origins = "http://localhost:4200")																		//open up server for angular client
	@RequestMapping(value = "/test", method = RequestMethod.POST)														//mapping /test
	public PostResponse Test(@RequestBody PostRequest inputPayload) {
		PostResponse response = new PostResponse();
		response.setMessage("server received message: " + inputPayload.getMessage());
		response.setTopic("server received topic: " + inputPayload.getTopic());
		try {
			subscriber.sendMessage(inputPayload.getTopic(), inputPayload.getMessage());

			}
		catch (org.eclipse.paho.client.mqttv3.MqttException e)
		{
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public GetResponce Get(@RequestBody GetResponce inputPayload) {
		GetResponce response = new GetResponce();
		response.setMessage(inputPayload.getMessage());
		response.setTopic(inputPayload.getTopic());

		return response;
	}
	void SetProperties()throws java.io.IOException
	{

		prop=new Properties();
		inputProp = WebController.class.getClassLoader().getResourceAsStream("webController.properties");
		prop.load(inputProp);
		brokerURI=prop.getProperty("brokerURI");
		mosquittoExecutable=prop.getProperty("mosquittoExecutable");
		mosquittoExecutableFilePath=prop.getProperty("mosquittoExecutableFilePath");
		blobFileName=prop.getProperty("blobFileName");
		containerName=prop.getProperty("containerName");

	}
}