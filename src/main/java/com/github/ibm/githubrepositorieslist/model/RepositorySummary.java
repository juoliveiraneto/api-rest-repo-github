package com.github.ibm.githubrepositorieslist.model;

import com.fasterxml.jackson.annotation.*;

@JsonPropertyOrder({"isPrivate", "createdAt", "description", "fullName", "language", "name", "owner", "updatedAt"})
public class RepositorySummary {

    private Boolean isPrivate;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("description")
    private String description;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("language")
    private String language;

    @JsonProperty("name")
    private String name;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("updated_at")
    private String updatedAt;

    public RepositorySummary() {
    }

    public RepositorySummary(GithubResponseModel githubResponseModel) {
        this.isPrivate = githubResponseModel.getPrivate();
        this.createdAt = githubResponseModel.getCreatedAt();
        this.description = githubResponseModel.getDescription();
        this.fullName = githubResponseModel.getFullName();
        this.language = githubResponseModel.getLanguage();
        this.name = githubResponseModel.getName();
        this.owner = githubResponseModel.getOwner().getLogin();
        this.updatedAt = githubResponseModel.getUpdatedAt();
    }

    @JsonProperty("_private")
    public Boolean getPrivate() {
        return isPrivate;
    }

    @JsonProperty("_private")
    public void setPrivate(Boolean aPrivate) {
        this.isPrivate = aPrivate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
