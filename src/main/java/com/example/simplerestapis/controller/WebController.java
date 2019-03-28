package com.example.simplerestapis.controller;
import com.example.simplerestapis.models.GetResponce;
import org.springframework.web.bind.annotation.*;

import com.example.simplerestapis.models.PostRequest;
import com.example.simplerestapis.models.PostResponse;
import com.example.simplerestapis.models.SampleResponse;

@RestController
public class WebController {

	@RequestMapping("/sample")
	public SampleResponse Sample(@RequestParam(value = "name",
	defaultValue = "Robot") String name) {
		SampleResponse response = new SampleResponse();
		response.setId(1);
		response.setMessage("Your name is "+name);
		return response;

	}
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public PostResponse Test(@RequestBody PostRequest inputPayload) {
		PostResponse response = new PostResponse();
		response.setMessage("server received message: " + inputPayload.getMessage());
		response.setTopic("server received topic: " + inputPayload.getTopic());
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