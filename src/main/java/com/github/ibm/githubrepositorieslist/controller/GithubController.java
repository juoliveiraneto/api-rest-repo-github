package com.github.ibm.githubrepositorieslist.controller;

import com.github.ibm.githubrepositorieslist.model.GithubUser;
import com.github.ibm.githubrepositorieslist.model.RepositorySummary;
import com.github.ibm.githubrepositorieslist.service.GithubRepoListService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GithubController {
    private static final Logger logger = LoggerFactory.getLogger(GithubController.class);

    @Autowired
    private GithubRepoListService githubRepoListService;

    @ApiOperation(value = "Lista todos os repositórios do GitHub de um determinado usuário")
    @RequestMapping(value ="/list", method = RequestMethod.POST)
    public ResponseEntity<List<RepositorySummary>> listAllUserRepos(@RequestBody GithubUser user) {
        logger.info("Requesting repos to Github controller. Github user: {}", user.getUsername());

        if ((user.getUsername() == null || ("").equalsIgnoreCase(user.getUsername()))) {
            return ResponseEntity.notFound().build();
        }

        List<RepositorySummary> result = githubRepoListService.listAllUserGithubRepos(user.getUsername());
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }
}
