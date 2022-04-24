package com.tui.assignment.model.request;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UserRepoInfo {

    @JsonAlias("name")
    private String name;

    @JsonAlias("fork")
    private boolean isFork;

    @JsonAlias("owner")
    private OwnerInfo ownerInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFork() {
        return isFork;
    }

    public void setFork(boolean fork) {
        isFork = fork;
    }

    public OwnerInfo getOwnerInfo() {
        return ownerInfo;
    }

    public void setOwnerInfo(OwnerInfo ownerInfo) {
        this.ownerInfo = ownerInfo;
    }


    public class OwnerInfo {

        public OwnerInfo(){}


        @JsonAlias("login")
        private String login;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }
}
