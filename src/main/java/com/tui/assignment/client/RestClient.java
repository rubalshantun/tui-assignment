package com.tui.assignment.client;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Rest Client to call any API from within our services .
 */


@Component
public class RestClient {


    @Autowired
    private RestTemplate restTemplate;


    public JsonNode getJsonResponseForGivenGetRestAPI(String url) throws RuntimeException {
        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, JsonNode.class);
        return responseEntity.getBody();
    }


}
