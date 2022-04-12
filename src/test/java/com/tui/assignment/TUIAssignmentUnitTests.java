package com.tui.assignment;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.assignment.client.RestClient;
import com.tui.assignment.config.ConfigParameters;
import com.tui.assignment.model.UserGitRepo;
import com.tui.assignment.service.JsonParsingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TUIAssignmentUnitTests {


    @Autowired
    ApplicationContext applicationContext;

    @Mock
    RestClient restClient;

    @Mock
    ConfigParameters configParameters;

    @InjectMocks
    JsonParsingService jsonParsingService;

    ConfigParameters actualBean;

    @BeforeEach
    public void initConfigParameters() {
        actualBean = applicationContext.getBean(ConfigParameters.class);
    }


    @Test
    public void getResponseObjectOnPassingInputJsonToParsingMethod() throws Exception{
        String mockUserReposResponseJsonString = "[\n" +
                "    {\n" +
                "        \"id\": 3581279,\n" +
                "        \"node_id\": \"MDEwOlJlcG9zaXRvcnkzNTgxMjc5\",\n" +
                "        \"name\": \"rubal\",\n" +
                "        \"full_name\": \"rubal/rubal\",\n" +
                "        \"private\": false,\n" +
                "        \"owner\": {\n" +
                "            \"login\": \"rubal\",\n" +
                "            \"id\": 1484937,\n" +
                "            \"node_id\": \"MDQ6VXNlcjE0ODQ5Mzc=\",\n" +
                "            \"avatar_url\": \"https://avatars.githubusercontent.com/u/1484937?v=4\",\n" +
                "            \"gravatar_id\": \"\",\n" +
                "            \"url\": \"https://api.github.com/users/rubal\",\n" +
                "            \"html_url\": \"https://github.com/rubal\",\n" +
                "            \"followers_url\": \"https://api.github.com/users/rubal/followers\",\n" +
                "            \"type\": \"User\",\n" +
                "            \"site_admin\": false\n" +
                "        },\n" +
                "        \"html_url\": \"https://github.com/rubal/rubal\",\n" +
                "        \"description\": null,\n" +
                "        \"fork\": false,\n" +
                "        \"default_branch\": \"master\"\n" +
                "    },\n" +
                "\t{\n" +
                "        \"id\": 3581579,\n" +
                "        \"node_id\": \"MDEwOlJlcG9zaXRvcnkzNTgxMjc5\",\n" +
                "        \"name\": \"mock\",\n" +
                "        \"full_name\": \"rubal/mock\",\n" +
                "        \"private\": false,\n" +
                "        \"owner\": {\n" +
                "            \"login\": \"rubal\",\n" +
                "            \"id\": 1484934,\n" +
                "            \"node_id\": \"MDQ6VXNlcjE0ODQ5Mzc=\",\n" +
                "            \"avatar_url\": \"https://avatars.githubusercontent.com/u/1484937?v=4\",\n" +
                "            \"gravatar_id\": \"\",\n" +
                "            \"url\": \"https://api.github.com/users/rubal\",\n" +
                "            \"html_url\": \"https://github.com/rubal\",\n" +
                "            \"followers_url\": \"https://api.github.com/users/rubal/followers\",\n" +
                "            \"type\": \"User\",\n" +
                "            \"site_admin\": false\n" +
                "        },\n" +
                "        \"html_url\": \"https://github.com/rubal/rubal\",\n" +
                "        \"description\": null,\n" +
                "        \"fork\": false,\n" +
                "        \"default_branch\": \"master\"\n" +
                "    }\n" +
                "]";
        String rubalRepoBranchesResponseJsonString = "[\n" +
                "    {\n" +
                "        \"name\": \"master\",\n" +
                "        \"commit\": {\n" +
                "            \"sha\": \"aab1968634c9165c4a37f6ca206eb9a5f4751806\",\n" +
                "            \"url\": \"https://api.github.com/repos/rubal/rubal/commits/aab1968634c9165c4a37f6ca206eb9a5f4751806\"\n" +
                "        },\n" +
                "        \"protected\": false\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"thulium\",\n" +
                "        \"commit\": {\n" +
                "            \"sha\": \"986d20b171362755d315498fd4159c7e049d390f\",\n" +
                "            \"url\": \"https://api.github.com/repos/rubal/rubal/commits/986d20b171362755d315498fd4159c7e049d390f\"\n" +
                "        },\n" +
                "        \"protected\": false\n" +
                "    }\n" +
                "]";
        String mockRepoBranchesResponseJsonString = "[\n" +
                "    {\n" +
                "        \"name\": \"master\",\n" +
                "        \"commit\": {\n" +
                "            \"sha\": \"mocksha\",\n" +
                "            \"url\": \"https://api.github.com/repos/rubal/rubal/commits/aab1968634c9165c4a37f6ca206eb9a5f4751806\"\n" +
                "        },\n" +
                "        \"protected\": false\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"mock\",\n" +
                "        \"commit\": {\n" +
                "            \"sha\": \"mocksha23\",\n" +
                "            \"url\": \"https://api.github.com/repos/rubal/rubal/commits/986d20b171362755d315498fd4159c7e049d390f\"\n" +
                "        },\n" +
                "        \"protected\": false\n" +
                "    }\n" +
                "]";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode usernameRepoUrlResponse = objectMapper.readTree(mockUserReposResponseJsonString);
        JsonNode rubalRepoBrancheUrlResponse = objectMapper.readTree(rubalRepoBranchesResponseJsonString);
        JsonNode mockRepoBrancheUrlResponse = objectMapper.readTree(mockRepoBranchesResponseJsonString);
        initMockConfigBean();
        when(restClient.getJsonResponseForGivenGetRestAPI("https://api.github.com/users/rubal/repos")).thenReturn(usernameRepoUrlResponse);
        when(restClient.getJsonResponseForGivenGetRestAPI("https://api.github.com/repos/rubal/rubal/branches")).thenReturn(rubalRepoBrancheUrlResponse);
        when(restClient.getJsonResponseForGivenGetRestAPI("https://api.github.com/repos/rubal/mock/branches")).thenReturn(mockRepoBrancheUrlResponse);


        List<UserGitRepo> responseObjectList = jsonParsingService
                .fetchAllReposDataForUser("rubal");
        assertEquals(2, responseObjectList.size());
        assertEquals(2,responseObjectList.get(0).getRepoBranchDetails().length);
        assertTrue(responseObjectList.get(0).getRepoName()!=null);
        assertTrue(responseObjectList.get(0).getOwnerLogin()!=null);
        assertTrue(responseObjectList.get(0).getRepoBranchDetails()[0].getBranchName()!=null);
    }

    private void initMockConfigBean(){
        when(configParameters.getBranchCommitField()).thenReturn(actualBean.getBranchCommitField());
        when(configParameters.getBranchCommitHashField()).thenReturn(actualBean.getBranchCommitHashField());
        when(configParameters.getBranchNameField()).thenReturn(actualBean.getBranchNameField());
        when(configParameters.getGitHubRepoBranchesUrl()).thenReturn(actualBean.getGitHubRepoBranchesUrl());
        when(configParameters.getGitHubReposUrl()).thenReturn(actualBean.getGitHubReposUrl());
        when(configParameters.getInvalidResponseFormats()).thenReturn(actualBean.getInvalidResponseFormats());
        when(configParameters.getOwnerField()).thenReturn(actualBean.getOwnerField());
        when(configParameters.getRepoForkField()).thenReturn(actualBean.getRepoForkField());
        when(configParameters.getRepoNameField()).thenReturn(actualBean.getRepoNameField());
        when(configParameters.getOwnerLoginField()).thenReturn(actualBean.getOwnerLoginField());
    }


}
