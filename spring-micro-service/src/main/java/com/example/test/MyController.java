package com.example.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.cloud.client.discovery.DiscoveryClient;

@RestController
public class MyController {


	@Autowired
	private DiscoveryClient discoveryClient;
	 
	@GetMapping("/actuator/health")
	public @ResponseBody Map<String, String> getJson() {
	    Map<String, String> response = new HashMap<>();
	    response.put("status", "UP");
	    return response;
	}
	

	@GetMapping("/sendToNodeJsTemplate")
	public ResponseEntity<String> example() {
		// Get an instance of the service from Eureka
	    ServiceInstance instance = (ServiceInstance) discoveryClient.getInstances("NODEJSPROJECT").get(0);

	    // Build a URL for the request
	    String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/eurekaEndpointTesting";

	    
	    // Make a request to the service
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

	    return response;
	}
	

	@GetMapping("/eurekaEndpointTesting")
    public String sayHello() {
        return "Hello Eureka is working !";
    }
}