package com.example.simplerestapis.controller;
import com.example.simplerestapis.models.*;
import org.springframework.web.bind.annotation.*;
import java.io.File;
@RestController
public class WebController {
	String brokerUri="tcp://10.0.0.162:1883";
	Subscriber subscriber;
	String mosquittoExecutable = "C:\\Program Files\\mosquitto\\mosquitto.exe";
	String mosquittoExecutableFilePath = "C:\\Program Files\\mosquitto";
	public WebController()
	{
		try {
				Runtime.getRuntime().exec(mosquittoExecutable, null, new File(mosquittoExecutableFilePath));
				subscriber = new Subscriber(brokerUri);
			}
		catch (org.eclipse.paho.client.mqttv3.MqttException | java.net.URISyntaxException |java.io.IOException e)
			{
				e.printStackTrace();
			}


	}


	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/test", method = RequestMethod.POST)
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
}