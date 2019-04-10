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

	// FIXME: Directly working with the subscriber class is problematic,
	// since RestService will know about specifics of blob implementation in Azure,
	// blob file format, etc...
	// Separation of concerns is a much better practice, where you would pull all the data access logic to a separate class/service
	// For example DeviceRepository service could be set up, that can use Subscriber to get data and expose clear API that can be used from
	// REST controller
	// Another benefit of using a separate class is that it can seamlessly implement caching, there's no point
	// in reading the same file again and again if it doesn't change
	public  Subscriber 		subscriber;
	Properties 				prop;
	InputStream 			inputProp = null;



			 
	//@Autowired
    //private CounterService counterService;
	public RestService() 
	{
		try {
				// FIXME: LoadProperties includes hard-coded paths, and you don't use anything from application.properties here.
			    // If you tried, but it didn't work - the reason is that constructor is not the correct location to do this.
			    // Take a look at Spring component lifecycle documentation, and specifcally read about PostConstruct.
				this.LoadProperties();
				Runtime.getRuntime().exec(mosquittoExecutable, null, new File(mosquittoExecutableFilePath));

				// FIXME: It's better to use Spring DI than manually creating instances of classes such as Subscriber
				// Change the code to use Spring DI, wich the configuration supplied from application.properties
				subscriber = new Subscriber(brokerURI,blobFileName,containerName);
			}
		catch (org.eclipse.paho.client.mqttv3.MqttException | java.net.URISyntaxException |java.io.IOException e)
			{
				//FIXME: If you get an exception in constructor of your main class, in most cases the application will not be very stable.
				// In that case, it's best to print an error and quit, instead of printing a stack trace and staying alive.
				// You should either throw the exception onwards, or actively abort execution
				e.printStackTrace();
			}
	}

	// FIXME: In Java methods start with small letters (camelcase), it's just a convention - but for someone working with your code it's important
	// take a look at java code conventions at https://www.oracle.com/technetwork/java/codeconventions-150003.pdf
	public String queryId(String id)
	{
		// FIXME: File parsing logic doesn't belong here, see the comment on Subscriber above
		String textdata = subscriber.myBlob.DownloadFromBlob();
		String lines[] = textdata.split("\\r?\\n");
		List<String> list = new ArrayList<String>();

		// FIXME: For the logic below, for-each loop would result in a shorter and more readable code
		for(int i=0;i<lines.length;i++)
		{
			//FIXME: indexOf(id) can have all kinds of odd behavior since it's not specifically looking where id is located, but at the whole line.
			// It's best to have some structure to the data and only look at where you would expect the id to be
			if(lines[i].indexOf(id)!=-1? true: false)
			{
				list.add(lines[i]);
			}

		}
		return list.toString();
	}
	
	public String queryIdDate(String id, String date)
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
		// FIXME: Reading external properties can easily fail if file isn't there or there is a syntax mistake.
		// The best way to handle this is either report this issue as warning and supply defaults, or wrap with try/catch
		// and provide a meaningful error.

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
