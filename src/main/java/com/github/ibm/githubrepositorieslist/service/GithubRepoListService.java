package com.github.ibm.githubrepositorieslist.service;

import com.github.ibm.githubrepositorieslist.model.GithubResponseModel;
import com.github.ibm.githubrepositorieslist.model.RepositorySummary;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class GithubRepoListService {

    private final RestTemplate restTemplate;

    public GithubRepoListService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private HttpEntity<HttpHeaders> baseHeader () {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

    private StringBuilder baseUrl(String user) {
        StringBuilder baseUrl = new StringBuilder();
        return baseUrl.append("https://api.github.com/users/").append(user).append("/repos");
    }

    public List<RepositorySummary> listAllUserGithubRepos(String user) {
        List<RepositorySummary> repositorySummaryList = new ArrayList<>();
        try {
            StringBuilder url = baseUrl(user);
            ResponseEntity<List<GithubResponseModel>> response = restTemplate.exchange(url.toString(), HttpMethod.GET, baseHeader(), new ParameterizedTypeReference<List<GithubResponseModel>>(){});

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                response.getBody().forEach(githubResponseModel -> {
                    RepositorySummary repositorySummary = new RepositorySummary(githubResponseModel);
                    repositorySummaryList.add(repositorySummary);
                });
                return repositorySummaryList;
            }
        } catch (HttpStatusCodeException ex) {
            if (ex.getRawStatusCode() == 404) {
                return new ArrayList<>();
            }
            ex.printStackTrace();
        }
        return null;
    }
}
