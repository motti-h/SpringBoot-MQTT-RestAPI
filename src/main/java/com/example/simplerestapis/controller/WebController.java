package com.example.simplerestapis.controller;
import com.example.simplerestapis.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class WebController {
	
	@Autowired
	private RestService restService;

	@CrossOrigin(origins = "http://localhost:4200")																		//open up server for angular client
	@RequestMapping(value = "/test", method = RequestMethod.POST)														//mapping /test
	public PostResponse Test(@RequestBody PostRequest inputPayload) 
	{
		return restService.ResponseTest(inputPayload);
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