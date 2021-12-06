package com.reward.cotroller;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reward.exception.RewardsPointServiceCalException;
import com.reward.model.RewardPoint;
import com.reward.service.RewardsPointCalcService;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class RewardsPointControllerTest {

    @InjectMocks
    private RewardsPointController rewardsPointController;

    @Mock
    private RewardsPointCalcService rewardsPointCalcService;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(rewardsPointController)
                .build();
    }

    @Test
    public void testCalculateReward() throws Exception {
        RewardPoint rd = new RewardPoint();
        rd.setRewardId(1);
        rd.setTransactionDate(new Date());
        rd.setTransactionPrice(120.0);
        Mockito.when(rewardsPointCalcService.calculateRewards(Mockito.any())).thenReturn(rd);
        String contentAsString = mockMvc.perform(post("/calculatereward")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(rd)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        Assert.assertEquals("Successfully Generated Reward with ID 1", contentAsString);

    }

    @Test
    public void testGetRewards() throws Exception {
        List<RewardPoint> rewardPointList = new ArrayList();
        RewardPoint rd = new RewardPoint();
        rd.setRewardId(1);
        rd.setTransactionDate(new Date());
        rd.setTransactionPrice(120.0);

        RewardPoint rd2 = new RewardPoint();
        rd2.setRewardId(2);
        rd2.setTransactionDate(new Date());
        rd2.setTransactionPrice(121.0);

        RewardPoint rd3 = new RewardPoint();
        rd3.setRewardId(3);
        rd3.setTransactionDate(new Date());
        rd3.setTransactionPrice(122.0);

        rewardPointList.add(rd);
        rewardPointList.add(rd2);
        rewardPointList.add(rd3);
        Mockito.when(rewardsPointCalcService.fetchAllRewards()).thenReturn(rewardPointList);
        mockMvc.perform(get("/getrewards")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(rd)))
                .andExpect(status().isOk()).andReturn();
        assertNotNull(rewardPointList);
        assertEquals(3, rewardPointList.size());
        assertTrue(rewardPointList.size() > 0);
        assertEquals(122.0, rewardPointList.get(2).getTransactionPrice());
    }

    @Test(expected = java.lang.AssertionError.class)
    public void testGetRewards_Fail() throws Exception {
        List<RewardPoint> rewardPointList = new ArrayList();
        RewardPoint rd = new RewardPoint();
        rd.setRewardId(1);
        rd.setTransactionDate(new Date());
        rd.setTransactionPrice(120.0);

        RewardPoint rd2 = new RewardPoint();
        rd2.setRewardId(2);
        rd2.setTransactionDate(new Date());
        rd2.setTransactionPrice(121.0);

        RewardPoint rd3 = new RewardPoint();
        rd3.setRewardId(3);
        rd3.setTransactionDate(new Date());
        rd3.setTransactionPrice(122.0);

        rewardPointList.add(rd);
        rewardPointList.add(rd2);
        rewardPointList.add(rd3);
        Mockito.when(rewardsPointCalcService.fetchAllRewards()).thenThrow(RewardsPointServiceCalException.class);


        String exceptionParam = "failed_to_fetch";

        mockMvc.perform(get("/getrewards", exceptionParam)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RewardsPointServiceCalException));
    }

    @Test
    public void testGetRewardById() throws Exception {
        RewardPoint rewardPoint = new RewardPoint();
        RewardPoint rd = new RewardPoint();
        rd.setRewardId(1);
        rd.setTransactionDate(new Date());
        rd.setTransactionPrice(120.0);

        Mockito.when(rewardsPointCalcService.getRewardById(rewardPoint.getRewardId())).thenReturn(rd);
        mockMvc.perform(get("/reward/{id}", rd.getRewardId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(rd)))
                .andExpect(status().isOk()).andReturn();
        assertNotNull(rewardPoint);
        assertEquals(1, rd.getRewardId());
    }

    @Test(expected = junit.framework.AssertionFailedError.class)
    public void testCalculateReward_Fail() throws Exception {
        RewardPoint rd = new RewardPoint();
        rd.setRewardId(1);
        rd.setTransactionDate(new Date());
        rd.setTransactionPrice(120.0);

        String exceptionParam = "failed_to_save";

        mockMvc.perform(post("/calculatereward", exceptionParam)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RewardsPointServiceCalException))
                .andExpect(result -> assertEquals("bad arguments", result.getResolvedException().getMessage()));



    }
}
