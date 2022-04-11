package com.tui.assignment.service;

import com.tui.assignment.client.RestClient;
import com.tui.assignment.exception.GenericException;
import com.tui.assignment.exception.UserDoesNotExitsException;
import com.tui.assignment.model.UserGitRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    ObjectMapper objectMapper;

    @Autowired
    RestClient restClient;

    @Value("${repo.name.field}")
    private String repoNameField;

    @Value("${repo.owner.field}")
    private String ownerField;

    @Value("${repo.owner.login.field}")
    private String ownerLoginField;

    @Value("${repo.fork.field}")
    private String repoForkField;

    @Value("${branch.name.field}")
    private String branchNameField;

    @Value("${branch.commit.field}")
    private String branchCommitField;

    @Value("${branch.commit.hash.field}")
    private String branchCommitHashField;

    @Value("${github.repos.branches.uri}")
    private String repoBranchesUrl;

    @Value("${github.repos.uri}")
    private String gitHubReposUrl;

    @Value("${github.user.url}")
    private String gitHubUserUrl;


    public List<UserGitRepo> fetchAllReposDataForUser(String userName) {

        List<UserGitRepo> gitRepo = null;
        try {
            String fullUrlForRepos = MessageFormat.format(gitHubReposUrl, userName);
            log.info("Going to call GitHub API for User Info on given URL : " + fullUrlForRepos);
            JsonNode reposJsonData = restClient.getJsonResponseForGivenGetRestAPI(fullUrlForRepos);

            if (reposJsonData.isArray()) {
                log.info("Going to Parse JSON Response " + reposJsonData.toPrettyString());
                gitRepo = new ArrayList<>();
                for (int i = 0; i < reposJsonData.size(); i++) {
                    UserGitRepo userGitRepo = new UserGitRepo();
                    JsonNode singleRepoData = reposJsonData.get(i);
                    if (singleRepoData.get(repoForkField).asBoolean()) {
                        continue;
                    }
                    userGitRepo.setRepoName(singleRepoData.get(repoNameField).asText());
                    JsonNode ownerData = singleRepoData.get(ownerField);
                    userGitRepo.setOwnerLogin(ownerData.get(ownerLoginField).asText());
                    String fullUrlForBranches = MessageFormat.format(repoBranchesUrl, userName, userGitRepo.getRepoName());
                    JsonNode branchesData = restClient.getJsonResponseForGivenGetRestAPI(fullUrlForBranches);
                    UserGitRepo.RepoBranchDetail[] repoBranchDetails = null;
                    if (branchesData.isArray()) {
                        repoBranchDetails = new UserGitRepo.RepoBranchDetail[branchesData.size()];
                        for (int j = 0; j < branchesData.size(); j++) {
                            UserGitRepo.RepoBranchDetail repoBranchDetail = userGitRepo.new RepoBranchDetail();
                            JsonNode branchData = branchesData.get(j);
                            repoBranchDetail.setBranchName(branchData.get(branchNameField).asText());
                            JsonNode commitData = branchData.get(branchCommitField);
                            repoBranchDetail.setLastCommitSHA(commitData.get(branchCommitHashField).asText());
                            repoBranchDetails[j] = repoBranchDetail;
                        }
                    }
                    userGitRepo.setRepoBranchDetails(repoBranchDetails);
                    gitRepo.add(userGitRepo);
                }
            }
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("404 Not Found")) {
                log.warn("User doesn't exits ",ex);
                throw new UserDoesNotExitsException("Given Username doesn't exits on GitHub.");
            } else {
                log.error("Some Error happened" , ex);
                throw new GenericException(ex.getMessage());
            }
        }
        return gitRepo;
    }

}
