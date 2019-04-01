package com.example.simplerestapis.controller;
import com.example.simplerestapis.MQTT.Subscriber;
import com.example.simplerestapis.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Properties;




@RestController
public class WebController {

	//this.LoadProperties();



	private String 			brokerURI;

	private String 			mosquittoExecutable;

	private String 			mosquittoExecutableFilePath;

	private String			blobFileName;

	private String			containerName;
	Subscriber 		subscriber;
	Properties 		prop;
	InputStream 	inputProp = null;


	public WebController()
	{
		try {
			//Runtime.getRuntime().exec(configProperties.getMosquittoExecutable(), null, new File(configProperties.getMosquittoExecutableFilePath()));
			//subscriber = new Subscriber(configProperties.getBrokerURI(),configProperties.getBlobFileName(),configProperties.getContainerName());
				this.LoadProperties();
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

	@RequestMapping(value = "/iotdata/", method = RequestMethod.GET)
	public String GetIotData() {
	return subscriber.myBlob.DownloadFromBlob();

	}

	void LoadProperties()throws java.io.IOException
	{

		prop=new Properties();
		inputProp = WebController.class.getClassLoader().getResourceAsStream("WebController.properties");
		prop.load(inputProp);
		brokerURI=prop.getProperty("brokerURI");
		mosquittoExecutable=prop.getProperty("mosquittoExecutable");
		mosquittoExecutableFilePath=prop.getProperty("mosquittoExecutableFilePath");
		blobFileName=prop.getProperty("blobFileName");
		containerName=prop.getProperty("containerName");

	}

	public void setBlobFileName(String blobFileName) {
		this.blobFileName = blobFileName;
	}

	public void setBrokerURI(String brokerURI) { this.brokerURI=brokerURI;}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public void setMosquittoExecutable(String mosquittoExecutable) {
		this.mosquittoExecutable = mosquittoExecutable;
	}

	public void setMosquittoExecutableFilePath(String mosquittoExecutableFilePath) {
		this.mosquittoExecutableFilePath = mosquittoExecutableFilePath;
	}

	public String getBlobFileName() {
		return blobFileName;
	}

	public String getBrokerURI() {
		return brokerURI;
	}

	public String getContainerName() {
		return containerName;
	}

	public String getMosquittoExecutable() {
		return mosquittoExecutable;
	}

	public String getMosquittoExecutableFilePath() {
		return mosquittoExecutableFilePath;
	}
}