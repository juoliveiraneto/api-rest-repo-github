package com.github.ibm.githubrepositorieslist.model;

import java.io.Serializable;

public class GithubUser implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;
    private String username;
    private String password;

    public GithubUser() {
    }

    public GithubUser(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
