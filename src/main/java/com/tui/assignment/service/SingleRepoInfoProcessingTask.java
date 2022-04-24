package com.tui.assignment.service;

import com.tui.assignment.client.RestClient;
import com.tui.assignment.model.request.RepoBranchInfo;
import com.tui.assignment.model.response.UserGitRepo;
import com.tui.assignment.model.request.UserRepoInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class SingleRepoInfoProcessingTask implements Callable<UserGitRepo> {

    private final String repoBranchesUrl;
    private UserRepoInfo singleRepoData;

    @Autowired
    private RestClient restClient;

    public SingleRepoInfoProcessingTask(UserRepoInfo userRepoInfo,String fullUrlForBranches){
        singleRepoData = userRepoInfo;
        repoBranchesUrl = fullUrlForBranches;
    }

    @Override
    public UserGitRepo call() {
        UserGitRepo userGitRepo = new UserGitRepo();
        userGitRepo.setRepoName(singleRepoData.getName());
        userGitRepo.setOwnerLogin(singleRepoData.getOwnerInfo().getLogin());
        List<RepoBranchInfo> branchesData = restClient.getRepoBranchesInfo(repoBranchesUrl);
        List<UserGitRepo.RepoBranchDetail> repoBranchDetails = new ArrayList<>();
        // all the branch data of a repo
        for (RepoBranchInfo branchData:branchesData) { //iterate over each branch of the enclosing repo
            UserGitRepo.RepoBranchDetail repoBranchDetail = userGitRepo.new RepoBranchDetail();
            repoBranchDetail.setBranchName(branchData.getBranchName());
            repoBranchDetail.setLastCommitSHA(branchData.getCommitInfo().getSha());
            repoBranchDetails.add(repoBranchDetail);
        }
        userGitRepo.setRepoBranchDetails(repoBranchDetails);
        return userGitRepo;
    }
}
