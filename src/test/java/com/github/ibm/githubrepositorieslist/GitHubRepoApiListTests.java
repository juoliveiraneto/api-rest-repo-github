package com.github.ibm.githubrepositorieslist;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ibm.githubrepositorieslist.model.JwtResponse;
import com.github.ibm.githubrepositorieslist.model.RepositorySummary;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class GitHubRepoApiListTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldNotAllowAccessToNotValidUsers() throws Exception {
        String exampleUserJson = "{\"username\":\"admin\",\"password\":\"test\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/auth")
                .accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/list")).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUserReposListForValidGithubUser() throws Exception {
        String userToGetToken = "{\"username\":\"admin\",\"password\":\"12345\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/auth")
                .accept(MediaType.APPLICATION_JSON).content(userToGetToken)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult tokenResult = mvc.perform(requestBuilder).andReturn();
        String response = tokenResult.getResponse().getContentAsString();

        JwtResponse token = new ObjectMapper().readValue(response, new TypeReference<JwtResponse>(){}); //formatToken(response);

        String userToListRepos = "{\"username\":\"juoliveiraneto\",\"password\":\"12345\"}";

        MvcResult githubReposListResult = mvc.perform(MockMvcRequestBuilders.post("/list")
                .accept(MediaType.APPLICATION_JSON).content(userToListRepos)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getToken()))
                .andReturn();

        String repositories = githubReposListResult.getResponse().getContentAsString();
        List<RepositorySummary> list = new ObjectMapper().readValue(repositories, new TypeReference<List<RepositorySummary>>(){});

        assertEquals(list.get(0).getOwner(), "juoliveiraneto");
    }
}
