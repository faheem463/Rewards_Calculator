package com.reward;

import com.reward.cotroller.RewardsPointController;
import com.reward.dao.RewardRepository;
import com.reward.service.RewardsPointCalcService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
class RewardProgramAppApplicationTests {

    @Autowired
    private RewardsPointController rewardsPointController;

    @Autowired
    private RewardsPointCalcService rewardsPointCalcService;

    @Autowired
    private RewardRepository rewardRepository;

    @Test
    void contextLoads() {
        assertThat(rewardsPointController).isNotNull();
        assertThat(rewardsPointCalcService).isNotNull();
    }

}
