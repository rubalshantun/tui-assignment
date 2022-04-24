package com.tui.assignment.controller;

import com.tui.assignment.model.response.UserGitRepo;
import com.tui.assignment.service.GitAPIsResponseParsingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * All Rest EndPoints are implemented in this class
 */


@RestController
@RequestMapping("/api/v1")
public class APIEndPoints {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private GitAPIsResponseParsingService gitAPIsResponseParsingService;


    @GetMapping(value = "/user-github-repos-info")
    public List<UserGitRepo> getGitHubReposInfoForUser(@RequestParam(name = "username") String userName,
                                                       @RequestHeader("accept") String responseFormat) {
        log.info("user-github-repos-info Endpoint called with params username as : " + userName + " and Accept headers as : " + responseFormat);
        gitAPIsResponseParsingService.validateRequestedResponseFormat(responseFormat);
        gitAPIsResponseParsingService.validateUserName(userName);
        return gitAPIsResponseParsingService.fetchAllReposDataForUser(userName);
    }


}
