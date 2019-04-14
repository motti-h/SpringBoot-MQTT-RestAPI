package com.example.simplerestapis.controller;
import com.example.simplerestapis.models.FileNameModel;
import com.example.simplerestapis.models.MyMqttMessageFormat;
import com.example.simplerestapis.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class WebController {
	
	@Autowired
	private RestService restService;

	@CrossOrigin(origins = "http://localhost:4200")																		//open up server for angular client
	@RequestMapping(value = "/test", method = RequestMethod.POST)														//mapping /test
	public MyMqttMessageFormat Test(@RequestBody MyMqttMessageFormat inputPayload)
	{
		return restService.responseTest(inputPayload);
	}

	@RequestMapping(value = "/iotdata/{id}", method = RequestMethod.GET)
	public String GetIotData(@PathVariable("id") String id)
	{	
		return restService.queryId(id);
	}


	@RequestMapping(value = "/iotdata/{id}/{date}", method = RequestMethod.GET)
	public String GetIotDataWithDate(@PathVariable("id") String id,@PathVariable("date") String date) 
	{
		return restService.queryIdDate(id,date);
	}
	@RequestMapping(value = "/createblob", method = RequestMethod.POST)														//mapping /test
	public String createblob(@RequestBody FileNameModel fileName)
	{
		return restService.createAppendBlob(fileName.getFileName());
	}


	

}