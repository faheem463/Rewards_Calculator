package com.reward.service;

import com.reward.dao.RewardRepository;
import com.reward.model.RewardPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class RewardsPointCalcService {

    @Value("${rewards.point}")
    private Integer rewardPnt;


    @Autowired
    private RewardRepository rewardRepository;

    public RewardPoint calculateRewards(RewardPoint reward) {
        log.info("Into calculate Rewards");
        if (reward.getTransactionPrice() >= 50 && reward.getTransactionPrice() < 100) {
            double v = reward.getTransactionPrice() - 50;
            reward.setRewards(Double.valueOf(v).intValue());
        } else if (reward.getTransactionPrice() > 100) {
            double v = rewardPnt * (reward.getTransactionPrice() - 100) + 50;
            reward.setRewards(Double.valueOf(v).intValue());
        }
        rewardRepository.save(reward);
        log.info("SuccessFully calcualted and saved");
        return reward;
    }

    public List<RewardPoint> fetchAllRewards() {
        return rewardRepository.findAll();
    }

    public RewardPoint getRewardById(Integer rewardId) {
        Optional<RewardPoint> anyReward = rewardRepository.findAll().stream()
                .filter(x -> x.getRewardId() == rewardId).findAny();
        return anyReward.orElse(null);
    }
}
