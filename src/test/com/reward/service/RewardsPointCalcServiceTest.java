package com.reward.service;

import com.reward.dao.RewardRepository;
import com.reward.exception.RewardsPointServiceCalException;
import com.reward.model.RewardPoint;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class RewardsPointCalcServiceTest {

    @Mock
    private RewardRepository rewardRepository;

    @InjectMocks
    RewardsPointCalcService rewardsPointCalcService;


    @Test
    public void testCalculateReward() {

        RewardPoint rd = new RewardPoint();
        rd.setRewardId(1);
        rd.setTransactionDate(new Date());
        rd.setTransactionPrice(121.0);

        when(rewardRepository.save(any(RewardPoint.class))).thenReturn(rd);
        ReflectionTestUtils.setField(rewardsPointCalcService, "rewardPnt", 2);
        RewardPoint rewardPoint = rewardsPointCalcService.calculateRewards(rd);
        assertThat(rd).isNotNull();
        assertEquals(1, rewardPoint.getRewardId());
    }

    @Test
    public void testCalculateReward_Case2() {

        RewardPoint rd = new RewardPoint();
        rd.setRewardId(2);
        rd.setTransactionDate(new Date());
        rd.setTransactionPrice(99);

        when(rewardRepository.save(any(RewardPoint.class))).thenReturn(rd);
        ReflectionTestUtils.setField(rewardsPointCalcService, "rewardPnt", 2);
        RewardPoint rewardPoint = rewardsPointCalcService.calculateRewards(rd);
        assertThat(rd).isNotNull();
        assertEquals(2, rewardPoint.getRewardId());
    }

    @Test(expected = RewardsPointServiceCalException.class)
    public void testCalculateReward_Fail() {

        RewardPoint rd = new RewardPoint();
        rd.setRewardId(1);
        rd.setTransactionDate(new Date());
        rd.setTransactionPrice(121.0);

        when(rewardRepository.save(any(RewardPoint.class))).thenThrow(RewardsPointServiceCalException.class);
        ReflectionTestUtils.setField(rewardsPointCalcService, "rewardPnt", 2);
        RewardPoint rewardPoint = rewardsPointCalcService.calculateRewards(rd);
        assertThat(rd).isNotNull();
        assertEquals(1, rd.getRewardId());
    }


    @Test
    public void testFetchAllRecords() {

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


        when(rewardRepository.findAll()).thenReturn(rewardPointList);
        List<RewardPoint> rewardList = rewardsPointCalcService.fetchAllRewards();
        assertThat(rewardList).isNotNull();
        assertTrue(rewardList.size() > 0);
        assertEquals(3,rewardList.get(2).getRewardId());
    }

    @Test
    public void testGetRewardByid() {

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


        when(rewardRepository.findAll()).thenReturn(rewardPointList);
        RewardPoint rewardById = rewardsPointCalcService.getRewardById(1);
        assertThat(rewardById).isNotNull();
        assertEquals(1,rewardById.getRewardId());
    }

}
