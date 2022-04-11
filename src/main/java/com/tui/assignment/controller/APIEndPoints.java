package com.tui.assignment.controller;

import com.tui.assignment.exception.IncorrectResponseFormatException;
import com.tui.assignment.model.UserGitRepo;
import com.tui.assignment.service.JsonParsingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * All Rest EndPoints are implemented in this class
 * */



@RestController
@RequestMapping("/api/v1")
public class APIEndPoints {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JsonParsingService jsonParsingService;

    @Value("${invalid.response.formats}")
    private String invalidResponseFormats; //configurable property having csv of all not supported Response Formats


    @GetMapping(value = "/user-github-repos-info")
    public List<UserGitRepo> getGitHubReposInfoForUser(@RequestParam(name = "username") String userName,
                                                       @RequestHeader("accept") String responseFormat) {
        log.info("user-github-repos-info Endpoint called with params username as : " + userName + " and Accept headers as : " + responseFormat);
        if (invalidResponseFormats.contains(responseFormat)) {
            throw new IncorrectResponseFormatException("Requested Response data format is not acceptable. Supported format is JSON only");
        }
        return jsonParsingService.fetchAllReposDataForUser(userName);
    }


}
