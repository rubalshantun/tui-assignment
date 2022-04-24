package com.tui.assignment.model.request;

import com.fasterxml.jackson.annotation.JsonAlias;

public class RepoBranchInfo {

    @JsonAlias("name")
    private String branchName;

    @JsonAlias("protected")
    private boolean isProtected;

    @JsonAlias("commit")
    private CommitInfo commitInfo;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public CommitInfo getCommitInfo() {
        return commitInfo;
    }

    public void setCommitInfo(CommitInfo commitInfo) {
        this.commitInfo = commitInfo;
    }

    public class CommitInfo{

        public CommitInfo(){

        }

        @JsonAlias("url")
        private String commitUrl;

        @JsonAlias("sha")
        private String sha;

        public String getCommitUrl() {
            return commitUrl;
        }

        public void setCommitUrl(String commitUrl) {
            this.commitUrl = commitUrl;
        }

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }
    }
}
