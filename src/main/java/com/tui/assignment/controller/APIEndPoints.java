package com.tui.assignment.controller;

import com.tui.assignment.config.ConfigParameters;
import com.tui.assignment.exception.IncorrectResponseFormatException;
import com.tui.assignment.exception.UserDoesNotExitsException;
import com.tui.assignment.model.UserGitRepo;
import com.tui.assignment.service.JsonParsingService;
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
    private ConfigParameters configParameters;

    @Autowired
    private JsonParsingService jsonParsingService;


    @GetMapping(value = "/user-github-repos-info")
    public List<UserGitRepo> getGitHubReposInfoForUser(@RequestParam(name = "username") String userName,
                                                       @RequestHeader("accept") String responseFormat) {
        log.info("user-github-repos-info Endpoint called with params username as : " + userName + " and Accept headers as : " + responseFormat);
        if (configParameters.getInvalidResponseFormats().contains(responseFormat)) {
            throw new IncorrectResponseFormatException("Requested Response data format is not acceptable. Supported format is JSON only");
        }
        if(userName.trim().length()==0){
            throw new UserDoesNotExitsException("Given Username doesn't exits on GitHub.");
        }
        return jsonParsingService.fetchAllReposDataForUser(userName);
    }


}
