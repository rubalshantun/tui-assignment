package com.tui.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigParameters {


    @Value("${repo.name.field}")
    private  String repoNameField;

    @Value("${repo.owner.field}")
    private  String ownerField;

    @Value("${repo.owner.login.field}")
    private  String ownerLoginField;

    @Value("${repo.fork.field}")
    private  String repoForkField;

    @Value("${branch.name.field}")
    private  String branchNameField;

    @Value("${branch.commit.field}")
    private  String branchCommitField;

    @Value("${branch.commit.hash.field}")
    private  String branchCommitHashField;

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

    public  String getRepoNameField() {
        return repoNameField;
    }

    public  String getOwnerField() {
        return ownerField;
    }

    public  String getOwnerLoginField() {
        return ownerLoginField;
    }

    public  String getRepoForkField() {
        return repoForkField;
    }

    public  String getBranchNameField() {
        return branchNameField;
    }

    public  String getBranchCommitField() {
        return branchCommitField;
    }

    public  String getBranchCommitHashField() {
        return branchCommitHashField;
    }

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
}
