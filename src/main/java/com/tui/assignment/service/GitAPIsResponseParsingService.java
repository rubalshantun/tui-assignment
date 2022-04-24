package com.tui.assignment.service;

import com.tui.assignment.client.RestClient;
import com.tui.assignment.config.ConfigParameters;
import com.tui.assignment.exception.GenericException;
import com.tui.assignment.exception.IncorrectResponseFormatException;
import com.tui.assignment.exception.UserDoesNotExitsException;
import com.tui.assignment.model.request.RepoBranchInfo;
import com.tui.assignment.model.response.UserGitRepo;
import com.tui.assignment.model.request.UserRepoInfo;
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
public class GitAPIsResponseParsingService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConfigParameters configParameters;

    @Autowired
    private RestClient restClient;


    public List<UserGitRepo> fetchAllReposDataForUser(String userName) {

        List<UserGitRepo> gitRepo;
        try {
            String fullUrlForRepos = MessageFormat.format(configParameters.getGitHubReposUrl(), userName);
            log.info("Going to call GitHub API for User Info on given URL : " + fullUrlForRepos);
            List<UserRepoInfo> allReposInfoForUser = restClient.getAllReposInfoForUser(fullUrlForRepos);
            log.info("Going to Parse API Response " + allReposInfoForUser.toString());
            gitRepo = new ArrayList<>();
//            allReposInfoForUser.stream().filter(userRepoInfo -> ! userRepoInfo.isFork()).collect(Collectors.toList());
//            Executor executor = Executors.newFixedThreadPool(configParameters.getThreadPoolSize());
            for (UserRepoInfo singleRepoData:allReposInfoForUser) {//iterate over each repo of the given user
                if (singleRepoData.isFork()) {// if a repo is forked ,ignore it and move to the next one
                    continue;
                }
                UserGitRepo userGitRepo = new UserGitRepo();
                userGitRepo.setRepoName(singleRepoData.getName());
                userGitRepo.setOwnerLogin(singleRepoData.getOwnerInfo().getLogin());
                String fullUrlForBranches = MessageFormat.format(configParameters.getGitHubRepoBranchesUrl(), userName,userGitRepo.getRepoName());
                List<RepoBranchInfo> branchesData = restClient.getRepoBranchesInfo(fullUrlForBranches);
                List<UserGitRepo.RepoBranchDetail> repoBranchDetails = new ArrayList<>();
                // all the branch data of a repo
                for (RepoBranchInfo  branchData:branchesData) { //iterate over each branch of the enclosing repo
                    UserGitRepo.RepoBranchDetail repoBranchDetail = userGitRepo.new RepoBranchDetail();
                    repoBranchDetail.setBranchName(branchData.getBranchName());
                    repoBranchDetail.setLastCommitSHA(branchData.getCommitInfo().getSha());
                    repoBranchDetails.add(repoBranchDetail);
                }
                userGitRepo.setRepoBranchDetails(repoBranchDetails);
                gitRepo.add(userGitRepo);
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

    public void validateRequestedResponseFormat(String responseFormat) {
        if (configParameters.getInvalidResponseFormats().contains(responseFormat)) {
            throw new IncorrectResponseFormatException("Requested Response data format is not acceptable. Supported format is JSON only");
        }
    }

    public void validateUserName(String userName) {
        if (userName.trim().length() == 0) {
            throw new UserDoesNotExitsException("Given Username doesn't exits on GitHub.");
        }
    }

}
