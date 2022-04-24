package com.tui.assignment;


import com.tui.assignment.client.RestClient;
import com.tui.assignment.config.ConfigParameters;
import com.tui.assignment.exception.IncorrectResponseFormatException;
import com.tui.assignment.exception.UserDoesNotExitsException;
import com.tui.assignment.model.request.RepoBranchInfo;
import com.tui.assignment.model.request.UserRepoInfo;
import com.tui.assignment.model.response.UserGitRepo;
import com.tui.assignment.service.GitAPIsResponseParsingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TUIAssignmentUnitTests {

    @Mock
    private RestClient restClient;

    @Mock
    private ConfigParameters configParameters;

    @InjectMocks
    private GitAPIsResponseParsingService gitAPIsResponseParsingService;

    @Test
    public void fetchFinalResponseObjectsForInputUser() {

        UserRepoInfo mockRepoInfo = new UserRepoInfo();
        mockRepoInfo.setFork(false);
        mockRepoInfo.setName("mockRepo1");
        UserRepoInfo.OwnerInfo mockOwnerInfo = mockRepoInfo.new OwnerInfo();
        mockOwnerInfo.setLogin("mockLogin1");
        mockRepoInfo.setOwnerInfo(mockOwnerInfo);

        RepoBranchInfo mockBranchInfo = new RepoBranchInfo();
        mockBranchInfo.setBranchName("mockBranch1");
        RepoBranchInfo.CommitInfo commitInfo = mockBranchInfo.new CommitInfo();
        commitInfo.setSha("mockSHA");
        mockBranchInfo.setCommitInfo(commitInfo);

        List<UserRepoInfo> userRepoInfoList = new ArrayList<>();
        userRepoInfoList.add(mockRepoInfo);
        List<RepoBranchInfo> repoBranchInfoList = new ArrayList<>();
        repoBranchInfoList.add(mockBranchInfo);

        when(configParameters.getGitHubReposUrl()).thenReturn("mockRepoURL/{0}");
        when(configParameters.getGitHubRepoBranchesUrl()).thenReturn("mockBranchesURL/{0}/{1}");
        when(configParameters.getThreadPoolSize()).thenReturn(8);

        when(restClient.getAllReposInfoForUser("mockRepoURL/rubal")).thenReturn(userRepoInfoList);
        when(restClient.getRepoBranchesInfo("mockBranchesURL/rubal/mockRepo1")).thenReturn(repoBranchInfoList);

        List<UserGitRepo> responseObjectList = gitAPIsResponseParsingService
                .fetchAllReposDataForUser("rubal");
        assertEquals(1, responseObjectList.size());
        assertEquals(1, responseObjectList.get(0).getRepoBranchDetails().size());
        assertNotNull(responseObjectList.get(0).getRepoName());
        assertNotNull(responseObjectList.get(0).getOwnerLogin());
        assertNotNull(responseObjectList.get(0).getRepoBranchDetails().get(0).getBranchName());
        assertNotNull(responseObjectList.get(0).getRepoBranchDetails().get(0).getLastCommitSHA());
        assertEquals("mockBranch1", responseObjectList.get(0).getRepoBranchDetails().get(0).getBranchName());
        assertEquals("mockRepo1", responseObjectList.get(0).getRepoName());
    }

    @Test
    public void invalidResponseFormatRequestHandling() {
        when(configParameters.getInvalidResponseFormats()).thenReturn("application/xml");
        assertThrows(IncorrectResponseFormatException.class, () -> gitAPIsResponseParsingService.validateRequestedResponseFormat("application/xml"));
    }

    @Test
    public void validResponseFormatRequest() {
        when(configParameters.getInvalidResponseFormats()).thenReturn("application/xml");
        assertDoesNotThrow(() -> gitAPIsResponseParsingService.validateRequestedResponseFormat("*/*"));
    }

    @Test
    public void incorrectUserName() {
        assertThrows(UserDoesNotExitsException.class, () -> gitAPIsResponseParsingService.validateUserName(" "));
    }

    @Test
    public void correctUserName() {
        assertDoesNotThrow(() -> gitAPIsResponseParsingService.validateUserName("rubal"));
    }
}
