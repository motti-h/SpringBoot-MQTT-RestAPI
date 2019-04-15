package com.example.simplerestapis.service;

import javax.annotation.PostConstruct;

import com.example.simplerestapis.models.FileNameModel;
import com.example.simplerestapis.models.MyMqttMessageFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RestService {

	//@Autowired
	//RestServiceProp restServiceProp;

	@Autowired
	DeviceRepository deviceRepository;
	// FIXED
	// FIXME: Directly working with the subscriber class is problematic,
	// since RestService will know about specifics of blob implementation in Azure,
	// blob file format, etc...
	// Separation of concerns is a much better practice, where you would pull all the data access logic to a separate class/service
	// For example DeviceRepository service could be set up, that can use Subscriber to get data and expose clear API that can be used from
	// REST controller
	// Another benefit of using a separate class is that it can seamlessly implement caching, there's no point
	// in reading the same file again and again if it doesn't change

	@PostConstruct
	public void init() throws java.io.IOException
	{
		//Runtime.getRuntime().exec(restServiceProp.getMosquittoExecutable(), null, new File(restServiceProp.getMosquittoExecutableFilePath()));
		/*try {
			// FIXED
			// FIXME: LoadProperties includes hard-coded paths, and you don't use anything from application.properties here.
		    // If you tried, but it didn't work - the reason is that constructor is not the correct location to do this.
		    // Take a look at Spring component lifecycle documentation, and specifcally read about PostConstruct.
			//this.loadProperties();

			//FIXED:
			//Runtime.getRuntime().exec(restServiceProp.getMosquittoExecutable(), null, new File(restServiceProp.getMosquittoExecutableFilePath()));
			// FIXME: It's better to use Spring DI than manually creating instances of classes such as Subscriber
			// Change the code to use Spring DI, wich the configuration supplied from application.properties
			//mqttsubscriber = new MqttSubscriber(brokerURI,blobFileName,containerName);

		}
	catch (java.io.IOException e)
		{	//FIXED:
			//FIXME: If you get an exception in constructor of your main class, in most cases the application will not be very stable.
			// In that case, it's best to print an error and quit, instead of printing a stack trace and staying alive.
			// You should either throw the exception onwards, or actively abort execution
			e.printStackTrace();
		}
		*/
	}


	//FIXED:
	// FIXME: In Java methods start with small letters (camelcase), it's just a convention - but for someone working with your code it's important
	// take a look at java code conventions at https://www.oracle.com/technetwork/java/codeconventions-150003.pdf

	public String queryId(String id)
	{
		return deviceRepository.quaryInfoById(id);
	}
	
	public String queryIdDate(String id, String date)
	{
		return deviceRepository.quaryInfoByIdAndDate(id,date);
	}
	
	public MyMqttMessageFormat responseTest(MyMqttMessageFormat inputPayload)
	{
		MyMqttMessageFormat response = new MyMqttMessageFormat();
		ObjectMapper mapper = new ObjectMapper();
		String jsonMessage;
		try {
			jsonMessage = mapper.writeValueAsString(inputPayload);
			deviceRepository.sendMqttMessage(inputPayload.getTopic(),jsonMessage);

		}catch (com.fasterxml.jackson.core.JsonProcessingException e)
		{
			e.printStackTrace();
		}


		response.setMessage(inputPayload.getMessage());
		response.setTopic(inputPayload.getTopic());
		response.setClientId(inputPayload.getClientId());
		return response;	
	}

	public String createAppendBlob(String fileName)
	{
		return deviceRepository.createAppendBlob(fileName);
	}

	

}
