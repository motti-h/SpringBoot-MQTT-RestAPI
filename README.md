# REST APIS with Springboot

this code is my rest API for a simple mqtt IoT project
it serve a an mqtt broker that manage mqqt messages between clients 
it can also transfer messages from my angular mqtt messenger app

## Pre-requisite

Ensure you have maven installed in your system. You can install it from [https://maven.apache.org/](https://maven.apache.org/)

Also ensure maven path is set in you System so that you can run `mvn` commands

to work with this project you must have mosquitto mqtt broker installed and set
the path to it in the WebController class.


## Run the Application

The Application can be run using the following command 

```bash
mvn spring-boot:run
```
