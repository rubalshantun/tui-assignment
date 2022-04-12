package com.tui.assignment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tui.assignment.client.RestClient;
import com.tui.assignment.config.ConfigParameters;
import com.tui.assignment.exception.GenericException;
import com.tui.assignment.exception.UserDoesNotExitsException;
import com.tui.assignment.model.UserGitRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * This class holds all th business logic behind the EndPoint and is responsible for parsing the
 * Github API response and creating response for Our Endpoint.
 */


@Service
public class JsonParsingService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConfigParameters configParameters;

    @Autowired
    private RestClient restClient;


    public List<UserGitRepo> fetchAllReposDataForUser(String userName) {

        List<UserGitRepo> gitRepo = null;
        try {
            String fullUrlForRepos = MessageFormat.format(configParameters.getGitHubReposUrl(), userName);
            log.info("Going to call GitHub API for User Info on given URL : " + fullUrlForRepos);
            JsonNode reposJsonData = restClient.getJsonResponseForGivenGetRestAPI(fullUrlForRepos);

            if (reposJsonData.isArray()) { //all the repos data of a user
                log.info("Going to Parse JSON Response " + reposJsonData.toPrettyString());
                gitRepo = new ArrayList<>();
                for (int i = 0; i < reposJsonData.size(); i++) {//iterate over each repo of the given user
                    UserGitRepo userGitRepo = new UserGitRepo();
                    JsonNode singleRepoData = reposJsonData.get(i);
                    if (singleRepoData.get(configParameters.getRepoForkField()).asBoolean()) {// if a repo is forked ,ignore it and move to the next one
                        continue;
                    }
                    userGitRepo.setRepoName(singleRepoData.get(configParameters.getRepoNameField()).asText());
                    JsonNode ownerData = singleRepoData.get(configParameters.getOwnerField());
                    userGitRepo.setOwnerLogin(ownerData.get(configParameters.getOwnerLoginField()).asText());
                    String fullUrlForBranches = MessageFormat.format(configParameters.getGitHubRepoBranchesUrl(), userName, userGitRepo.getRepoName());
                    JsonNode branchesData = restClient.getJsonResponseForGivenGetRestAPI(fullUrlForBranches);
                    UserGitRepo.RepoBranchDetail[] repoBranchDetails = null;
                    if (branchesData.isArray()) {// all the branch data of a repo
                        repoBranchDetails = new UserGitRepo.RepoBranchDetail[branchesData.size()];
                        for (int j = 0; j < branchesData.size(); j++) { //iterate over each branch of the enclosing repo
                            UserGitRepo.RepoBranchDetail repoBranchDetail = userGitRepo.new RepoBranchDetail();
                            JsonNode branchData = branchesData.get(j);
                            repoBranchDetail.setBranchName(branchData.get(configParameters.getBranchNameField()).asText());
                            JsonNode commitData = branchData.get(configParameters.getBranchCommitField());
                            repoBranchDetail.setLastCommitSHA(commitData.get(configParameters.getBranchCommitHashField()).asText());
                            repoBranchDetails[j] = repoBranchDetail;
                        }
                    }
                    userGitRepo.setRepoBranchDetails(repoBranchDetails);
                    gitRepo.add(userGitRepo);
                }
            }
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("404 Not Found")) {
                log.warn("User doesn't exits ", ex);
                throw new UserDoesNotExitsException("Given Username doesn't exits on GitHub.");
            } else {
                log.error("Some Error happened", ex);
                throw new GenericException(ex.getMessage());
            }
        }
        return gitRepo;
    }

}
