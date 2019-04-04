package com.example.simplerestapis.controller;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.springframework.stereotype.Service;
import com.example.simplerestapis.MQTT.Subscriber;
import com.example.simplerestapis.models.PostRequest;
import com.example.simplerestapis.models.PostResponse;

@Service
public class RestService {
	private String 			brokerURI;
	private String 			mosquittoExecutable;
	private String 			mosquittoExecutableFilePath;
	private String			blobFileName;
	private String			containerName;
	public  Subscriber 		subscriber;
	Properties 				prop;
	InputStream 			inputProp = null;
	
			 
	//@Autowired
    //private CounterService counterService;
	public RestService() 
	{
		try {
				this.LoadProperties();
				Runtime.getRuntime().exec(mosquittoExecutable, null, new File(mosquittoExecutableFilePath));
				subscriber = new Subscriber(brokerURI,blobFileName,containerName);
			}
		catch (org.eclipse.paho.client.mqttv3.MqttException | java.net.URISyntaxException |java.io.IOException e)
			{
				e.printStackTrace();
			}
	}
	
	public String QuarryId(String id) 
	{	
		String textdata = subscriber.myBlob.DownloadFromBlob();
		String lines[] = textdata.split("\\r?\\n");
		List<String> list = new ArrayList<String>();
		for(int i=0;i<lines.length;i++)
		{
			if(lines[i].indexOf(id)!=-1? true: false)
			{
				list.add(lines[i]);
			}

		}
		return list.toString();
	}
	
	public String QuarryIdDate(String id,String date) 
	{
		String textdata = subscriber.myBlob.DownloadFromBlob();
		String lines[] = textdata.split("\\r?\\n");
		List<String> list = new ArrayList<String>();
		for(int i=0;i<lines.length;i++)
		{
			if(lines[i].contains(date)&&lines[i].contains(id))
			{
				list.add(lines[i]);
			}
		}

			return list.toString();
	}
	
	public PostResponse ResponseTest(PostRequest inputPayload) 
	{
		PostResponse response = new PostResponse();
		response.setMessage("server received message: " + inputPayload.getMessage());
		response.setTopic("server received topic: " + inputPayload.getTopic());
		response.setDeviceId(inputPayload.getDeviceId());
		String payload=inputPayload.getDeviceId() + " " + inputPayload.getMessage();
		try {
			subscriber.sendMessage(inputPayload.getTopic(), payload);
			}
		catch (org.eclipse.paho.client.mqttv3.MqttException e)
		{
			e.printStackTrace();
		}
		
		return response;	
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
	

}
