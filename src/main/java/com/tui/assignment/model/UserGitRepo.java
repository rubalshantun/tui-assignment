package com.tui.assignment.model;

/**
 * POJO Class to hold all the required info to send in response for each repo of a user.
 * This object is created ony for non-fork repos the logic of which is implemented in service
 * layer class.
 */


public class UserGitRepo {

    private String repoName;
    private String ownerLogin;
    private RepoBranchDetail[] repoBranchDetails;

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public RepoBranchDetail[] getRepoBranchDetails() {
        return repoBranchDetails;
    }

    public void setRepoBranchDetails(RepoBranchDetail[] repoBranchDetails) {
        this.repoBranchDetails = repoBranchDetails;
    }


    /**
     * POJO class having info of each branch of a Repo . This is inner class since
     * without a parent Repo a branch info won't make sense .
     */
    public class RepoBranchDetail {

        private String branchName;
        private String lastCommitSHA;

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getLastCommitSHA() {
            return lastCommitSHA;
        }

        public void setLastCommitSHA(String lastCommitSHA) {
            this.lastCommitSHA = lastCommitSHA;
        }
    }


}
