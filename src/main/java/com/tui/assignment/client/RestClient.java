package com.tui.assignment.client;


import com.fasterxml.jackson.databind.JsonNode;
import com.tui.assignment.model.request.RepoBranchInfo;
import com.tui.assignment.model.request.UserRepoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    public List<UserRepoInfo> getAllReposInfoForUser(String url) throws RuntimeException {
        ResponseEntity<List<UserRepoInfo>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserRepoInfo>>() {
        });
        return responseEntity.getBody();
    }


    public List<RepoBranchInfo> getRepoBranchesInfo(String url) throws RuntimeException {
        ResponseEntity<List<RepoBranchInfo>> repoBranchesList = restTemplate.exchange(url, HttpMethod.GET, null,new ParameterizedTypeReference<List<RepoBranchInfo>>(){});
        return repoBranchesList.getBody();
    }


}
