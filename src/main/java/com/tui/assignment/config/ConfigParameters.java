package com.tui.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigParameters {

    @Value("${github.repos.branches.uri}")
    private  String gitHubRepoBranchesUrl;

    @Value("${github.repos.uri}")
    private  String gitHubReposUrl;

    @Value("${invalid.response.formats}")
    private  String invalidResponseFormats; //configurable property having csv of all not supported Response Formats

    @Value("${integration.test.invalid.username}")
    private String integrationTestInvalidUsername;

    @Value("${integration.test.valid.username}")
    private String integrationTestValidUsername;

    @Value("${thread.pool.size}")
    private int threadPoolSize;

    public  String getGitHubRepoBranchesUrl() {
        return gitHubRepoBranchesUrl;
    }

    public  String getGitHubReposUrl() {
        return gitHubReposUrl;
    }

    public  String getInvalidResponseFormats() {
        return invalidResponseFormats;
    }

    public String getIntegrationTestInvalidUsername() {
        return integrationTestInvalidUsername;
    }

    public String getIntegrationTestValidUsername() {
        return integrationTestValidUsername;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }
}
