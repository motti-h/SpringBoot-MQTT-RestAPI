package com.example.simplerestapis.models;

public class PostRequest {
	String topic;
	String message;
	String deviceId;
	public String getTopic() {
		return topic;
	}

	public String getMessage() {
		return message;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getDeviceId() 
	{
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) 
	{
		this.deviceId=deviceId;
	}
	

}
