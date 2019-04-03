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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;




@RestController
public class WebController {

	

	@Autowired
	private RestService restService;

	@CrossOrigin(origins = "http://localhost:4200")																		//open up server for angular client
	@RequestMapping(value = "/test", method = RequestMethod.POST)														//mapping /test
	public PostResponse Test(@RequestBody PostRequest inputPayload) {
		PostResponse response = new PostResponse();
		response.setMessage("server received message: " + inputPayload.getMessage());
		response.setTopic("server received topic: " + inputPayload.getTopic());
		response.setDeviceId(inputPayload.getDeviceId());
		String payload=inputPayload.getDeviceId() + " " + inputPayload.getMessage();
		try {
			restService.subscriber.sendMessage(inputPayload.getTopic(), payload);

			}
		catch (org.eclipse.paho.client.mqttv3.MqttException e)
		{
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/iotdata/{id}", method = RequestMethod.GET)
	public String GetIotData(@PathVariable("id") String id) 
	{	
		return restService.QuarryId(id);
	}


	@RequestMapping(value = "/iotdata/{id}/{date}", method = RequestMethod.GET)
	public String GetIotDataWithDate(@PathVariable("id") String id,@PathVariable("date") String date) 
	{
		return restService.QuarryIdDate(id,date);
	}

	

}